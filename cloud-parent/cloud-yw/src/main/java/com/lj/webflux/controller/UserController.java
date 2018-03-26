package com.lj.webflux.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lj.webflux.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	static Map<String, User> users = new HashMap<>();

	@PostMapping
	@ResponseBody
	public Mono<ResponseEntity<String>> add(@RequestBody User user) {
		users.put(user.getId(), user);
		return Mono.just(new ResponseEntity<String>("Post Successfully!", HttpStatus.CREATED));
	}

	@PostMapping("/post")
	public Mono<ResponseEntity<String>> postUser(@RequestBody User user) {
		users.put(user.getId(), user);
		logger.info("########### POST:" + JSON.toJSONString(user));
		return Mono.just(new ResponseEntity<>("Post Successfully!", HttpStatus.CREATED));
	}

	@GetMapping("/list")
	public Flux<User> getAll() {
		return Flux.fromIterable(users.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()));
	}

}
