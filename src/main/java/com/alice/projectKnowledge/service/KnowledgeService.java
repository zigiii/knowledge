package com.alice.projectKnowledge.service;

import com.alice.projectKnowledge.common.ResponseResult;

public interface KnowledgeService {
	
	public ResponseResult queryKnowledge(String knowledgeId);
	
	public ResponseResult queryLinkKnowledge(String knowledgeCode);

}
