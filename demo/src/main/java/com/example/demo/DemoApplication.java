package com.example.demo;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {

	// 整个项目的入口函数
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	// @GetMapping会在接收到get类型的url时进行匹配
//	@GetMapping("/hello")
//	public String hello_test(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return String.format("Hello %s!", name);
//	}
//
//	@GetMapping("/info")
//	// 两种传参方式，通过@RequestParam注解标注形参列表，或者使用自定义的对象传参
//	// value是在url中?后赋值时使用的名称
//	public String info_test(@RequestParam(value = "id", defaultValue = "2017013579") String my_id,
//					   @RequestParam(value = "data", defaultValue = "complaint...") String my_data) {
//		TestController controller = new TestController();
//		TestController.Info my_info = controller.new Info();
//		my_info.setId(my_id);
//		my_info.setData(my_data);
//		// return my_info;	// 错误，必须返回一个String.format
//		// return String.format("My id is %s", my_info.id);
//		return String.format("My data is %s", my_info.getData());
//	}
//
////	@ResponseBody
////	@GetMapping(name = "/json", produces = "application/json;charset=UTF-8")
////	// 返回json的方法：通过@ResponseBody注解与produces的说明，设定返回值的类型
////	// 关于@RequestParam和@RequestBody：Param用来处理Content-Type或简单对象而Body处理非Content-Type。由于GET请求中没有HttpEntity故Body不适用
////	// 一个请求可以包括多个Param，但只能有一个Body
////	// public JSONObject json_test(@RequestBody JSONObject jsonParam) {
////	public JSONObject json_test(@RequestParam(value = "example", defaultValue = "Perhaps OK now?") String jsonParam) {
////		JSONObject result = new JSONObject();
////		try {
////			result.put("msg", "OK!");
////			result.put("data", jsonParam);
////		} catch(JSONException e) {
////			// 捕捉异常，但是什么都不做
////		}
////		return result;
////	}
//	@RequestMapping("/getInfo")
//	public TestController.Info showJson() {
//		TestController controller = new TestController();
//		TestController.Info my_info = controller.new Info();
//		my_info.setId("123456");
//		my_info.setData("654321");
//		return my_info;
//	}

	// TODO: 需要改成PostMapping
	// 用户注册，请求类型POST，前端提供用户名、用户密码、学号，后端返回状态码和用户id
	@PostMapping("/register")
	public User registerUser(@RequestParam(value = "Username", defaultValue = "default username") String username,
							 @RequestParam(value = "Password", defaultValue = "default password") String password,
							 @RequestParam(value = "ID", defaultValue = "default ID") String id) {
		User newUser = new User();
		newUser.setUserName(username);
		newUser.setPassWord(password);
		newUser.setStudentId(id);
		return newUser;
		// TODO: 如果不返回newUser的话，可能需要创建一个responseMessage类专门用来描述返回信息
	}

	// 用户登录，请求类型POST，前端提供用户名、用户密码，后端返回状态码、用户id
	@PostMapping("/login")
	public User loginUser(@RequestParam(value = "Username", defaultValue = "") String username) {
		return new User();
	}

	// 查询用户信息，请求类型GET，前端提供用户id，后端返回用户信息
	@GetMapping("/info")
	public User infoUser(@RequestParam(value = "Userid", defaultValue = "") String userid) {
		return new User();
	}

	// 修改个人信息，请求类型POST，前端提供新的个人信息，后端返回状态码
	@PostMapping("/change")
	public User changeUser(@RequestParam(value = "newInfo") JSONObject newInfo) {
		return new User();
	}

	// 考虑到这样下去url可能太多了，可能会有API合并代表一类操作，然后根据具体的某个参数取值来区分
}