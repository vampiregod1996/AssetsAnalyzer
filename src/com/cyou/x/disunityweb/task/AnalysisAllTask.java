package com.cyou.x.disunityweb.task;

import com.cyou.x.disunityweb.core.domain.BaseTask;
import com.cyou.x.disunityweb.event.DisUnityTaskCompleteEvent;
import com.google.common.eventbus.Subscribe;

public class AnalysisAllTask extends BaseTask {
	
	
	
	public AnalysisAllTask() {
		// TODO Auto-generated constructor stub
	}
	
	@Subscribe
	public void disunityCMDonComplete(DisUnityTaskCompleteEvent event) {
		if (!this.getTaskID().equals(event.taskId) || !this.getFileID().equals(event.fileId)) {
			// x 不等的时候，说明这个任务不是自己的。。。
			return;
		}
		System.out.println(" ---- " + event.taskId + "  " + event.taskType + "  " + event.asset.length + " ---- ");
		
		/**
		 * TODO 
		 * 1.记录DisUnity是否完成
		 * 2.存库操作
		 */
		
		
	}
	
	

}
