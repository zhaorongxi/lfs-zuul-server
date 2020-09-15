package com.lfs.zuul.model;

/**
 * <p>Copyright:Copyright(c)2018</p >
 * <p>Create Date:2019/9/1 下午5:14</p >
 * <p>Modified By:linxi</p >
 * <p>Modified Date:2019/9/1 下午5:14</p >
 * @author linxi
 * @version Version 0.1
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