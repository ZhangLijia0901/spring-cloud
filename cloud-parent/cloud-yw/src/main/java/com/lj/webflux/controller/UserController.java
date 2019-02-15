package com.lj.webflux.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lj.webflux.entity.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/user")
public class UserController {

//	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	static Map<String, User> users = new HashMap<>();

//	@PostMapping
//	@ResponseBody
//	public Mono<ResponseEntity<String>> add(@RequestBody User user) {
//		users.put(user.getId(), user);
//		return Mono.just(new ResponseEntity<String>("Post Successfully!", HttpStatus.CREATED));
//	}
//
//	@PostMapping("/post")
//	public Mono<ResponseEntity<String>> postUser(@RequestBody User user) {
//		users.put(user.getId(), user);
//		logger.info("########### POST:" + JSON.toJSONString(user));
//		return Mono.just(new ResponseEntity<>("Post Successfully!", HttpStatus.CREATED));
//	}
//
//	@GetMapping("/list")
//	public Flux<User> getAll() {
//		return Flux.fromIterable(users.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()));
//	}

	@PostMapping
	@ResponseBody
	@ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
	public ResponseEntity<String> add(@RequestBody User user) {
		users.put(user.getId(), user);
		return new ResponseEntity<String>("Post Successfully!", HttpStatus.CREATED);
	}

	@GetMapping("/list")
	@ApiOperation(value = "获取用户列表", notes = "")
	public Collection<User> getAll() {
		return users.values();
	}

}
