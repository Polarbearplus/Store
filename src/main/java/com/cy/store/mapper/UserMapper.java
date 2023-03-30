package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/** 用户模块的持久层*/

public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响行数（增删改查都用受影响行数作为返回）
     */
    Integer insert(User user);
    /**
     * 根据用户名查询
     * @param username
     * @return 找到了就返回用户数据，没有就返回null
     */
    User findByUsername(String username);

    User findByUid(Integer uid);

    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);
    Integer updateInfoByUid(User user);

    Integer updateAvatarByUid(Integer uid,
                           String avatar,
                           String modifiedUser,
                           Date modifiedTime);
/**
 * @Param注解是把uid数据注入注解内userId内用在后端#{uid}-->#{userId}
 * 一般当SQL语句的占位符和映射接口方法不一样时，
 * 需要将某个参数强行注入到某个占位符上
    Integer setAvatarByUid(@Param("userId") Integer uid,
                           @Param("Avatar") String avatar,
                           @Param("") String modifiedUser,
                           @Param("") Date modifiedTime);
                           */
}
