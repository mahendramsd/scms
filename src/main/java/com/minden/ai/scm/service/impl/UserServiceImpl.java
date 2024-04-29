package com.minden.ai.scm.service.impl;

import com.minden.ai.scm.domain.User;
import com.minden.ai.scm.exception.CustomException;
import com.minden.ai.scm.repository.UserRepository;
import com.minden.ai.scm.utils.CustomErrorCodes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author mahendrasridayarathna
 * @created 29/04/2024 - 1:14 pm
 * @project IntelliJ IDEA
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(CustomErrorCodes.USER_NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }
}
