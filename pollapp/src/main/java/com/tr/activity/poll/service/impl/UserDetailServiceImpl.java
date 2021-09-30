package com.tr.activity.poll.service.impl;

import com.tr.activity.poll.entity.user.MyUserDetails;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userRepository.findUserInfoByName(username);

        if(userInfo==null){
            throw new UsernameNotFoundException("Could not found this user "+username);
        }

        return new MyUserDetails(userInfo);

    }

}
