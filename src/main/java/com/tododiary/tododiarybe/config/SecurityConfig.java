package com.tododiary.tododiarybe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tododiary.tododiarybe.security.JwtAuthenticationEntryPoint;
import com.tododiary.tododiarybe.security.JwtAuthenticationFilter;
import com.tododiary.tododiarybe.security.oauth2.CustomOAuth2UserService;
import com.tododiary.tododiarybe.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.tododiary.tododiarybe.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.tododiary.tododiarybe.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeHttpRequests((authorize) -> {
			try {
				authorize.requestMatchers(HttpMethod.GET, "/files/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/file/download/**").permitAll()
						.requestMatchers("/api/auth/**").permitAll().requestMatchers("/oauth2/**").permitAll()
						.anyRequest().authenticated().and().oauth2Login().authorizationEndpoint()
						.baseUri("/oauth2/authorize")
						.authorizationRequestRepository(cookieAuthorizationRequestRepository()).and()
						.redirectionEndpoint().baseUri("/oauth2/callback/*")
						.and().userInfoEndpoint().userService(customOAuth2UserService)
						.and().successHandler(oAuth2AuthenticationSuccessHandler)
						.failureHandler(oAuth2AuthenticationFailureHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
