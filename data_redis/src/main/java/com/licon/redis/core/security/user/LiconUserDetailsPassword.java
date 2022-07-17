package com.licon.redis.core.security.user;

import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

/**
 * 用户登录后更新密码,用于升级密码使用
 * @author Licon
 */
@RequiredArgsConstructor
@Service
public class LiconUserDetailsPassword implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        /*return userRepository.findAllByUsername(userDetails.getUsername())
                .map(user -> (UserDetails)new LiconUserDetail(userRepository.save(user.withPassword(newPassword))))
                .orElse(userDetails);*/
        return new User();
    }
}
