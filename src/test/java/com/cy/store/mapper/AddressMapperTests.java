package com.cy.store.mapper;


import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//标注测试类，不会随项目一同打包
@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(10);
        address.setPhone("3195998299");
        address.setName("女朋友");
        addressMapper.insert(address);
    }
    @Test
    public void countByUid(){
        Integer s = addressMapper.countByUid(10);
        System.out.println(s);
    }

    @Test
    public void getByUid() {
        List<Address> districts = addressMapper.findAllByUid(10);
        for ( Address d : districts)
            System.out.println(d);
    }
    @Test
    public void getByAid() {
        System.out.println(addressMapper.findByAid(1));
    }
    @Test
    public void getByBid() {
        System.out.println(addressMapper.updateNonDefault(10));
    }
    @Test
    public void getByCid() {
        System.out.println(addressMapper.updateByAid(1,"管理者",new Date()));
    }

    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(1);
    }

    @Test
    public void selectLastByUid() {
        System.out.println(addressMapper.findLastModified(10));
    }
}
