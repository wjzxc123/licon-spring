package com.licon.redis.core.api.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.licon.redis.core.util.SecurityUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
	@GetMapping("/test")
	public ResponseEntity<?> test(HttpServletRequest request, HttpServletResponse response){
		return ResponseEntity.ok().body(SecurityUtil.getCurrentLogin());
	}
}
