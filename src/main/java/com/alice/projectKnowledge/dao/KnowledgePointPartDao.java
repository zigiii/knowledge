package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreKnowledgePointPart;

@Mapper
public interface KnowledgePointPartDao {
	public List<CoreKnowledgePointPart> queryKnowledgePointPartList(Integer pointId);
}
