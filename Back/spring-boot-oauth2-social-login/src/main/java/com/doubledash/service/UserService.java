package com.doubledash.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.doubledash.dto.*;
import com.doubledash.exception.user.UserNotFoundException;
import com.doubledash.exception.widget.WidgetNotFoundException;
import com.doubledash.model.Widget;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.doubledash.exception.UserAlreadyExistAuthenticationException;
import com.doubledash.model.User;

public interface UserService {

	User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	void deleteUserById(Long userId) throws UserNotFoundException;

	User updateUserById(UserDTO userDTO, Long userId) throws UserNotFoundException;

	List<User> getAllUser();

	UserByIdDTO getUserById(Long userId) throws UserNotFoundException;
}
