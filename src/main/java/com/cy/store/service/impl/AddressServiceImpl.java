package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    //在添加用户的收货地址的业务层依赖于IDistrictService的业务层接口
    @Autowired
    private IDistrictService districtService;
    @Value("${user-address-max-count}")
    private Integer maxCount;
    @Override
    public void addNewAddress(Address address, Integer uid, String username) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址超出上限");
        }

        //address对象中的数据进行补全
        address.setProvinceName(districtService.getNameByCode(address.getProvinceCode()));
        address.setCityName(districtService.getNameByCode(address.getCityCode()));
        address.setAreaName(districtService.getNameByCode(address.getAreaCode()));

        address.setUid(uid);
        Integer isDelete = count == 0 ? 1 : 0;
        address.setIsDefault(isDelete);
        address.setCreatedUser(username);
        address.setModifiedTime(new Date());
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        Integer rows = addressMapper.insert(address);
        if ( rows != 1 ) {
            throw new InsertException("插入地址产生未知的异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> addresses = addressMapper.findAllByUid(uid);
        return addresses;
    }

    /**
     * 修改某条地址为默认地址
     * @param aid
     * @param uid
     * @param username
     */
    @Override
    public void setDefaultAddress(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("该地址不存在");
        }
        if ( !result.getUid().equals(uid) ) {
            throw new AccessDeniedException("非法访问");
        }
        if (addressMapper.updateNonDefault(uid) < 1 ) {
            throw new UpdateException("更新数据产生未知异常");
        }
        if (addressMapper.updateByAid(aid,username,new Date()) != 1 ) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }

    @Override
    public void deleteAddress(Integer aid, Integer uid, String username) {
        Address res = addressMapper.findByAid(aid);
        if ( res == null ) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        if ( !res.getUid().equals(uid) ) {
            throw new AccessDeniedException("非法访问数据");
        }
        if ( addressMapper.deleteByAid(aid) != 1) {
            throw new DeleteException("删除时产生未知异常");
        }
        System.out.println(res);
        if ( res.getIsDefault() == 1 ) {
            if ( addressMapper.countByUid(uid) == 0 ) {
                return;
            }
            Address address = addressMapper.findLastModified(uid);
            if (addressMapper.updateByAid(address.getAid(),username,new Date()) != 1) {
                throw new UpdateException("更新数据产生未知异常");
            }
        }
    }

}
