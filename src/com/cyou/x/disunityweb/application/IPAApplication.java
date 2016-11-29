package com.cyou.x.disunityweb.application;

import java.nio.file.Path;

import com.cyou.x.disunityweb.core.domain.UnityApplication;

public class IPAApplication extends UnityApplication {

	public IPAApplication(String filePath) {
		super(filePath);
		
	}

	@Override
	public Path findDataRoot() {
		
		return null;
	}

}
