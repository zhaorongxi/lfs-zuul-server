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
 * <p>Copyright:Copyright(c)2018</p >
 * <p>Create Date:2019/9/1 下午5:14</p >
 * <p>Modified By:linxi</p >
 * <p>Modified Date:2019/9/1 下午5:14</p >
 * @author linxi
 * @version Version 0.1
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
