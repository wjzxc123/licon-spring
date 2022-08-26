INSERT INTO t_role (`id`, `enable`, `role_code`, `role_name`) VALUES (1, b'1', 'ROLE_USER', '普通用户');

INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (1, 'READ', '读', b'1');
INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (2, 'WRITE', '写', b'1');
INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (3, 'UPDATE', '更新', b'1');
INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (4, 'DELETE', '删除', b'1');

INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 1);
INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 2);
INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 3);
INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 4);

INSERT INTO `licon`.`t_user` (`id`, `account_non_expired`, `account_non_locked`, `code_mfa_key`, `credentials_non_expired`, `email`, `enabled`, `mfa_type`, `mobile`, `name`, `password_hash`, `sex`, `sms_mfa_key`, `username`, `using_mfa`) VALUES (1, b'1', b'1', 'iNAia8q8OHafJpTRQFfy56RAMXgFcdlp8tIbHgvo9xw=', b'1', 'licon@qq.com', b'1', 0, '17393121300', 'licon', '{bcrypt}$2a$10$/kvGXt3Js9WOApay8QaM4ehjou1i7.Nw5zvwLj7JjalUOjlt5CDuC', 0, 'BGWugfdLw5v13WNq8Rtnl4UqRTnPmWQ1o0EmGYlzhOA=', 'admin', b'1');
