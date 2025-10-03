package com.example.realtimetaskmanagement.controller.pagingcontrollers;

import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.service.pagingservice.PagingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paging/users")
public class PagingUserController {

    private final PagingUserService userService;


    @GetMapping("/all")
    public ResponseEntity<?> findAllUsersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Users> users = userService.findAllUsersPaged(page, size);
        return ResponseEntity.ok(users);
    }
}
