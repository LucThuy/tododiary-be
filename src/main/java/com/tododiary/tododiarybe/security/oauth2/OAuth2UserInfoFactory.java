package com.tododiary.tododiarybe.security.oauth2;

import java.util.Map;

import com.tododiary.tododiarybe.security.AuthProvider;

public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        
        return null;
    }
}
