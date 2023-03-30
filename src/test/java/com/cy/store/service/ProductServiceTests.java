package com.cy.store.service;

import com.cy.store.entity.District;
import com.cy.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    private IProductService productService;

    @Test
    public void getHostList() {
        List<Product> list = productService.findHotList();
        for (Product d : list) {
            System.out.println(d);
        }
    }
    @Test
    public void findById() {
        System.out.println(productService.findById(10000022));
    }
}
