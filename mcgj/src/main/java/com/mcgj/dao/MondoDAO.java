package com.mcgj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 * mondoDAO
 * @author ad
 *
 */
@Service
public class MondoDAO {
	public MondoDAO(){
		System.out.println("ÊµÀý»¯mongodao...");
	}
	@Autowired
	private MongoOperations mongoTemplate;
	public MongoOperations getMongoTemplate(){
		return mongoTemplate;
	}
}
