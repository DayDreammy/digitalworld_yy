package edu.tongji.cc.digitalworld.api;

import java.util.concurrent.atomic.AtomicLong;

import edu.tongji.cc.digitalworld.entity.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class demonstrates how to implement a restful API interface method.
 */
@RestController
@RequestMapping(value="/api/")
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * Test URL: http://localhost:8080/api/greeting
	 * @param name
	 * @return
	 */
	@GetMapping("greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
