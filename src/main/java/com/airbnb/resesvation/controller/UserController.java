package com.airbnb.resesvation.controller;

import com.airbnb.resesvation.dto.JWTResponse;
import com.airbnb.resesvation.dto.LoginDto;
import com.airbnb.resesvation.dto.PropertyUserDto;
import com.airbnb.resesvation.entity.PropertyUser;
import com.airbnb.resesvation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/api/v1/users/addUser
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto dto) {
        PropertyUser user = userService.addUser(dto);
        if (user != null) {

            return new ResponseEntity<>("sign up successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
       String jwtToken = userService.verifyLogin(loginDto);
        if (jwtToken!=null){
            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setToken(jwtToken);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    @GetMapping("/profile")
    public PropertyUser getCurrentProfile(@AuthenticationPrincipal PropertyUser propertyUser){
        return propertyUser;
    }
}
