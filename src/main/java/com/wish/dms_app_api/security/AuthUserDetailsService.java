package com.wish.dms_app_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wish.dms_app_api.entity.User;
import com.wish.dms_app_api.repository.IUserRepository;


@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " );
        }
        return new UserSingleton(user);
    }
}
