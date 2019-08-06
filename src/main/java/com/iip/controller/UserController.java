package com.iip.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	/**
	 * 测试controller
	 */
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		System.out.println("UserController.hello()");
		return "ok";
	}
	@RequestMapping("/add")
	public String add() {
		return "/user/add";
	}
	@RequestMapping("/update")
	public String update() {
		return "/user/update";
	}
	@RequestMapping("/tologin")
	public String tologin() {
		return "/login";
	}
	@RequestMapping("/noAuth")
	public String noAuth(){
		return "/noAuth";
	}
	
	/**
	 * 测试thymeleaf
	 */
	@RequestMapping("/testThymeleaf")
	public String testThymeleaf(Model model){
		model.addAttribute("name","gcy");
		//返回test.html
		return "test";
	}
	
	/**
	 * 登录逻辑处理
	 */
	@RequestMapping("/login")
	private String login(String name,String password,Model model) {
		/**
		 * 使用Shiro编写认证操作
		 */
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name,password);
		//3.执行登录方法
		try {
			subject.login(token);//只有执行login方法，就会走UserRealm流程
			//登陆成功
			return "redirect:/testThymeleaf";
		} catch (UnknownAccountException e) {
			//e.printStackTrace();
			//登录失败
			model.addAttribute("msg","用户名不存在");
			return "login";
		}catch (IncorrectCredentialsException e) {
			model.addAttribute("msg","密码错误");
			return "login";
		}
	}
}
