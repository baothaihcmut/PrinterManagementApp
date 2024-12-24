package com.printerapp.domain.aggregates.user.entities;

import java.time.LocalDate;

import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.models.BaseAggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseAggregate<UserId> {
    private String employeeId;

    private LocalDate startWorkDate;
    private Boolean isOnWork;

    public Employee(UserId userId, String employeeId) {
        super(userId);
        this.employeeId = employeeId;
        this.isOnWork = false;
    }

    public void activate() {
        this.isOnWork = true;
        this.startWorkDate = LocalDate.now();
    }

    public void onWork() {
        this.isOnWork = true;
    }

    public void offWork() {
        this.isOnWork = false;
    }

}
