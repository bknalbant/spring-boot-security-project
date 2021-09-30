package com.tr.activity.poll.repository;

import com.tr.activity.poll.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
