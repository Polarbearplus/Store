package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UserNotFoundException;
import com.cy.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("zhibao");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK!");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("p05","123");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(16,"管理员","123456","123");
    }
    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(1));

    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setGender(1);
        user.setEmail("654321@qq.com");
        user.setPhone("10987654321");
        userService.changeInfo(user,"管理员",2);
    }

    @Test
    public void changeAvatar() {
        userService.updateAvatarByUid(2,"jkl","管理员");
    }
}
