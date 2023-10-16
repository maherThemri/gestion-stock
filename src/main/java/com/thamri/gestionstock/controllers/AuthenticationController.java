package com.thamri.gestionstock.controllers;

import static com.thamri.gestionstock.utils.Constants.AUTHENTICATION_ENDPOINT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.dto.auth.AuthenticationRequest;
import com.thamri.gestionstock.dto.auth.AuthenticationResponse;
import com.thamri.gestionstock.model.auth.ExtendedUser;
import com.thamri.gestionstock.services.auth.ApplicationUserDetailsService;
import com.thamri.gestionstock.utils.JwtUtil;

@RestController
@RequestMapping(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ApplicationUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);
		return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
	}

}
