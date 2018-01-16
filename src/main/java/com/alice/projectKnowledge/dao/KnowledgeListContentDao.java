package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreKnowledgeListContent;

@Mapper
public interface KnowledgeListContentDao {
	public List<CoreKnowledgeListContent> queryKnowledgeListContentList(Integer partId);
}
