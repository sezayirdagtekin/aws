package com.sezo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sezo.demo.domain.Comment;
import com.sezo.demo.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private final CommentService service;

	@Autowired
	public CommentController(CommentService service) {
		this.service = service;
	}

	@PostMapping("/save")
	public void putItem(@RequestBody Comment comment) {
		service.putItem(comment);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Comment>> getAllComments() {
		return new ResponseEntity<>(service.getAllComments(), HttpStatus.OK);
	}

	
	@GetMapping("/rating")
	public ResponseEntity<List<Comment>> getAllComments( @RequestParam("itemId")  String itemId,  @RequestParam("rating") int rating) {
		return new ResponseEntity<>(service.getAllCommentsWithMinRating(itemId, rating), HttpStatus.OK);
	}



	@GetMapping("/user/all")
	public ResponseEntity<List<Comment>> getAllCommentsByUser(@RequestParam("userId") String userId) {
		return new ResponseEntity<>(service.getAllCommentsByUser(userId), HttpStatus.OK);
	}




}
