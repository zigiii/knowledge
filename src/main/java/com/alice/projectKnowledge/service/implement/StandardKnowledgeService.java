package com.alice.projectKnowledge.service.implement;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.projectKnowledge.bean.CoreKnowledge;
import com.alice.projectKnowledge.bean.CoreKnowledgePoint;
import com.alice.projectKnowledge.bean.CoreKnowledgePointPart;
import com.alice.projectKnowledge.bean.CoreLinkMapping;
import com.alice.projectKnowledge.common.ResponseResult;
import com.alice.projectKnowledge.dao.CharacteristicDao;
import com.alice.projectKnowledge.dao.KnowledgeDao;
import com.alice.projectKnowledge.dao.KnowledgeImageContentDao;
import com.alice.projectKnowledge.dao.KnowledgeLinkDao;
import com.alice.projectKnowledge.dao.KnowledgeListContentDao;
import com.alice.projectKnowledge.dao.KnowledgePointDao;
import com.alice.projectKnowledge.dao.KnowledgePointPartDao;
import com.alice.projectKnowledge.dao.KnowledgeTextContentDao;
import com.alice.projectKnowledge.dao.SampleDao;
import com.alice.projectKnowledge.service.KnowledgeService;
import com.alice.projectKnowledge.vo.KnowledgePointPartVo;
import com.alice.projectKnowledge.vo.KnowledgePointVo;
import com.alice.projectKnowledge.vo.KnowledgeVo;

@Service("knowledgeService")
@Transactional
public class StandardKnowledgeService implements KnowledgeService{
	
	@Autowired
	private KnowledgeDao knowledgeDao;
	@Autowired
	private KnowledgePointDao knowledgePointDao;
	@Autowired
	private KnowledgePointPartDao knowledgePointPartDao;
	@Autowired
	private KnowledgeListContentDao knowledgeListContentDao;
	@Autowired
	private KnowledgeTextContentDao knowledgeTextContentDao;
	@Autowired
	private KnowledgeImageContentDao knowledgeImageContentDao;
	@Autowired
	private KnowledgeLinkDao knowledgeLinkDao;
	
//	@Value("${indexPageSize}")
//	private int indexPageSize;

	@Override
	public ResponseResult queryKnowledge(String knowledgeId) {
		ResponseResult result = new ResponseResult();
		KnowledgeVo knowledgeVo = new KnowledgeVo();
		List<KnowledgePointVo> knowledgePointVoList = new LinkedList<KnowledgePointVo>();
		//第一步：查知识信息
		CoreKnowledge coreKnowledge = knowledgeDao.queryKnowledge(knowledgeId);
		//第二步：查知识的所有知识点信息
		List<CoreKnowledgePoint> coreKnowledgePointList = knowledgePointDao.queryKnowledgePointList(coreKnowledge.getKnowledgeCode());
		//第三步：查知识点所有组成部分
		for(CoreKnowledgePoint point : coreKnowledgePointList){
			KnowledgePointVo knowledgePointVo = new KnowledgePointVo();
			knowledgePointVo.setCoreKnowledgePoint(point);
			List<CoreKnowledgePointPart> coreKnowledgePointPartList = knowledgePointPartDao.queryKnowledgePointPartList(point.getPointId());
			//第四步：查知识点组成部分的内容
			List<KnowledgePointPartVo> knowledgePointPartVoList = new LinkedList<KnowledgePointPartVo>(); 
			for(CoreKnowledgePointPart coreKnowledgePointPart : coreKnowledgePointPartList){
				 KnowledgePointPartVo knowledgePointPartVo = new KnowledgePointPartVo();
				 knowledgePointPartVo.setCoreKnowledgePointPart(coreKnowledgePointPart);
				 if("01".equals(coreKnowledgePointPart.getPartShowType())){ // 文字类型
					 knowledgePointPartVo.setCoreKnowledgeTextContent(knowledgeTextContentDao.queryKnowledgeTextContentList(coreKnowledgePointPart.getPartId()));
				 }else if("02".equals(coreKnowledgePointPart.getPartShowType())){ //列表类型
					 knowledgePointPartVo.setCoreKnowledgeListContentList(knowledgeListContentDao.queryKnowledgeListContentList(coreKnowledgePointPart.getPartId()));
				 }else if("03".equals(coreKnowledgePointPart.getPartShowType())){ //图片类型
					 knowledgePointPartVo.setCoreKnowledgeImageContentList(knowledgeImageContentDao.queryKnowledgeImageContentList(coreKnowledgePointPart.getPartId()));
				 }
				 knowledgePointPartVoList.add(knowledgePointPartVo);
			}
			knowledgePointVo.setCoreKnowledgePointPartVoList(knowledgePointPartVoList);
			knowledgePointVoList.add(knowledgePointVo);
		}
		knowledgeVo.setCoreKnowledge(coreKnowledge);
		knowledgeVo.setKnowledgePointVoList(knowledgePointVoList);
		result.setData(knowledgeVo);
		return result;
	}

	@Override
	public ResponseResult queryLinkKnowledge(String knowledgeCode) {
		ResponseResult result = new ResponseResult();
		CoreKnowledge coreKnowledge = knowledgeDao.queryKnowledgeByCode(knowledgeCode);
		result.setData(queryKnowledge(coreKnowledge.getKnowledgeId().toString()));
		return result;
	}
	
}

