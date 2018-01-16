package com.alice.projectKnowledge.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.projectKnowledge.bean.CoreTree;
import com.alice.projectKnowledge.common.ResponseResult;
import com.alice.projectKnowledge.dao.TreeDao;
import com.alice.projectKnowledge.service.TreeService;
import com.alice.projectKnowledge.vo.NodeVo;

@Service("treeService")
@Transactional
public class StandardTreeService implements TreeService{
	
	@Autowired
	private TreeDao treeDao;
	
//	@Value("${indexPageSize}")
//	private int indexPageSize;

	@Override
	public ResponseResult queryAll() {
		ResponseResult result = new ResponseResult();
		NodeVo tree = resursionNode(1);
		result.setData(tree);
		return result;
	}
	
	private NodeVo resursionNode(Integer nodeId){
		NodeVo node = null;
		CoreTree root = treeDao.queryNode(nodeId);
		if(null != root){
			node = new NodeVo();
			node.setId(root.getNodeId());
			node.setText(root.getNodeName());
			node.setKnowledgeId(root.getKnowledgeId());
			node.setNodes(new ArrayList<NodeVo>());
		}
		List<CoreTree> nodeList = treeDao.queryNodeList(nodeId);
		for (CoreTree coreTree : nodeList) {
			NodeVo nv = resursionNode(coreTree.getNodeId());
			if(null != node){
				node.getNodes().add(nv);
			}
		}
		return node;
	}

}

