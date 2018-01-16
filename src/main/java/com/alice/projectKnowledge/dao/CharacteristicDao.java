package com.alice.projectKnowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alice.projectKnowledge.bean.CoreCharacteristic;

@Mapper
public interface CharacteristicDao {
	public List<CoreCharacteristic> queryCharacteristicList(Integer pointId);
}
