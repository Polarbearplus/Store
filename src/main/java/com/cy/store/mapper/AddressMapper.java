package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;

import java.util.Date;
import java.util.List;

/** 收货地址持久层 */
public interface AddressMapper {
    /**
     * 插入收货地址数据
     * @param address  收货地址
     * @return  受影响行数
     */
    Integer insert(Address address);

    /**
     * 根据uid查询当前用户收货地址数量
     * @param uid 用户id
     * @return 总数
     */
    Integer countByUid(Integer uid);


    List<Address> findAllByUid(Integer uid);

    /**
     * 查询当前地址是否存在
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);

    /**
     * 设置所有地址isDefault为0
     * @param uid
     * @return
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 设置当前点击地址为默认地址
     * @param aid
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateByAid(Integer aid,
                        String modifiedUser,
                        Date modifiedTime);

    /**
     * 根据地址id删除收货地址
     * @param aid 收货地址id
     * @return 受影响函数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 查找用户最后一次修改的地址
     * @param uid 用户id
     * @return 地址
     */
    Address findLastModified(Integer uid);
}
