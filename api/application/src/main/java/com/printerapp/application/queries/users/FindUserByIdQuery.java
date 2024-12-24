package com.printerapp.application.queries.users;

import com.printerapp.domain.aggregates.user.value_objects.UserId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindUserByIdQuery {
    UserId userId;
}
