package com.alice.projectKnowledge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreLinkMapping;

@Mapper
public interface KnowledgeLinkDao {
	public CoreLinkMapping queryLinkKnowledge(String linkKnowledgePoint);
}
