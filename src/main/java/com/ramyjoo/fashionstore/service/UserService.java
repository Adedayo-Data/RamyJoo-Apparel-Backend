package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.*;
import com.ramyjoo.fashionstore.model.*;
import com.ramyjoo.fashionstore.repository.AddressRepository;
import com.ramyjoo.fashionstore.repository.CartRepository;
import com.ramyjoo.fashionstore.repository.UserRepository;
import com.ramyjoo.fashionstore.security.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final AddressRepository addressRepo;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final ModelMapper modelMapper;
    private CloudinaryService cloudinaryService;

    // User Registration
    public AuthResponseDTO createUserHandler(@RequestBody User user){
        User isUserExist = userRepo.findByEmail(user.getEmail());
        try{
            if(isUserExist != null){
                throw new BadCredentialsException("Email already Exist!");
            }
            User createdUser = new User();
            createdUser.setEmail(user.getEmail());
            createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
            createdUser.setFullName(user.getFullName());
            createdUser.setGender(user.getGender());
            createdUser.setPhone(user.getPhone());
            createdUser.setRole(user.getRole());
            createdUser.setDeliveryAddress(user.getDeliveryAddress());
            // setting users reference for address column
//            List<Address> addresses = createdUser.getDeliveryAddress();
//            for(Address address : addresses){
//                address.setUser(createdUser);
//            }
            // Save user
            User savedUser = userRepo.save(createdUser);

            // saving user Address info
            Address address = savedUser.getDeliveryAddress();
            address.setUser(savedUser);
            addressRepo.save(address);

            // saving cart per user
            Cart cart = new Cart();
            cart.setUser(savedUser);
            cart.setTotalAmount(BigDecimal.ZERO);
            cartRepo.save(cart);

            Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(String.valueOf(createdUser.getRole())));
            // authenticate the user after saving
            Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getEmail(), createdUser.getPassword(), authorities);
            // set authentication in security context holder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = new UserPrincipal(createdUser);
            // generate JWT token
            String jwt = jwtService.generateToken(userDetails);

            AuthResponseDTO responseDTO = new AuthResponseDTO();
            responseDTO.setJwt(jwt);
            responseDTO.setMessage("Sign-up successful!");
            responseDTO.setRole(createdUser.getRole());

            return responseDTO;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    } // end User Registration

    // User Login
    public AuthResponseDTO validateLoginRequest(AuthRequestDTO loginRequestDTO) {

        String username = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        Authentication authentication = authenticate(username, password);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtService.generateToken(userDetails);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setJwt(jwt);
        responseDTO.setRole(USER_ROLE.valueOf(role));
        responseDTO.setMessage("Login successful!");

        return responseDTO;
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("username not found!");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password does not match!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public ProfileResponseDto updateProfilePhoto(MultipartFile file) throws IOException {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(userPrincipal.getUsername());

        String imageUrl = cloudinaryService.uploadImage(file);
        user.setProfilePhoto(imageUrl);

        userRepo.save(user);
        return modelMapper.map(user, ProfileResponseDto.class);
    }

    public ProfileResponseDto userProfile(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(userPrincipal.getUsername());

        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }

        return modelMapper.map(user, ProfileResponseDto.class);
    }

    public ProfileResponseDto updateProfile(ProfileRequestDTO request) throws Exception {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(userPrincipal.getUsername());
//        Address address = new Address();
//        address.setUser(user);

        // Update fields
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setRole(request.getRole());

        // Map to ADDRESS
        Address address = modelMapper.map(request.getDeliveryAddress(), Address.class);
        address.setUser(user);
        user.setDeliveryAddress(address);

        User updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, ProfileResponseDto.class);
    }
}
