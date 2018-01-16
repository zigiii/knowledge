package com.alice.projectKnowledge.bean;

import java.util.ArrayList;
import java.util.List;

public class CoreTree {

	private Integer nodeId;
	private Integer parentId;
	private String nodeName;
	private Integer knowledgeId;
	private List<CoreTree> nodeList = new ArrayList<>();
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Integer getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(Integer knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public List<CoreTree> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<CoreTree> nodeList) {
		this.nodeList = nodeList;
	}
}
