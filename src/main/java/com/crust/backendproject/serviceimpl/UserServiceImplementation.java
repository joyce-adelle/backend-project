package com.crust.backendproject.serviceimpl;

import com.crust.backendproject.audit.AuditAware;
import com.crust.backendproject.dto.request.UpdatePasswordRequest;
import com.crust.backendproject.dto.request.UpdateUserRequest;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.exception.AppException;
import com.crust.backendproject.models.User;
import com.crust.backendproject.repositories.UserRepository;
import com.crust.backendproject.service.UserService;
import com.crust.backendproject.util.Errors;
import com.crust.backendproject.util.OffsetBasedPageRequest;
import com.crust.backendproject.util.UtilClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditAware auditAware;


    @Override
    public User updateUser(UpdateUserRequest updateUserRequest) {

        User user = auditAware.getCurrentUserAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        if (UtilClass.isNull(updateUserRequest.getName()))
            throw new AppException(Errors.NOTHING_TO_UPDATE);
        else
            user.setName(updateUserRequest.getName());

        return userRepository.save(user);

    }

    @Override
    public User getUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

    }

    @Override
    public List<User> getAllUsers(long offset, int limit) {

        Pageable page = new OffsetBasedPageRequest(offset, limit, Sort.Direction.ASC, "name");
        return userRepository.findAll(page);

    }

    @Override
    public MessageResponse updatePassword(UpdatePasswordRequest request) {

        User user = auditAware.getCurrentUserAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new AppException(Errors.INVALID_PASSWORD);

        String newPassword = request.getNewPassword();

        if (newPassword.equals(request.getOldPassword())) {
            throw new AppException(Errors.DUPLICATE_PASSWORDS);
        }

        userRepository.updatePasswordByEmail(passwordEncoder.encode(newPassword), user.getEmail());

        return MessageResponse.builder().message("New password set successfully").build();

    }

}
