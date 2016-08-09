package com.jxnu.spring.data.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shoumiao_yao
 * @date 2016-08-09
 */
@Component
public class jdbcTest {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void query(String sql, Object[] params) {
        this.jdbcTemplate.queryForObject(sql, Model.class);
        this.jdbcTemplate.queryForObject(sql, params, Model.class);
        this.jdbcTemplate.queryForObject(sql, new RowMapper<Model>() {
            public Model mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Model();
            }
        });
    }

}
