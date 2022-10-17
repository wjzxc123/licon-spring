package com.licon.redis.core.api.controller;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/10/14 15:03
 */
@RestController
@RequestMapping("/sys")
public class ResourceController {
	@GetMapping("/hello")
	public String hello(Principal principal) {
		return "Hello," + principal.getName();
	}

	@GetMapping("/foo")
	public String foo(Principal principal) {
		return "Hello," + principal.getName();
	}

	@GetMapping("/bar")
	public String bar(Principal principal) {
		return "Hello";
	}
}
