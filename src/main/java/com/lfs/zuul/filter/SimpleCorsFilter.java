package com.lfs.zuul.filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * CORS跨域
 *
 * <p>Copyright:Copyright(c)2018</p >
 * <p>Create Date:2019/9/1 下午5:14</p >
 * <p>Modified By:linxi</p >
 * <p>Modified Date:2019/9/1 下午5:14</p >
 * @author linxi
 * @version Version 0.1
 */
@Configuration
class SimpleCorsFilter  {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //http:www.a.com, http:www.b.com
        config.setAllowedOrigins(Arrays.asList("*"));
        //设置传递的请求头
        config.setAllowedHeaders(Arrays.asList("*"));
        //设置请求的方法，get、post
        config.setAllowedMethods(Arrays.asList("*"));
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        //针对所有请求检查跨域
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
