package com.bc.zuul.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * .<br/>
 * <p>
 * Copyright: Copyright (c) 2019  zteits
 *
 * @ClassName: StorageRequestRowMapper
 * @Description:
 * @version: v1.0.0
 * @author: Dylan
 * @date: 2019-02-21 15:47
 */
public class StorageRequestRowMapper implements RowMapper<StorageRequest> {


    @Override
    public StorageRequest mapRow(ResultSet row, int rowNum) throws SQLException {
        StorageRequest sr = new StorageRequest();
        sr.setConvey(row.getInt("convey"));
        sr.setInterceptType(row.getInt("intercept_type"));
        sr.setUrl(row.getString("url"));
        return sr;
    }
}
