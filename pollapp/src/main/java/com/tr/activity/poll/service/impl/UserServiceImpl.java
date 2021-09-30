package com.tr.activity.poll.service.impl;

import com.tr.activity.poll.data.UserData;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.exception.UserAlreadyException;
import com.tr.activity.poll.repository.UserRepository;
import com.tr.activity.poll.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserInfo> register(UserData userData) throws UserAlreadyException {

        if(checkIfUserExist(userData.getUsername())){
            throw new UserAlreadyException("User already exist for this email.");
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userData,userInfo);
        return Optional.of(userRepository.save(userInfo));
    }




    private boolean checkIfUserExist(String username) {

        ExampleMatcher matcher=ExampleMatcher.matching().withIgnorePaths("id").withMatcher("username",ignoreCase());
        UserInfo entity=new UserInfo();
        entity.setUsername(username);
        Example<UserInfo> ofUser = Example.of(entity, matcher);
        return userRepository.exists(ofUser);
    }

}
