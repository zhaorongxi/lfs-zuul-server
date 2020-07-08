package com.bc.zuul.fallBack;

import com.alibaba.fastjson.JSON;
import com.bc.base.dto.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * hystrix回退配置
 * zuul调用服务失败时，统一返回
 * {
 * "resultCode": "9999",
 * "resultMsg": "服务暂不可用"
 * }
 *
 * @ClassName BaseProviderFallBack
 * <p>Copyright:Copyright(c)2018</p >
 * <p>Create Date:2019/9/1 下午5:14</p >
 * <p>Modified By:linxi</p >
 * <p>Modified Date:2019/9/1 下午5:14</p >
 * @author linxi
 * @version Version 0.1
 */
@Component
public class BaseProviderFallBack implements FallbackProvider {

    private static Logger log = LoggerFactory.getLogger(BaseProviderFallBack.class);

    @Override
    public String getRoute() {
        //代表所有的服务调用失败之后，都执行此回退处理
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {
            //do something
            }

            @Override
            public InputStream getBody() throws IOException {
                log.error("BaseProviderFallBack body method记录");
                return new ByteArrayInputStream(JSON.toJSON(ResultObject.serverErrorObject("服务维护中...")).toString().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }


}
