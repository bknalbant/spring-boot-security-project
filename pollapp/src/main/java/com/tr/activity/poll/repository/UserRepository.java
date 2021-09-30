package com.tr.activity.poll.repository;

import com.tr.activity.poll.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {

    @Query("SELECT u FROM user  u WHERE  u.username = :name")
    UserInfo findUserInfoByName(@Param("name") String name);
}
