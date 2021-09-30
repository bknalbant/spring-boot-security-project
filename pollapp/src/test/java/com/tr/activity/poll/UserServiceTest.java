package com.tr.activity.poll;

import com.tr.activity.poll.data.UserData;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.repository.UserRepository;
import com.tr.activity.poll.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    static UserData userData;

    static UserInfo userInfo;

    @BeforeClass
    public static void initialize(){

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String testPasswordEncoded = encoder.encode("12345");
        userData = UserData.builder().username("demo").password(testPasswordEncoded).build();

        userInfo= UserInfo.builder().username("demo").password(testPasswordEncoded).build();
    }

    @Test
    public void should_insert_user_when_call_insert(){

        when(userRepository.exists(any(Example.class))).thenReturn(false);

        when(userRepository.save(any(UserInfo.class))).thenReturn(userInfo);

        Optional<UserInfo> userInfo = userService.register(userData);

        Assert.assertTrue(userInfo.isPresent());

        verify(userRepository,atLeastOnce()).exists(any(Example.class));

        verify(userRepository,atLeastOnce()).save(any(UserInfo.class));

        Assert.assertEquals(userData.getUsername(),userInfo.get().getUsername());

        Assert.assertNotNull(userInfo);

    }
}
