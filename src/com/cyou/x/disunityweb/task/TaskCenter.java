package com.cyou.x.disunityweb.task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.cyou.x.disunityweb.core.domain.BaseTask;
import com.cyou.x.disunityweb.core.domain.BaseTaskHandler;
import com.cyou.x.disunityweb.core.domain.UnityApplication;

/**
 * 暂时不使用，因为系统逻辑很简单，没必要多加这么一层
 * @author wangxun
 *
 */
public class TaskCenter {

	public static final byte TASK_TYPE_UNZIP = 0x000001;
	public static final byte TASK_TYPE_ANALYSISALL = 0x000001;
	
	private static ConcurrentLinkedQueue<BaseTask> tasks = new ConcurrentLinkedQueue<BaseTask>();
	private static ConcurrentHashMap<Byte, BaseTaskHandler> handler = new ConcurrentHashMap<Byte, BaseTaskHandler>();
	
	/**
	 * 初始化任务中心
	 */
	public static void init() {
		//TODO 初始化handler，我突然想写个beanfactory。。。
	}
	
	public static void signINTask(BaseTask task, String methodName, UnityApplication listener) {
		task.onCompleteMethod = methodName;
		task.setFileID(listener.getUUID());
		tasks.add(task);
	}
	
}
