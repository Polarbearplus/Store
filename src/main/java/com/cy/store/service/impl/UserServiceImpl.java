package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service //将当前的对象类交给Spring来管理，自动创建对象
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //调用findByUsername()判断用户名是否已经存在
        User result = userMapper.findByUsername(user.getUsername());
        //判断结果是否不为null  抛出用户名被占用异常
        if(result != null ) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //密码加密：md5算法
        //(串+password+串) -----md5加密，连续三次
        //盐值 + password + 串 ---- 盐值就是一个随机的字符串
        String oldPassword = user.getPassword();
        //随机生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //记录盐值 登录检测
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密
        String md5Password = getMD5Password(oldPassword,salt);
        //加密后密码重新补全设置到user对象中
        user.setPassword(md5Password);


        //补全数据 is_delete以及四个日志字段
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        //插入功能实现
        Integer rows = userMapper.insert(user);
        if( rows != 1 ) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        //查询是否存在
        User result = userMapper.findByUsername(username);
        if (result == null ) {
            throw new UserNotFoundException("用户不存在");
        }
        //因为密码已经被加密了，所以需要把传过来的密码进行相同的加密算法进行改造
        String newPwd = getMD5Password(password,result.getSalt());
        String oldPwd = result.getPassword();
        if ( !newPwd.equals(oldPwd) ) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断是否已被删除is_delete
        if (result.getIsDelete() == 1 ) {
            throw new UserNotFoundException("用户不存在");
        }
        //性能更高 因为传输的数据更小
        User user = new User();
        user.setAvatar(result.getAvatar());
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        return user;
    }

    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword) {
        User result = userMapper.findByUid(uid);
        if ( result == null || result.getIsDelete() == 1 ) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if ( !result.getPassword().equals(getMD5Password(oldPassword,result.getSalt()))){
            throw new PasswordNotMatchException("密码错误");
        }
        Integer rows = userMapper.updatePasswordByUid(uid,
                getMD5Password(newPassword,result.getSalt()),
                username,
                new Date());
        if ( rows != 1 ) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if ( result == null || result.getIsDelete() == 1 ) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(User user, String username, Integer uid) {
        User result = userMapper.findByUid(uid);
        if ( result == null || result.getIsDelete() == 1 ) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedTime(new Date());
        user.setModifiedUser(username);
        Integer rows = userMapper.updateInfoByUid(user);
        if ( rows != 1 ) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void updateAvatarByUid(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if ( result == null || result.getIsDelete() == 1 ) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,
                avatar,
                username,
                new Date());
        if ( rows != 1 ) {
            throw new UpdateException("更新用户头像产生未知异常");
        }
    }

    /**定义一个md5算法的加密处理*/
    private String getMD5Password(String password, String salt){
        //调用md5加密算法
        for( int i = 0; i < 3; i++ ) {
            password = DigestUtils.md5DigestAsHex((salt +password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
