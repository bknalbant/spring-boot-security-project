package com.tr.activity.poll.service;

import com.tr.activity.poll.data.UserData;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.exception.UserAlreadyException;

import java.util.Optional;

public interface UserService {

    public Optional<UserInfo> register(UserData userData) throws UserAlreadyException;
}
