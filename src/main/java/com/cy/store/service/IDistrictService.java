package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据父代号查询地区信息
     * @param parent  父代码
     * @return  多个区的信息
     */
    List<District> getByParent(String parent);
    String getNameByCode(String code);
}
