package com.crust.backendproject.security;

import com.crust.backendproject.exception.AppException;
import com.crust.backendproject.models.User;
import com.crust.backendproject.repositories.UserRepository;
import com.crust.backendproject.util.Errors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public PrincipalUser loadUserByUsername(String username) {
        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        return PrincipalUser.create(user);
    }

}
