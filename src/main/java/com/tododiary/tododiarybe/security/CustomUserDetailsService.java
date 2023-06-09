package com.tododiary.tododiarybe.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tododiary.tododiarybe.entity.User;
import com.tododiary.tododiarybe.repository.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
					.orElseThrow(() -> 
							new UsernameNotFoundException("User not found with username: " + username));
		
		Set<GrantedAuthority> authority =  new HashSet<GrantedAuthority>();
		authority.add(new SimpleGrantedAuthority(user.getRole()));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authority);
	}
}
