package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;
    @Test
    public void insert() {
        Address address = new Address();
        address.setPhone("123456789");
        address.setName("男朋友");
        addressService.addNewAddress(address,9,"管理员");
    }

    @Test
    public void setDefault() {
        addressService.setDefaultAddress(1,10,"华为");
    }

    @Test
    public void deleteAddress() {
        addressService.deleteAddress(7,10,"管理员");
    }
}
