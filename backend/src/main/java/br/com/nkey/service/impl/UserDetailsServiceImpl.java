package br.com.nkey.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nkey.domain.Usuario;
import br.com.nkey.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		log.info("Pesquisando no banco de dados o usuario pelo email '{}'", email);
		
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário não encontrado pelo email informado '%s'", email)));
		
		log.info("Usuário encontrado '{}'", usuario);
		
		return new CustomUserDetails(usuario);
	}
	
	private static final class CustomUserDetails extends Usuario implements UserDetails {
		
		private static final long serialVersionUID = 8404445530136903817L;

		CustomUserDetails(Usuario applicationUser) {
			super(applicationUser);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + this.getPermissao());
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public String getPassword() {
			return this.getSenha();
		}

		@Override
		public String getUsername() {
			return this.getEmail();
		}

	}

}
