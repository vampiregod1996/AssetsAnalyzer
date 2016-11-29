package com.cyou.x.disunityweb.application;

import java.nio.file.Path;
import java.util.Observable;

import com.cyou.x.disunityweb.core.domain.UnityApplication;
import com.cyou.x.disunityweb.event.UnzipCompletEvent;
import com.cyou.x.disunityweb.util.DisunityWebUtil;
import com.google.common.eventbus.Subscribe;

public class APKApplication extends UnityApplication {

	public APKApplication(String filePath) {
		super(filePath);
		setApplicationType(UnityApplication.APP_TYPE_APK);
	}

	@Subscribe
	public void unzipComplete(UnzipCompletEvent event) {
		if (!event.fileId.equals(this.getUUID().toString())) {
			// x 不等的时候，说明这个任务不是自己的。。。
			return;
		}
		//TODO 生成AnalysisAllTask
		
		
		
	}

	@Override
	public Path findDataRoot() {
		// 没有解压的话
		if (true) {
			DisunityWebUtil.extractApplication((UnityApplication)this);
		}
		
		return null;
	}

}
