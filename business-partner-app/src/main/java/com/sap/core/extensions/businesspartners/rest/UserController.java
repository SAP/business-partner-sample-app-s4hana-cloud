package com.sap.core.extensions.businesspartners.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.core.extensions.businesspartners.dto.UserDTO;
import com.sap.core.extensions.businesspartners.services.UserInfo;

@RestController
@RequestMapping("/v1/users")
class UserController {
	private final UserInfo userInfo;

	@Autowired
	UserController(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@GetMapping("/current")
	public ResponseEntity<UserDTO> getBusinessPartners() {
		UserDTO user = userInfo.getUser();

		return ResponseEntity.ok(user);
	}

}
