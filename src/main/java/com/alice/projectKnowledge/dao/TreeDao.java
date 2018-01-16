package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreTree;

@Mapper
public interface TreeDao {
	public List<CoreTree> queryNodeList(Integer parentId);
	public CoreTree queryNode(Integer nodeId);
}
