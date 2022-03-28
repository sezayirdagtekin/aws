package com.sezo.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class S3Service {

	private static final String DOWNLOAD_URI = "C://download/";
	
	@Autowired
	private AmazonS3 s3Client;

	
	/**
	 * 
	 * @param bucketName
	 */
	public void createBucket(String bucketName) {

		Bucket bucket = s3Client.createBucket(bucketName);
		log.info("Bucket created:" + bucket.toString());

	}
	
	/**
	 * 
	 * @param bucketName
	 */
	public void deleteBucket(String bucketName) {
		DeleteBucketRequest request= new DeleteBucketRequest(bucketName);
		s3Client.deleteBucket(request);
		log.info("Bucket deeted:" + bucketName);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getBuckets() {
		return s3Client.listBuckets().stream().map(Bucket::getName).toList();
		
	}
	
	/**
	 * 
	 * @param bucketName
	 * @param file
	 * @throws AmazonServiceException
	 * @throws SdkClientException
	 * @throws IOException
	 */
	public void upload(String bucketName, MultipartFile file)
			throws AmazonServiceException, SdkClientException, IOException {

		ObjectMetadata data = new ObjectMetadata();
		data.setContentType(file.getContentType());
		data.setContentLength(file.getSize());
		PutObjectResult objectResult = s3Client.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(),
				data);
		log.info("File upload is succcess:" + objectResult.toString());

	}

	/**
	 * 
	 * @param bucket
	 * @param fileName
	 */
	public void download(String bucket, String fileName) {

		GetObjectRequest request = new GetObjectRequest(bucket, fileName);

		File file = new File(DOWNLOAD_URI + fileName);
		s3Client.getObject(request, file);

	}

	/**
	 * 
	 * @param bucketName
	 * @param file
	 */
	public void delete(String bucketName, String file) {
		DeleteObjectRequest request = new DeleteObjectRequest(bucketName, file);
		s3Client.deleteObject(request);

	}
	
	/**
	 * 
	 * @param bucketName
	 * @return
	 */
	public List<String> getFiles(String bucketName) {

		ObjectListing objectListing = s3Client.listObjects(bucketName);
		return objectListing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).toList();

	}
	
	/**
	 * 
	 * @param bucketName
	 */
	public void blockBucket(String bucketName) {
		
		s3Client.setPublicAccessBlock(new SetPublicAccessBlockRequest()
				.withBucketName(bucketName)
				.withPublicAccessBlockConfiguration(new PublicAccessBlockConfiguration()
						.withBlockPublicAcls(true)
						.withIgnorePublicAcls(true)
						.withBlockPublicPolicy(true)
						.withRestrictPublicBuckets(true)));
		
	}
	
	/**
	 * 
	 * @param bucketName
	 */
	public void unBlockBucket(String bucketName) {
		
		s3Client.setPublicAccessBlock(new SetPublicAccessBlockRequest()
				.withBucketName(bucketName)
				.withPublicAccessBlockConfiguration(new PublicAccessBlockConfiguration()
						.withBlockPublicAcls(false)
						.withIgnorePublicAcls(false)
						.withBlockPublicPolicy(false)
						.withRestrictPublicBuckets(false)));
		
	}

	/**
	 * 
	 * @param bucketName
	 * @param fileName
	 * @return
	 */
	public String generatePreSignUrl(String bucketName, String fileName) {
	
        log.info("Generating pre-signed URL.");
        
		 Date expiration = getExpirationDate();
     
          GeneratePresignedUrlRequest request =
                  new GeneratePresignedUrlRequest(bucketName, fileName)
                          .withMethod(HttpMethod.GET)
                          .withExpiration(expiration);
		
		URL url = s3Client.generatePresignedUrl(request);
		return url.toString();

	}

	private java.util.Date getExpirationDate() {
		Date expiration = new Date();
		long expTimeMillis = Instant.now().toEpochMilli();
		expTimeMillis += 1000 * 30;// 30 seconds
		expiration.setTime(expTimeMillis);
		return expiration;
	}


}
