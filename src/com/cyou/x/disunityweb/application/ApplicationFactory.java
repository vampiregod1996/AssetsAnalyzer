package com.cyou.x.disunityweb.application;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.cyou.x.disunityweb.core.domain.UnityApplication;

public class ApplicationFactory {
	
	private static final String APK = "apk";
	private static final String IPA = "ipa";
	
	
	private static final ConcurrentHashMap<String, UnityApplication> data = new ConcurrentHashMap<>();
	
	private static ApplicationFactory instance = null;
	public static ApplicationFactory getInstance() {
		if (instance == null) {
			instance = new ApplicationFactory();
		}
		return instance;
	}
	
	public UnityApplication getApplication(UUID uuid) {
		return data.get(uuid.toString());
	}
	
	public UnityApplication getNewApplication(String filePath) {
		//TODO 判断是ipa还是apk
		File app = new File(filePath);
		if (!app.isFile()) {
			return null;
		}
		
		String[] str = app.getName().split("\\.");
		if (str.length < 2) {
			return null;
		}
		
		
		UnityApplication ua = null;
		
		if (IPA.equalsIgnoreCase(str[str.length-1])) {
			ua = new IPAApplication(filePath);
		}
		
		if (APK.equalsIgnoreCase(str[str.length-1])) {
			ua = new APKApplication(filePath);
		}
		
		data.put(ua.getUUID().toString(), ua);
		return ua;
	}
	
}
