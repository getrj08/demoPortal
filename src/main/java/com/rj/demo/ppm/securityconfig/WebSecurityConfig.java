package com.rj.demo.ppm.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rj.demo.ppm.interceptors.JwtRequestFilter;

@Configuration
@EnableWebSecurity(debug= true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter requestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// configure AuthenticationManager so that it knows from where to load
	// user for matching credentials
	// Use BCryptPasswordEncoder
	
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
	// We don't need CSRF for this example
		httpSecurity.csrf().disable()
	// dont authenticate this particular request
				.authorizeRequests()
					.antMatchers("/user").permitAll()
	// all other requests need to be authenticated
				.anyRequest().authenticated().and()
	// make sure we use stateless session; session won't be used to
	// store user's state.
				.exceptionHandling()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/user/authenticate","/user/register");
	}
	
}
