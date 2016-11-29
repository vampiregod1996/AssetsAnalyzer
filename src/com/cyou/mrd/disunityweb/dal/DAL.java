package com.cyou.mrd.disunityweb.dal;

import java.util.concurrent.ConcurrentHashMap;

import com.cyou.mrd.disunityweb.core.domain.task.BaseTask;


public class DAL {

	//key:taskId,value:task 
	public static final ConcurrentHashMap<String, BaseTask> tasks = new ConcurrentHashMap<String, BaseTask>();
	
	//key:uuid,value:analyzeResultDao
//	public static final ConcurrentHashMap<UUID, Ana>
	
}
