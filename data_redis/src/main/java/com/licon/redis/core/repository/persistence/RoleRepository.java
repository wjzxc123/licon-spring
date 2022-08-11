package com.licon.redis.core.repository.persistence;

import java.util.Optional;

import com.licon.redis.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/11 9:42
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

	/**
	 * 根据角色编码查找角色  #{#entityName}
	 * @param roleCode
	 * @return
	 */
	@Query(value = "select  r.id, enable, role_code, role_name from t_role r where r.role_code = ?1 and r.enable = 1" ,nativeQuery = true)
	Optional<Role> findOptionalByRoleCode(String roleCode);
}
