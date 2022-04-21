package com.sezo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.sezo.demo.domain.Comment;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

	private final DynamoDBMapper mapper;

	@Autowired
	public CommentService(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	public void putItem(Comment comment) {
		try {
			mapper.save(comment);
			log.info("Comment is saved");
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

	public List<Comment> getAllComments() {
		return mapper.scan(Comment.class, new DynamoDBScanExpression());
	}

	public List<Comment> getAllCommentsWithMinRating(String itemId, int rating) {

		Comment comment = new Comment();
		comment.setItemId(itemId);

		Condition condition = new Condition()
				.withComparisonOperator(ComparisonOperator.GE)
				.withAttributeValueList(new AttributeValue()
				.withN(Integer.toString(rating)));

		DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
				.withHashKeyValues(comment)
				.withRangeKeyCondition("rating", condition)
				.withScanIndexForward(false);

		return mapper.query(Comment.class, queryExpression);
	}

	public List<Comment> getAllCommentsByUser(String userId) {

		Comment comment = new Comment();
		comment.setUserId(userId);

		DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
				.withHashKeyValues(comment)
				.withConsistentRead(false)// eventualy consistent read. we can not read strong consistency  with global index
				.withScanIndexForward(false);

		return mapper.query(Comment.class, queryExpression);
	}

}
