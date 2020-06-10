package com.bc.zuul.model;

/**
 * .<br/>
 * <p>
 * Copyright: Copyright (c) 2019  zteits
 *
 * @ClassName: InterceptEnums
 * @Description:
 * @version: v1.0.0
 * @author: Dylan
 * @date: 2019-02-21 15:57
 */
public enum  InterceptEnums {

    /**
     * 服务方形
     */
    SERVER_RELASE(1),

    URL_RELASE(2);

    private Integer meaning;

    InterceptEnums(Integer meaning) {
        this.meaning = meaning;
    }

    public Integer getMeaning() {
        return meaning;
    }
}