package com.licon.redis.core.security.user;


import java.util.List;


import com.licon.redis.core.entity.Authority;
import com.licon.redis.core.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 13:16
 */
@Getter
@Setter
public class LiconUserDetail extends User implements UserDetails {
	private static final long serialVersionUID = 8605035861075499152L;
	private List<Authority> authorities;

	public LiconUserDetail(Long id, String username, String password, int sex, boolean accountExpired, boolean accountLocked, boolean credentialsExpired, boolean enable, List<Authority> authorities) {
		super(id, username, password, sex, accountExpired, accountLocked, credentialsExpired, enable);
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsExpired();
	}

	@Override
	public boolean isEnabled() {
		return isEnable();
	}
}
