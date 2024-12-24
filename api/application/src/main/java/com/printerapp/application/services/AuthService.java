package com.printerapp.application.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.printerapp.application.commands.auth.ExchangeTokenCommand;
import com.printerapp.application.exceptions.BadArgumentException;
import com.printerapp.application.exceptions.NotFoundException;
import com.printerapp.application.results.auth.ExchangeTokenResult;
import com.printerapp.application.results.auth.ExchangeTokenResult.Token;
import com.printerapp.application.results.auth.UserContext;
import com.printerapp.application.results.google.GoogleResult;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    @NonFinal
    @Value("${jwt.access_token.sign_key}")
    private String accessTokenSignKey;

    @NonFinal
    @Value("${jwt.access_token.expiration}")
    private Integer accessTokenExpiration;

    @NonFinal
    @Value("${jwt.refresh_token.sign_key}")
    private String refreshTokenSignKey;

    @NonFinal
    @Value("${jwt.refresh_token.expiration}")
    private Integer refreshTokenExpiration;

    private final UserRepository userRepository;
    private final GoogleService googleService;

    private String genAccessToken(User user) throws Exception {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().getValue().toString())
                .expirationTime(new Date(Instant.now().plus(accessTokenExpiration, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", user.getRole().getValue())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(accessTokenSignKey));
        return jwsObject.serialize();
    }

    private String genRefreshToken(User user) throws Exception {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().getValue().toString())
                .expirationTime(new Date(Instant.now().plus(refreshTokenExpiration, ChronoUnit.HOURS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(refreshTokenSignKey));
        return jwsObject.serialize();
    }

    @Transactional
    public ExchangeTokenResult exchangeToken(ExchangeTokenCommand exchangeTokencommand) throws Exception {
        GoogleResult googleResult = googleService.outboundAuthentication(exchangeTokencommand.getToken());
        String email = googleResult.getUserEmail();
        // check if email is customer or employee
        if (email.endsWith("@hcmut.edu.vn")) {
            // if email is customer
            // check if user exist
            User user = this.userRepository.findByEmail(new Email(email, Role.CUSTOMER)).orElse(null);
            if (user != null) {
                return new ExchangeTokenResult(
                        user.getIsActive(),
                        user.getRole(),
                        new Token(
                                this.genAccessToken(user),
                                this.genRefreshToken(user)));
            }
            // if customer not exisit create new user with email, Customer role and isActive
            // false
            User newUser = new User();
            newUser.setEmail(new Email(email, Role.CUSTOMER));
            newUser.setRole(Role.CUSTOMER);
            newUser.setIsActive(false);
            this.userRepository.save(newUser);
            return new ExchangeTokenResult(false, Role.CUSTOMER,
                    new Token(this.genAccessToken(newUser), this.genRefreshToken(newUser)));
        } else {
            // if email not hcmut email
            User user = this.userRepository.findByEmail(new Email(email, Role.EMPLOYEE))
                    .orElseThrow(() -> new NotFoundException("Email not found in system"));
            return new ExchangeTokenResult(user.getIsActive(), user.getRole(),
                    new Token(this.genAccessToken(user), this.genRefreshToken(user)));
        }
    }

    public UserContext getUserContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        String id = context.getAuthentication().getName();
        Role[] roles = context.getAuthentication().getAuthorities().stream()
                .map(auth -> {
                    try {
                        return Role.valueOf(auth.getAuthority().toUpperCase().substring(5));
                    } catch (IllegalArgumentException e) {
                        throw new BadArgumentException("Invalid role");
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Role[]::new);
        return new UserContext(UUID.fromString(id), roles);
    }
}
