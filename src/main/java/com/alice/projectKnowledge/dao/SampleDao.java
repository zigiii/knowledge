package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreSample;

@Mapper
public interface SampleDao {
	public List<CoreSample> querySampleList(Integer pointId);
}
