package com.alice.projectKnowledge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alice.projectKnowledge.common.ResponseResult;
import com.alice.projectKnowledge.service.KnowledgeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/knowledge")
public class KnowledgeController {
	
	@Autowired
	KnowledgeService knowledgeService;

	@ResponseBody
	@RequestMapping(value="/queryKnowledge/{knowledgeId}",method=RequestMethod.GET)
	@ApiOperation(value="查询知识对象",notes="查询知识对象")
	public String queryKnowledge(HttpServletRequest request, @ApiParam(name="knowledgeId",value="知识ID",required=true)@PathVariable String knowledgeId){
		ResponseResult result = knowledgeService.queryKnowledge(knowledgeId);
		String callback = request.getParameter("jsonpcallback");
		String json = JSON.toJSONString(result);
		return callback + "(" + json + ")";
	}
	
	@ResponseBody
	@RequestMapping(value="/queryLinkKnowledge/{knowledgeCode}",method=RequestMethod.GET)
	@ApiOperation(value="根据知识编码查询知识对象",notes="根据知识点查询知识对象")
	public String queryLinkKnowledge(HttpServletRequest request, @ApiParam(name="knowledgeCode",value="知识编码",required=true)@PathVariable String knowledgeCode){
		ResponseResult result = knowledgeService.queryLinkKnowledge(knowledgeCode);
		String callback = request.getParameter("jsonpcallback");
		String json = JSON.toJSONString(result.getData());
		return callback + "(" + json + ")";
	}
	
}
