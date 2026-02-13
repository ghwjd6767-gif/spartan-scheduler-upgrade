package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.Logindto.*;
import com.sparta.scheduler.dto.Userdto.*;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.signup(request);
    }

    @GetMapping("/users/{userId}")
    public GetOneUserResponse getOneUser(@PathVariable Long userId) {
        return userService.getOne(userId);
    }

    @GetMapping("/users")
    public List<GetOneUserResponse> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/user/{userId}")
    public UpdateUserResponse update(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request,HttpSession session
    ) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new IllegalStateException("로그인 정보없음");
        }
        return userService.update(userId, request,sessionUser.getId());
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable Long userId,HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new IllegalStateException("로그인 정보없음");
        }
        userService.delete(userId,sessionUser.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request, session);
        SessionUser sessionUser = new SessionUser(user.getId(), user.getEmail());
        session.setAttribute("loginUser", sessionUser);

        LoginResponse response = new LoginResponse(user.getId(), user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
