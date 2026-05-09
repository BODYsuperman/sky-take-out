package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;
    @Override
    public User login(UserLoginDTO dto) {

         HashMap<String, String> paramMap = new HashMap<>();
         paramMap.put("appid", weChatProperties.getAppid());
         paramMap.put("secret", weChatProperties.getSecret());
         paramMap.put("js_code", dto.getCode());
         paramMap.put("grant_type", "authorization_code");
        //1.HttpClient

        String res = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        //2. response get openid


        JSONObject jsonObject = JSON.parseObject(res);

        String openid = (String) jsonObject.get("openid");

        if(openid == null){
            throw  new LoginFailedException(MessageConstant.USER_NOT_LOGIN);
        }

        //3. openid check user table

        User user = userMapper.getByOpenId(openid);
        //4. new user insert into user

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }


        //5.else , return user

        return user;
    }
}
