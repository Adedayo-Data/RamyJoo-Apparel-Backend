package com.ramyjoo.fashionstore.controller;

import com.ramyjoo.fashionstore.dto.AuthRequestDTO;
import com.ramyjoo.fashionstore.dto.AuthResponseDTO;
import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody User user){
        AuthResponseDTO responseDTO = userService.createUserHandler(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signin(@RequestBody AuthRequestDTO loginRequestDTO){
        AuthResponseDTO responseDTO = userService.validateLoginRequest(loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
