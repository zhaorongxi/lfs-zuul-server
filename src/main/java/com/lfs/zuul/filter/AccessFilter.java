package com.lfs.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.lfs.base.dto.ResultObject;
import com.lfs.base.enums.ErrorCodeEnum;
import com.lfs.cache.redis.base.CommonCache;
import com.lfs.cache.redis.base.StringCache;
import com.lfs.interfaces.common.RedisConstants;
import com.lfs.interfaces.common.ShareConstants;
import com.lfs.interfaces.model.dto.TokenDateModelDTO;
import com.lfs.zuul.model.ConveyEnums;
import com.lfs.zuul.model.InterceptEnums;
import com.lfs.zuul.model.StorageRequest;
import com.lfs.zuul.model.StorageRequestRowMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 鉴权过滤器
 *
 * <p>Copyright:Copyright(c)2018</p >
 * <p>Create Date:2019/9/1 下午5:14</p >
 * <p>Modified By:linxi</p >
 * <p>Modified Date:2019/9/1 下午5:14</p >
 * @author linxi
 * @version Version 0.1
 */
@Configuration
public class AccessFilter extends ZuulFilter {


	@Autowired
	private CommonCache commonCache;

	@Autowired
	private StringCache stringCache;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(200);
		ctx.getResponse().setContentType("application/json;charset=utf-8");
		HttpServletRequest request = ctx.getRequest();
		// 取token
		String accessToken = request.getHeader("Authorization");



		// 如果token为null则告诉•
		if (StringUtils.isBlank(accessToken)) {
			log.warn("access token is empty");
			ctx.setResponseBody(
					JSONObject.toJSONString(ResultObject.customMessage(
							ErrorCodeEnum.NULL_TOKEN.getCode(), ErrorCodeEnum.NULL_TOKEN.getMsg())));
		} else {
			log.info("token不为空!");
			issuedUserId(ctx,accessToken);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestUrl = request.getRequestURI().toLowerCase();
        log.info("进入访问过滤器，访问的url:{}，访问的方法：{},截切后缀:{}",request.getRequestURL(),request.getMethod(),requestUrl);
		Boolean bool = intercepUrl(ctx,requestUrl,request.getHeader("Authorization"));
		return bool ;
	}

	@Override
	public int filterOrder() {

		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}


	private void issuedUserId(RequestContext ctx,String accessToken) {
		String userId =getUserId(accessToken);
		if(StringUtils.isNotBlank(userId)){

			ctx.addZuulRequestHeader("userId",userId);
			setResponse(ctx,true,200,false,null);
;		}else{
			setResponse(ctx,false,200,false,JSONObject.toJSONString(
					ResultObject.customMessage(
							ErrorCodeEnum.TOKEN_FAIL.getCode(),ErrorCodeEnum.TOKEN_FAIL.getMsg())));
		}
	}

	public  Boolean intercepUrl(RequestContext ctx,String url,String accessToken){
		String sql = "select * from lfs_storage_request";
		RowMapper<StorageRequest> storageRequest = new StorageRequestRowMapper();
		List<StorageRequest> urlList =  jdbcTemplate.query(sql,storageRequest);
		for (int i = 0; i < urlList.size(); i++) {
			//服务放行
			if (urlList.get(i).getInterceptType().equals(InterceptEnums.SERVER_RELASE.getMeaning())
					&& url.toLowerCase().startsWith(urlList.get(i).getUrl().toLowerCase())) {
				return false;
				//URL放行 （但不验证）
			}
			else if (urlList.get(i).getInterceptType().equals(InterceptEnums.URL_RELASE.getMeaning())
					&& urlList.get(i).getConvey().equals(ConveyEnums.NO_CHECK_TOKEN.getMeaning())
					&& urlList.get(i).getUrl().toLowerCase().equals(url)
			) {
				return false;
				//URL放行  都需要验证
			}else if (urlList.get(i).getInterceptType().equals(InterceptEnums.URL_RELASE.getMeaning())
					&& urlList.get(i).getConvey().equals(ConveyEnums.CHECK_TOKEN_ANYWAY.getMeaning())
					&& urlList.get(i).getUrl().toLowerCase().equals(url)){
				String userId 	=getUserId(accessToken);
				ctx.addZuulRequestHeader("userId",userId);
				return false;
			}
		}
		return  true;
	}

	/**
	 * 获取用户ID
	 * @param accessToken
	 * @return
	 */
	public  String getUserId(String accessToken) {
		String userId= "";
		if (StringUtils.isNotBlank(accessToken)) {
			TokenDateModelDTO tokenDateModelDTO = parsingToken(accessToken);
			if (tokenDateModelDTO.getUserId() != null) {

				userId = String.valueOf(tokenDateModelDTO.getUserId());
			}
		}
		return  userId;
	}

	/**
	 * 解析token
	 * @param token
	 * @return
	 */
	public TokenDateModelDTO parsingToken(String token) {
		TokenDateModelDTO model = new TokenDateModelDTO();
		if(commonCache.hasKey(RedisConstants.JWT_TOKEN+token)){
			model = (TokenDateModelDTO) stringCache.get(RedisConstants.JWT_TOKEN+token);
			commonCache.expire(RedisConstants.JWT_TOKEN +token, ShareConstants.PC_TOKEN_EXPIRE);
		}
		return model;
	}


	/**
	 * 放行配置
	 * @param ctx
	 * @param sendZuulResp 对请求进行路由  true:路由  false:不路由
	 * @param statusCode 返回code
	 * @param isSucess  下一个拦截器是否需要再次拦截
	 * @param responseBody 内容
	 */
	private void setResponse(RequestContext ctx, boolean sendZuulResp, int statusCode, boolean isSucess, String responseBody) {
		ctx.setSendZuulResponse(sendZuulResp);
		ctx.setResponseStatusCode(statusCode);
		ctx.set("isSuccess", isSucess);
		if (StringUtils.isNotBlank(responseBody)) {
			ctx.setResponseBody(responseBody);
		}
	}

}
