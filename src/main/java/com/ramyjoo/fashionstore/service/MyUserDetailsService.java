package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.model.UserPrincipal;
import com.ramyjoo.fashionstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        return new UserPrincipal(user);
    }
}
