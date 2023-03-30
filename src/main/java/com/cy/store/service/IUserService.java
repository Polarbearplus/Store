package com.cy.store.service;

import com.cy.store.entity.User;

/** 用户模块业务层接口*/
public interface IUserService {
    /**
     * 注册方法
     * @param user 用户数据
     */
    void reg(User user);
    /**
     * 用户登录
     * @param username 用户名
     * @param password  用户密码
     * @return  当前匹配的用户数据，如果没有则返回null值
     */
    User login(String username, String password);

    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    User getByUid(Integer uid );

    /**
     * 更新用户数据操作
     * @param user  更新信息
     * @param username  用户名
     * @param uid   用户id
     */
    void changeInfo(User user,
                    String username,
                    Integer uid);

    /**
     * 修改用户头像
     * @param uid      用户id
     * @param avatar    用户头像路径
     * @param username  用户名称
     */
    void updateAvatarByUid(Integer uid,
                           String avatar,
                           String username);
}