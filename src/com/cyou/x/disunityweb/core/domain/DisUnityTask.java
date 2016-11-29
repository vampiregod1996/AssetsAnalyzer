package com.cyou.x.disunityweb.core.domain;

/**
 * @author wangxun
 *
 */
public class DisUnityTask {
	
	/** 主任务id（是由哪个业务BaseTask生成的） */
	private String taskId;
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
	/** DisUnityTaskID  */
	private long dutId;
	public long getDutId() {
		return dutId;
	}

	public void setDutId(long dutId) {
		this.dutId = dutId;
	}
	
	/** 参数 */
	private String[] args;
	public String[] getArgs() {
		return this.args;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	private boolean isDone;
	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}
