package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//相当于 @Controller 以及在每一个方法上加一个
//@RequestBody  //表示此方法的响应结果以json格式进行数据的响应给前端
@RequestMapping("districts")
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService iDistrictService;
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        return new JsonResult<>(OK,iDistrictService.getByParent(parent));
    }
}
