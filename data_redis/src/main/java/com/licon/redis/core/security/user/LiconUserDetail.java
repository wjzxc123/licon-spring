package com.licon.redis.core.security.user;


import java.util.Collection;
import com.licon.redis.core.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 13:16
 */
@Getter
@Setter
public class LiconUserDetail implements UserDetails {
	private static final long serialVersionUID = 8605035861075499152L;

	private User user;
	public LiconUserDetail(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !user.isAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.isAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !user.isCredentialsExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnable();
	}
}
