package com.lfs.zuul.model;

/**
 * @ClassName: StorageRequest
 * @Description:放行配置
 * <p>Copyright:Copyright(c)2018</p >
 * <p>Create Date:2019/9/1 下午5:14</p >
 * <p>Modified By:linxi</p >
 * <p>Modified Date:2019/9/1 下午5:14</p >
 * @author linxi
 * @version Version 0.1
 */
public class StorageRequest {

    /**
     * 路径ID
     */
    private Integer id;

    /**
     * 拦截路径
     */
    private String url;
    /**
     * 拦截类型 1.服务放行  2.路径放行
     */
    private Integer interceptType;
    /**
     * 传送类型  1.不验证token放行  2.有token贼验证放行 无token则直接放行
     */
    private Integer  convey;

    public Integer getId() {
        return id;
    }

    public StorageRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public StorageRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getInterceptType() {
        return interceptType;
    }

    public StorageRequest setInterceptType(Integer interceptType) {
        this.interceptType = interceptType;
        return this;
    }

    public Integer getConvey() {
        return convey;
    }

    public StorageRequest setConvey(Integer convey) {
        this.convey = convey;
        return this;
    }

    public StorageRequest(Integer id, String url, Integer interceptType, Integer convey) {
        this.id = id;
        this.url = url;
        this.interceptType = interceptType;
        this.convey = convey;
    }

    public StorageRequest() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StorageRequest{");
        sb.append("url='").append(url).append('\'');
        sb.append(", interceptType=").append(interceptType);
        sb.append(", convey='").append(convey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
