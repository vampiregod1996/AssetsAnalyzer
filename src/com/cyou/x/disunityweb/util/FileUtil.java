package com.cyou.x.disunityweb.util;

import java.io.File;
import java.io.IOException;
public class FileUtil {
	
	public static File checkAndCreate(String path, boolean isFile) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			return file;
		}
		// 不存在，创建
		if (isFile) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} else {
			file.mkdirs();
		}
		
		return file;
	}
}
