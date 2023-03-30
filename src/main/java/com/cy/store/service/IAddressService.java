package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

public interface IAddressService {
    /**
     *  添加新收货地址
     * @param address 地址
     * @param uid   用户id
     * @param username 用户username
     */
    void addNewAddress(Address address, Integer uid, String username);

    List<Address> getByUid(Integer uid);

    void setDefaultAddress(Integer aid, Integer uid, String username);

    /**
     * 删除用户选中的收货地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 用户名
     */
    void deleteAddress(Integer aid,
                       Integer uid,
                       String username);
}
