package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//标注测试类，不会随项目一同打包
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
/**
 * 单元测试方法：独立于行，不用启动整个项目
 * 1、必须@Test注解
 * 2、必须void
 * 3、参数列表不指定任何类型
 * 4、方法必须public
 */
    @Autowired
    private UserMapper userMapper;
    //报红原因：接口不能直接创建Bean的（动态代理技术解决）
    // setting->编译器->检查->Spring->Spring Core->代码->自动装配Bean
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }
    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("bear");
        System.out.println(user);
    }
    @Test
    public void findByUid(){
        User user = userMapper.findByUid(1);
        System.out.println(user);
    }
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(1,"123456","管理员",new Date());
    }
    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(1);
        user.setPhone("11111111111");
        user.setEmail("312654@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(1,
                "abc",
                "管理员",
                new Date());
    }
}
