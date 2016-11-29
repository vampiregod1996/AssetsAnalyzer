package com.cyou.mrd.disunityweb.core.domain.task;

import java.util.UUID;

import com.cyou.mrd.disunityweb.core.domain.application.UnityApplication;

public abstract class BaseTask {

	public static final byte TASK_TYPE_NO = 0;
	public static final byte TASK_TYPE_TOP30 = 1;
	public static final byte TASK_TYPE_PERCENTAGE = 2;
	public static final byte TASK_TYPE_SCENE = 3;
	public static final byte TASK_TYPE_ASSETBUNDLE = 4;
	
	public static final byte TASK_STATE_ERROR = 0;
	public static final byte TASK_STATE_1 = 1;
	public static final byte TASK_STATE_NO_AB = -2;
	public static final byte TASK_STATE_3 = -3;
	public static final byte TASK_STATE_4 = -4;
	public static final byte TASK_STATE_5 = -5;
	
	private UUID fileId = null;
	private byte taskType = TASK_TYPE_NO;
	private String taskId = null;
	private boolean isDone = false;
	private byte state = 0;
	private UnityApplication application = null;
	
	public UUID getFileId() {
		return fileId;
	}

	public void setFileId(UUID fileId) {
		this.fileId = fileId;
	}
	
	public byte getTaskType() {
		return taskType;
	}

	public void setTaskType(byte taskType) {
		this.taskType = taskType;
	}

	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
		if (this.isDone) {
			this.state = TASK_STATE_1;
		}
	}
	
	public void setState(byte state) {
		this.state = state;
	}
	
	public byte getState() {
		return this.state;
	}

	
	public void setApplication(UnityApplication application) {
		this.application = application;
	}
	
	public UnityApplication getApplication() {
		return this.application;
	}

	public abstract void doTask();
}
