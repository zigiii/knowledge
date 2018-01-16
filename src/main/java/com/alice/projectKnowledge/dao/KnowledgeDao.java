package com.alice.projectKnowledge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreKnowledge;

@Mapper
public interface KnowledgeDao {
	public CoreKnowledge queryKnowledge(String knowledgeId);
	public CoreKnowledge queryKnowledgeByCode(String knowledgeCode);
}
