package com.signet.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.signet.model.Product;

import org.springframework.jdbc.core.RowMapper;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product obj = new Product();
        obj.setName(rs.getString("name"));
        
        return obj;
    }
}
