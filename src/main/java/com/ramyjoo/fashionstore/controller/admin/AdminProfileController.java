package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.dto.ProfileRequestDTO;
import com.ramyjoo.fashionstore.dto.ProfileResponseDto;
import com.ramyjoo.fashionstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> userProfile(){
        try{
            ProfileResponseDto responseDto = userService.userProfile();
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody ProfileRequestDTO updateRequest) throws Exception {
        ProfileResponseDto responseDto = userService.updateProfile(updateRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
