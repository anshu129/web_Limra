package com.limrainfracon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.limrainfracon.config.CustomAuthenticationFailureHandler;
import com.limrainfracon.config.CustomAuthenticationSuccessHandler;
import com.limrainfracon.config.PasswordEncoderConfig;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private PasswordEncoderConfig passwordEncoderConfig;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, ApplicationContext ctx) throws Exception {
	    UserDetailsService userDetailsService = ctx.getBean(UserDetailsService.class);
	    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderConfig.passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers("/css/**", "/js/**", "/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/signup", "/loginPage", "/user/saveUser", "/", "/sendConcernEmail").permitAll()
			.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
			.antMatchers("/user/**").hasAnyAuthority("L1","ADMIN","SUPER_USER")
			.anyRequest().authenticated().and().formLogin().loginPage("/loginPage")
			.loginProcessingUrl("/user/userLogin") // Specify the login URL
			.usernameParameter("loginId") // Specify the username parameter
			.passwordParameter("password").successHandler(customAuthenticationSuccessHandler)// Specify the password parameter
			.failureHandler(new CustomAuthenticationFailureHandler()).permitAll().and().logout()
			.logoutSuccessUrl("/loginPage") // Redirect to login page after logout
			.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
	}
}
