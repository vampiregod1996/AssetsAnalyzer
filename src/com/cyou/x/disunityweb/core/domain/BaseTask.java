package com.cyou.x.disunityweb.core.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.UUID;

import com.cyou.x.disunityweb.application.ApplicationFactory;

public class BaseTask {

	private final UUID taskID = UUID.randomUUID();
	
	private String fileID = null;
	public String onCompleteMethod = "onComplete";
	
	final public String getFileID() {
		return fileID;
	}

	// 保证有且只有第一个，才是任务poster
	final public void setFileID(UUID fileID) {
		if (this.fileID == null) {
			this.fileID = fileID.toString();
		}
	}
	
	final public String getTaskID() {
		return taskID.toString();
	}

	// x 2015-9-28 16:21:09 使用EventBus了，用不上这个些了
//	public void onComplete() {
//		UnityApplication ua = ApplicationFactory.getInstance().getApplication(UUID.fromString(fileID));
//		Method md;
//		try {
//			md = ua.getClass().getDeclaredMethod(onCompleteMethod);
//			md.invoke(ua);
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//	}
	
	
}
