package com.licon.redis.core.security.authority;

import java.util.Collection;
import java.util.List;

import com.licon.redis.core.entity.Url;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * Describe: 用来给不同的url分配不同的投票百分比
 *
 * @author Licon
 * @date 2022/7/4 9:51
 */
public class LiconAccessDecisionManager extends AbstractAccessDecisionManager {
	private final AffirmativeBased affirmativeBased;
	private final UnanimousBased unanimousBased;
	private final ConsensusBased consensusBased;
	public LiconAccessDecisionManager(List<AccessDecisionVoter<?>> decisionVoters) {
		super(decisionVoters);
		consensusBased = new ConsensusBased(decisionVoters);
		affirmativeBased = new AffirmativeBased(decisionVoters);
		unanimousBased = new UnanimousBased(decisionVoters);
	}

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		for (ConfigAttribute configAttribute : configAttributes) {
			LiconConfigAttribute liconConfig = (LiconConfigAttribute) configAttribute;
			Url url = liconConfig.getUrl();
			switch (url.getVoteType()){
				case Affirmative: affirmativeBased.decide(authentication, object, configAttributes);break;
				case Unanimous: unanimousBased.decide(authentication, object, configAttributes);break;
				case ConsensusBased: consensusBased.decide(authentication, object, configAttributes);break;
				default: throw new AccessDeniedException(this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
			}
		}
	}
}
