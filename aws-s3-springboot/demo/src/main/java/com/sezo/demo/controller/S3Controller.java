package com.sezo.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.sezo.demo.service.S3Service;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("s3")
public class S3Controller {

	@Autowired
	private S3Service service;

	@GetMapping("/bucket/list")
	public ResponseEntity<List<String>> getBuckets() {
		log.info("List buckets...");
		return new ResponseEntity<>(service.getBuckets(), HttpStatus.OK) ;
	}

	@PostMapping("/upload")
	public void upload(@RequestParam("bucketName") String bucketName, @RequestParam("file") MultipartFile file) {

		log.info("upload buckets...");
		try {
			service.upload(bucketName, file);
		} catch (SdkClientException | IOException e) {
			e.printStackTrace();
		}

	}

	@GetMapping("/download")
	public void download(@RequestParam("bucketName") String bucketName, @RequestParam("file") String file) {

		log.info("download buckets...");

		service.download(bucketName, file);

	}

	@PostMapping("/bucket/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createBucket(@RequestParam("bucketName") String bucketName) {
		log.info("Create bucket...");
		service.createBucket(bucketName);

	}

	@DeleteMapping("/bucket/delete")
	public void deleteBucket(@RequestParam("bucketName") String bucketName) {
		log.info("Delete bucket...");
		service.deleteBucket(bucketName);

	}

	@DeleteMapping("/delete")
	public void delete(@RequestParam("bucketName") String bucketName, @RequestParam("file") String file) {
		log.info("Delete file...");
		service.delete(bucketName, file);

	}

	@GetMapping("/list")
	public ResponseEntity<List<String>> getFilesFromBucket(@RequestParam("bucketName") String bucketName) {
		log.info("List files...");
		return  new ResponseEntity<>(service.getFiles(bucketName), HttpStatus.OK) ;

	}

	@PutMapping("/bucket/block")
	public void blockBucket(@RequestParam("bucketName") String bucketName) {
		log.info("Block bucket...");
		service.blockBucket(bucketName);

	}

	@PutMapping("/bucket/unblock")
	public void unblockBucket(@RequestParam("bucketName") String bucketName) {
		log.info("Unbock bucket for public access...");
		service.unBlockBucket(bucketName);

	}

	@GetMapping("/url")
	public ResponseEntity<String> generatePreSignUrl(@RequestParam("bucketName") String bucketName, @RequestParam("file") String file) {

		log.info("GeneratePreSignUrl...");
		return new ResponseEntity<>(service.generatePreSignUrl(bucketName, file), HttpStatus.OK) ;

	}
}
