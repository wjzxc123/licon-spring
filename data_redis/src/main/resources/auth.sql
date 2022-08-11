INSERT INTO t_role (`id`, `enable`, `role_code`, `role_name`) VALUES (1, b'1', 'ROLE_USER', '普通用户');

INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (1, 'READ', '读', b'1');
INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (2, 'WRITE', '写', b'1');
INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (3, 'UPDATE', '更新', b'1');
INSERT INTO `licon`.`t_authority` (`id`, `authority`, `authority_name`, `enable`) VALUES (4, 'DELETE', '删除', b'1');

INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 1);
INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 2);
INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 3);
INSERT INTO `licon`.`t_role_authority` (`role_id`, `authority_id`) VALUES (1, 4);