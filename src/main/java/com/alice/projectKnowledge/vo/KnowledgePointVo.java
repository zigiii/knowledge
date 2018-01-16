package com.alice.projectKnowledge.vo;

import java.util.List;

import com.alice.projectKnowledge.bean.CoreKnowledgePoint;

public class KnowledgePointVo {

	private CoreKnowledgePoint coreKnowledgePoint;
	private List<KnowledgePointPartVo> coreKnowledgePointPartVoList;
	
	public CoreKnowledgePoint getCoreKnowledgePoint() {
		return coreKnowledgePoint;
	}
	public void setCoreKnowledgePoint(CoreKnowledgePoint coreKnowledgePoint) {
		this.coreKnowledgePoint = coreKnowledgePoint;
	}
	public List<KnowledgePointPartVo> getCoreKnowledgePointPartVoList() {
		return coreKnowledgePointPartVoList;
	}
	public void setCoreKnowledgePointPartVoList(List<KnowledgePointPartVo> coreKnowledgePointPartVoList) {
		this.coreKnowledgePointPartVoList = coreKnowledgePointPartVoList;
	}
}
