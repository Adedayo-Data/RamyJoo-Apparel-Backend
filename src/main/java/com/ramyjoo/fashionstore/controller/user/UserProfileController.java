package com.ramyjoo.fashionstore.controller.user;

import com.ramyjoo.fashionstore.dto.ProfileRequestDTO;
import com.ramyjoo.fashionstore.dto.ProfileResponseDto;
import com.ramyjoo.fashionstore.model.USER_ROLE;
import com.ramyjoo.fashionstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

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

    @PutMapping("/update")
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody ProfileRequestDTO updateRequest) throws Exception {
        ProfileResponseDto responseDto = userService.updateProfile(updateRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/profile/photo")
    public ResponseEntity<ProfileResponseDto> updatePhoto(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.updateProfilePhoto(file));
    }

}
