package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreKnowledgeImageContent;

@Mapper
public interface KnowledgeImageContentDao {
	public List<CoreKnowledgeImageContent> queryKnowledgeImageContentList(Integer partId);
}
