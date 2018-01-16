package com.alice.projectKnowledge.vo;

import java.util.List;

import com.alice.projectKnowledge.bean.CoreKnowledge;

public class KnowledgeVo {

	private CoreKnowledge coreKnowledge;
	private List<KnowledgePointVo> knowledgePointVoList;
	public CoreKnowledge getCoreKnowledge() {
		return coreKnowledge;
	}
	public void setCoreKnowledge(CoreKnowledge coreKnowledge) {
		this.coreKnowledge = coreKnowledge;
	}
	public List<KnowledgePointVo> getKnowledgePointVoList() {
		return knowledgePointVoList;
	}
	public void setKnowledgePointVoList(List<KnowledgePointVo> knowledgePointVoList) {
		this.knowledgePointVoList = knowledgePointVoList;
	}
	
}
