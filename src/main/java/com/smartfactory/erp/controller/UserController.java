package com.smartfactory.erp.controller;

import com.smartfactory.erp.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login") //POST로 들어오면 실행되는 함수
    public LoginResponse login(@RequestBody LoginRequest request){
        boolean success = userService.login(request.getEmail(), request.getPassword());

        if(success){
            return new LoginResponse(true,"로그인 성공", request.email);
        }else{
            return new LoginResponse(false, "로그인 실패", request.email);
        }
    }

    @Data
    static class LoginRequest{
        private String email;
        private String password;
    }

    @Data
    static class LoginResponse{
        private boolean success;
        private String message;
        private String email;
        public LoginResponse(boolean success, String message, String email){
            this.success = success;
            this.message = message;
            this.email = email;
        }
    }
}
