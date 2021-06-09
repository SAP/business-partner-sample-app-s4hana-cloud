package com.sap.core.extensions.businesspartners.services.impl;

import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.SpringSecurityContext;
import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.businesspartners.dto.UserDTO;
import com.sap.core.extensions.businesspartners.services.UserInfo;

@Component
class UserInfoImpl implements UserInfo {

	public String getName() {
		return SpringSecurityContext.getToken().getLogonName();
	}

	@Override
	public UserDTO getUser() {
		Token token = SpringSecurityContext.getToken();
		return new UserDTO(token.getEmail() == null ? token.getLogonName() : token.getEmail(), token.getGivenName(),
				token.getFamilyName());
	}
}
