package com.doubledash.service;

import java.time.LocalDate;
import java.util.*;

import com.doubledash.dto.*;
import com.doubledash.exception.user.UserNotFoundException;
import com.doubledash.exception.widget.WidgetNotFoundException;
import com.doubledash.model.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.doubledash.exception.OAuth2AuthenticationProcessingException;
import com.doubledash.exception.UserAlreadyExistAuthenticationException;
import com.doubledash.model.Role;
import com.doubledash.model.User;
import com.doubledash.repo.RoleRepository;
import com.doubledash.repo.UserRepository;
import com.doubledash.security.oauth2.user.OAuth2UserInfo;
import com.doubledash.security.oauth2.user.OAuth2UserInfoFactory;
import com.doubledash.util.GeneralUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("Le user ID " + signUpRequest.getUserID() + " existe déjà");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("Un user avec l'email " + signUpRequest.getEmail() + " existe déjà");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		userRepository.flush();
		return user;
	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setEnabled(true);
		user.setProviderUserId(formDTO.getProviderUserId());
		return user;
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	@Override
	public void deleteUserById(Long userId) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}
		Set<User> userSet = roleRepository.findById(1L).get().getUsers();
		for (User u : roleRepository.findById(1L).get().getUsers()) {
			if (u.getId().equals(userId)){
				userSet.remove(u);
			}
		}
		roleRepository.findById(1L).get().setUsers(userSet);
		
		userRepository.deleteById(userId);
	}

	@Override
	public User updateUserById(UserDTO userDTO, Long userId) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}
		userRepository.findById(userId).map(user ->{
			user.setDisplayName(userDTO.getDisplayName());
			user.setEmail(userDTO.getEmail());
			user.setModifiedDate(java.sql.Date.valueOf(LocalDate.now()));
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			return userRepository.save(user);
				}
		);
		return null;
	}

	@Override
	public UserByIdDTO getUserById(Long userId) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}

		UserByIdDTO userDTO = new UserByIdDTO();

		userRepository.findById(userId).map(user ->{
			userDTO.setId(user.getId());
			userDTO.setDisplayName(user.getDisplayName());
			userDTO.setEmail(user.getEmail());
			userDTO.setPassword(passwordEncoder.encode(user.getPassword()));
			return userDTO;
				}
		);
		return userDTO;
	}

	@Override
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<>();
		for (User o : userRepository.findAll()) {
			userList.add(o);
		}
		return userList;
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
}
