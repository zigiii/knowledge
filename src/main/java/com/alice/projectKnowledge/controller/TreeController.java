package com.alice.projectKnowledge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alice.projectKnowledge.common.ResponseResult;
import com.alice.projectKnowledge.service.TreeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/tree")
public class TreeController {
	
	@Autowired
	TreeService treeService;

	@ResponseBody
	@RequestMapping(value="/queryAll",method=RequestMethod.GET)
	@ApiOperation(value="查询所有目标",notes="详细说明")
	public String queryAll(HttpServletRequest request){
		ResponseResult rr = treeService.queryAll();
		String callback = request.getParameter("jsonpcallback");
		String json = JSON.toJSONString(rr.getData());
		return callback + "(" + json + ")";
	}
	
}
