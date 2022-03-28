package com.sezo.demo.conifg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class AWSProperties {

	@Value("${aws.accessKey}")
	private String accessKey;

	@Value("${aws.secretKey}")
	private String secretKey;
}
