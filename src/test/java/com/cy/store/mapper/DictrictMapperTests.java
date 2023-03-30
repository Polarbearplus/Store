package com.cy.store.mapper;


import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//标注测试类，不会随项目一同打包
@SpringBootTest
@RunWith(SpringRunner.class)
public class DictrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> districts = districtMapper.findByParent("210100");
        for ( District d : districts)
            System.out.println(d);
    }

}

