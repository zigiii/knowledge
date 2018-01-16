package com.alice.projectKnowledge.vo;

import java.util.List;

public class NodeVo {

	private Integer id;
	private String text;
	private Integer knowledgeId;
	private List<NodeVo> nodes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(Integer knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public List<NodeVo> getNodes() {
		return nodes;
	}
	public void setNodes(List<NodeVo> nodes) {
		this.nodes = nodes;
	}

}
