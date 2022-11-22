package com.doubledash.controller;

import com.doubledash.dto.UserByIdDTO;
import com.doubledash.dto.UserDTO;
import com.doubledash.exception.user.UserNotFoundException;
import com.doubledash.exception.widget.WidgetNotFoundException;
import com.doubledash.model.User;
import com.doubledash.model.Widget;
import com.doubledash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.doubledash.config.CurrentUser;
import com.doubledash.dto.LocalUser;
import com.doubledash.util.GeneralUtils;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getContent() {
		return ResponseEntity.ok("Public content");
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAdminContent() {
		return ResponseEntity.ok("Admin content");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllUser")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/getUser/{userId}")
	public UserByIdDTO getUserById(@PathVariable(value = "userId") Long userId) throws UserNotFoundException {
		return userService.getUserById(userId);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUserById(@PathVariable(value = "userId") Long userId) throws UserNotFoundException {
		userService.deleteUserById(userId);
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@PutMapping("/{userId}")
	public UserDTO updateUserById(@RequestBody UserDTO userDTO, @PathVariable Long userId) throws UserNotFoundException {
		userService.updateUserById(userDTO, userId);
		return userDTO;
	}

}