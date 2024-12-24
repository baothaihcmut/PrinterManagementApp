package com.printerapp.application.results.transactions;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionUserResult {
    private UserId id;
    private UserName name;
    private Email email;
    private Role role;
    private String phoneNumber;
}
