package com.iip.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
/**
 * 
 * Shiro的配置类
 * @author windows10
 *
 */
@Configuration
public class ShiroConfig {
	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//设置Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perms： 该资源必须得到资源权限才可以访问
		 *       role: 该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterMap = new LinkedHashMap<String,String>();
		//filterMap.put("/add", "authc");
		//filterMap.put("/update", "authc");
		filterMap.put("/testThymeleaf","anon");//放行的资源要放在authc的前面
		//放行login.html页面
		filterMap.put("/login","anon");//放行的资源要放在authc的前面
		
		//授权过滤器
		//注意：当前授权拦截后，shiro会自动跳转到未授权页面
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		filterMap.put("/*", "authc");//一个目录下面的所有资源都需要认证
		//拦截成功，默认跳转到时login.jsp,可以修改默认的跳转的登录界面
		//修改调整的登录页面
		shiroFilterFactoryBean.setLoginUrl("/tologin");
		
		//设置未授权提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}
	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联Realm
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	/**
	 * 创建Realm
	 */
	@Bean("userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
	/**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}
}

