package com.printerapp.domain.records.transactions;

import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.Role;

public record UserRecord(UserId id, UserName name, Role role) {

}
