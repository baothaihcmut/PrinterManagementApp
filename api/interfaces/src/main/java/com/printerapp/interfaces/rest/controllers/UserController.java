package com.printerapp.interfaces.rest.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.printerapp.application.queries.users.FindUserByIdQuery;
import com.printerapp.application.queries.users.FindUserQuery;
import com.printerapp.application.queries.users.SearchUserQuery;
import com.printerapp.application.results.users.AddEmployeeMailResult;
import com.printerapp.application.results.users.UserResult;
import com.printerapp.application.services.UserService;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.Role;
import com.printerapp.interfaces.rest.common.response.AppResponse;
import com.printerapp.interfaces.rest.dtos.users.ActivateUserDTO;
import com.printerapp.interfaces.rest.dtos.users.AddEmployeeMailDTO;
import com.printerapp.interfaces.rest.mappers.RestUserMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestUserMapper userMapper;

    @PostMapping("/activate")
    public ResponseEntity<AppResponse> createUser(@RequestBody @Valid ActivateUserDTO createUserDTO) {
        UserResult res = this.userService.activateUser(this.userMapper.toUserInfoCommand(createUserDTO));
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Activate user success", res);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/{id}")
    public ResponseEntity<AppResponse> findById(@PathVariable("id") UUID id) {
        FindUserByIdQuery query = FindUserByIdQuery.builder().userId(new UserId(id)).build();
        UserResult res = this.userService.findUserById(query);
        return AppResponse.initRespose(HttpStatus.OK, true, "Get user by id success", res);
    }

    @PostMapping("/addEmployeeMail")
    public ResponseEntity<AppResponse> addMail(@RequestBody @Valid AddEmployeeMailDTO addMailDTO) {
        AddEmployeeMailResult res = this.userService
                .addEmployeeMail(this.userMapper.toAddEmployeeMailCommand(addMailDTO));
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Add mail success", res);
    }

    @GetMapping("")
    public ResponseEntity<AppResponse> findUser(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "sort", required = false) SortParam sortParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PaginatedResult<UserResult> res = this.userService.findUser(FindUserQuery.builder().role(role)
                .sortParam(sortParam).paginatedParam(PaginatedParam.builder().page(page).size(size).build()).build());
        return AppResponse.initRespose(HttpStatus.OK, true, "Find user sucess", res);
    }

    @GetMapping("/search")
    public ResponseEntity<AppResponse> searchUser(
            @RequestParam("search") String search,
            @RequestParam("role") Role role,
            @RequestParam(value = "sort", required = false) SortParam sortParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PaginatedResult<UserResult> res = this.userService.searchUser(SearchUserQuery.builder()
                .search(search)
                .role(role)
                .sortParam(sortParam).paginatedParam(PaginatedParam.builder().page(page).size(size).build()).build());
        return AppResponse.initRespose(HttpStatus.OK, true, "Find user sucess", res);
    }

}
