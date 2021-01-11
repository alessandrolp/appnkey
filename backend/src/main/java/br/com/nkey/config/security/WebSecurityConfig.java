package br.com.nkey.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import br.com.nkey.config.security.token.JwtConfiguration;
import br.com.nkey.config.security.token.TokenConverter;
import br.com.nkey.config.security.token.TokenFactory;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private TokenFactory tokenFactory;
	private TokenConverter tokenConverter;
	private JwtConfiguration jwtConfiguration;

	public WebSecurityConfig(
			JwtConfiguration jwtConfiguration,
			UserDetailsService userDetailsService,
			TokenFactory tokenFactory,
			TokenConverter tokenConverter) {
		this.jwtConfiguration = jwtConfiguration;
		this.userDetailsService = userDetailsService;
		this.tokenFactory = tokenFactory;
		this.tokenConverter = tokenConverter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.cors().configurationSource(request -> {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
				corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
				corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
				return corsConfiguration;
			})
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint((req, resp, ex) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and().authorizeRequests()
				.antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario").permitAll()
				.antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**","/**/v2/api-docs/**").permitAll()
				.antMatchers("/**").hasRole("ADMIN").anyRequest().authenticated()
				.and()
				.addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenFactory))
				.addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
