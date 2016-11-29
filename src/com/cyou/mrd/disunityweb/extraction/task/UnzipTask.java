package com.cyou.mrd.disunityweb.extraction.task;

import java.util.UUID;

import com.cyou.mrd.disunityweb.core.domain.application.ApplicationFactory;
import com.cyou.mrd.disunityweb.core.domain.task.BaseTask;

public class UnzipTask extends BaseTask {

	public UnzipTask(UUID fileId) {
		setFileId(fileId);
	}
	
	@Override
	public void doTask() {
		if (ApplicationFactory.data.containsKey(getFileId())) {
			ApplicationFactory.data.get(getFileId()).startExtract();
		}
	}

}
