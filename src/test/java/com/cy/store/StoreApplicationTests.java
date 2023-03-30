package com.cy.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired //自动装配
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }
    /**
     * 数据库连接池
     * 1、jdbc：DBCP
     * 2、C3P0
     * 3.Hikari
    *HikariProxyConnection@1504912697 wrapping com.mysql.cj.jdbc.ConnectionImpl@5b47731f
    **/
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }


}
