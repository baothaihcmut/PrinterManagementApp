package com.printerapp.application.results.auth;

import java.util.UUID;

import com.printerapp.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserContext {
    private UUID id;
    private Role[] role;
}
