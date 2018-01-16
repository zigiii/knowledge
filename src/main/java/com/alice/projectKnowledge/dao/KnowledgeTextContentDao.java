package com.alice.projectKnowledge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreKnowledgeTextContent;

@Mapper
public interface KnowledgeTextContentDao {
	public CoreKnowledgeTextContent queryKnowledgeTextContentList(Integer partId);
}
