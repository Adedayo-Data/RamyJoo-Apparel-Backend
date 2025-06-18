package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.AuthRequestDTO;
import com.ramyjoo.fashionstore.dto.AuthResponseDTO;
import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.model.USER_ROLE;
import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.model.UserPrincipal;
import com.ramyjoo.fashionstore.repository.UserRepository;
import com.ramyjoo.fashionstore.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

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
            List<Address> addresses = createdUser.getDeliveryAddress();
            for(Address address : addresses){
                address.setUser(createdUser);
            }

            userRepo.save(createdUser);

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
}
