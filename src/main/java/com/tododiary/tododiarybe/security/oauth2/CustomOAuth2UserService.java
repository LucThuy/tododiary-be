package com.tododiary.tododiarybe.security.oauth2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tododiary.tododiarybe.entity.User;
import com.tododiary.tododiarybe.repository.IUserRepository;
import com.tododiary.tododiarybe.security.AuthProvider;
import com.tododiary.tododiarybe.security.UserPrincipal;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}

	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

		Optional<User> userOptional = userRepository.findByUsername(oAuth2UserInfo.getUsername());

		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}

		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		User user = User.builder()
				.provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
				.providerId(oAuth2UserInfo.getId()).name(oAuth2UserInfo.getName())
				.username(oAuth2UserInfo.getUsername()).avatarUri(oAuth2UserInfo.getAvatarUri())
				.password(passwordEncoder.encode("@123qwe@ewq321@")).role("ROLE_USER").build();

		return userRepository.save(user);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setName(oAuth2UserInfo.getName());
		existingUser.setAvatarUri(oAuth2UserInfo.getAvatarUri());
		return userRepository.save(existingUser);
	}

}
