package com.alice.projectKnowledge.vo;

import java.util.List;

import com.alice.projectKnowledge.bean.CoreKnowledgeImageContent;
import com.alice.projectKnowledge.bean.CoreKnowledgeListContent;
import com.alice.projectKnowledge.bean.CoreKnowledgePointPart;
import com.alice.projectKnowledge.bean.CoreKnowledgeTextContent;

public class KnowledgePointPartVo {

	private CoreKnowledgePointPart coreKnowledgePointPart;
	private List<CoreKnowledgeListContent> coreKnowledgeListContentList;
	private CoreKnowledgeTextContent coreKnowledgeTextContent;
	private List<CoreKnowledgeImageContent> coreKnowledgeImageContentList; 
	public CoreKnowledgePointPart getCoreKnowledgePointPart() {
		return coreKnowledgePointPart;
	}
	public void setCoreKnowledgePointPart(CoreKnowledgePointPart coreKnowledgePointPart) {
		this.coreKnowledgePointPart = coreKnowledgePointPart;
	}
	public List<CoreKnowledgeListContent> getCoreKnowledgeListContentList() {
		return coreKnowledgeListContentList;
	}
	public void setCoreKnowledgeListContentList(List<CoreKnowledgeListContent> coreKnowledgeListContentList) {
		this.coreKnowledgeListContentList = coreKnowledgeListContentList;
	}
	public CoreKnowledgeTextContent getCoreKnowledgeTextContent() {
		return coreKnowledgeTextContent;
	}
	public void setCoreKnowledgeTextContent(CoreKnowledgeTextContent coreKnowledgeTextContent) {
		this.coreKnowledgeTextContent = coreKnowledgeTextContent;
	}
	public List<CoreKnowledgeImageContent> getCoreKnowledgeImageContentList() {
		return coreKnowledgeImageContentList;
	}
	public void setCoreKnowledgeImageContentList(List<CoreKnowledgeImageContent> coreKnowledgeImageContentList) {
		this.coreKnowledgeImageContentList = coreKnowledgeImageContentList;
	}
}
