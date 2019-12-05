package com.example.zipkin.springbootzipkin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {
	
	@GetMapping(path="/hello")
	public String getMessage() {
		return "Hello World..!";
	}

}
