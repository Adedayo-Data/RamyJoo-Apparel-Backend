package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.dto.UserResponseDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.STATUS;
import com.ramyjoo.fashionstore.model.USER_ROLE;
import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserResponseDTO> response = users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestParam("role") USER_ROLE newRole) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(newRole);
        userRepo.save(user);
        return ResponseEntity.ok("User role updated");
    }
}

