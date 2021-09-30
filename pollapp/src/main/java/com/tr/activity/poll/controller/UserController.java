package com.tr.activity.poll.controller;

import com.tr.activity.poll.data.UserData;
import com.tr.activity.poll.entity.user.UserInfo;
import com.tr.activity.poll.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/poll/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    private static final Logger logger= LoggerFactory.getLogger(UserController.class);


    @Operation(summary = "if you hava a new user on poll system,you can use this operation to save any user")
    @ApiResponse(responseCode = "200",content=@Content(schema = @Schema(implementation = UserInfo.class)))
    @PostMapping("/signup")
    public ResponseEntity<UserInfo> signUp(@RequestBody UserData userInfo){

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        Optional<UserInfo> optionalUserInfo = userService.register(userInfo);

        if(optionalUserInfo.isPresent()){
            logger.info("user signup on system");
            return ResponseEntity.ok(optionalUserInfo.get());
        }
        logger.info("user could not sign up on system");
        return ResponseEntity.noContent().build();

    }

}
