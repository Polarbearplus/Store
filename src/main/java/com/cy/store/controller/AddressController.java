package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.entity.BaseEntity;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//相当于 @Controller 以及在每一个方法上加一个
//@RequestBody  //表示此方法的响应结果以json格式进行数据的响应给前端
@RequestMapping("address")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;
    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address,
                                          HttpSession session){
        addressService.addNewAddress(address,
                getUidFromSession(session),
                getUsernameFromSession(session));
        System.out.println(address);
        return new JsonResult<>(OK);
    }
    @RequestMapping({"","/"})
    public JsonResult<List<Address>> findByUid(HttpSession session){
        return new JsonResult<>(OK,
                addressService.getByUid(
                        getUidFromSession(session)));
    }
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefaultAddress(@PathVariable("aid") Integer aid, HttpSession session) {
        //System.out.println(aid);
        addressService.setDefaultAddress(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> deleteAddress(@PathVariable("aid") Integer aid,
                                          HttpSession session){
        addressService.deleteAddress(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}
