package com.printerapp.infrastructure.google;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.printerapp.application.results.google.GoogleResult;
import com.printerapp.application.services.GoogleService;
import com.printerapp.infrastructure.google.dtos.ExchangeTokenResponse;
import com.printerapp.infrastructure.google.dtos.GoogleResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GoogleAuthenticationService implements GoogleService {

        @Value("${outbound.identity.client_id}")
        protected String CLIENT_ID;

        @Value("${outbound.identity.client_secret}")
        protected String CLIENT_SECRET;

        @Value("${outbound.identity.redirect_uri}")
        protected String REDIRECT_URI;

        public GoogleResponse getUserInfo(String alt, String accessToken) {

                RestTemplate restTemplate = new RestTemplate();
                String url = UriComponentsBuilder.fromHttpUrl("https://openidconnect.googleapis.com/v1/userinfo")
                                .queryParam("alt", alt)
                                .queryParam("access_token", accessToken)
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + accessToken);
                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<GoogleResponse> response = restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                requestEntity,
                                GoogleResponse.class);
                return response.getBody();
        }

        public GoogleResult outboundAuthentication(String googleCode)
                        throws JsonMappingException, JsonProcessingException {

                // log.info("______________________________");
                // log.info(googleCode);
                Map<String, String> requestBody = new LinkedHashMap<>();
                requestBody.put("code", googleCode);
                requestBody.put("client_id", CLIENT_ID);
                requestBody.put("client_secret", CLIENT_SECRET);
                requestBody.put("redirect_uri", REDIRECT_URI);
                requestBody.put("grant_type", "authorization_code");

                // Exchange token from Google
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

                ResponseEntity<String> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                                entity,
                                String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                // log.info(jsonResponse.toString());
                ExchangeTokenResponse googleResponse = objectMapper.treeToValue(jsonResponse,
                                ExchangeTokenResponse.class);
                // log.info(googleResponse.toString());

                GoogleResponse userInfo = getUserInfo("json", googleResponse.getAccess_token());

                log.info("User mail info {}", userInfo.getEmail());

                return GoogleResult.builder()
                                .userEmail(userInfo.getEmail())
                                .userImage(userInfo.getPicture())
                                .build();
        }

}
