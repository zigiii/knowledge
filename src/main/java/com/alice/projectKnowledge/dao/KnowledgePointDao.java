package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreKnowledgePoint;

@Mapper
public interface KnowledgePointDao {
	public List<CoreKnowledgePoint> queryKnowledgePointList(String knowledgeCode);
}
