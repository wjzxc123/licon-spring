package com.licon.redis.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/27 17:48
 */
@Controller
public class LoginController {
	@GetMapping("/login")
	public String getLogin(){
		return "login";
	}
}
