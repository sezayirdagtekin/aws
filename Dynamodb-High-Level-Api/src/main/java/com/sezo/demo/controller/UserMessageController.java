package com.sezo.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sezo.demo.domain.UserMessage;
import com.sezo.demo.service.UserMessageService;

@RestController
@RequestMapping("/message")
public class UserMessageController {

	private final UserMessageService service;

	@Autowired
	public UserMessageController(UserMessageService service) {
		this.service = service;
	}

	@PostMapping("/save")
	public void putItem(@RequestBody UserMessage message) {
		service.putItem(message);
	}

	@GetMapping("/query")
	public ResponseEntity<UserMessage> getItem(@RequestParam("department") String department, @RequestParam("time") String time) {
		return new ResponseEntity<>(service.getMessage(department,time), HttpStatus.OK);
	}
	

	@GetMapping("/all")
	public ResponseEntity<List<UserMessage>> getAllForDepartment(@RequestParam("department") String department) {
		return new ResponseEntity<>(service.getAllForDepartment(department), HttpStatus.OK);
	}

	@GetMapping("/departmentOnDate")
	public ResponseEntity<List<UserMessage>> getAllForDepartmentOnDate(@RequestParam("department") String department,
			@RequestParam("day") String day) {
		return new ResponseEntity<>(service.getAllForDepartmentOnDate(department, day), HttpStatus.OK);
	}
	

	@GetMapping("/urgentmessages")
	public ResponseEntity<List<UserMessage>> getAllUrgentMessages(@RequestParam("department") String department,
			@RequestParam("urgency") String urgency) {
		return new ResponseEntity<>(service.getAllUrgencyMessages(department, urgency), HttpStatus.OK);
	}
	

	@DeleteMapping("/delete")
	public  void deleteItem(@RequestParam("department") String department, @RequestParam("time") LocalDateTime time) {
        service.deleteMessage(department, time);
	}

}
