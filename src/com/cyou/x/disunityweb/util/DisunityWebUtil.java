package com.cyou.x.disunityweb.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import com.cyou.x.disunityweb.conf.Configs;
import com.cyou.x.disunityweb.core.domain.UnityApplication;

public class DisunityWebUtil {

	public static File getFile(String path) throws IOException {
		//TODO 遍历实现创建目录结构和文件
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.getAbsoluteFile().mkdirs();
		}
		file.createNewFile();
		return file;
	}
	
	public static String getFileUnzipPathByUUID(UUID id) {
		return Configs.getUnzipPath()+"\\"+id+"\\Payload\\";
	}
	
	
	public static boolean checkStringIsEmpty(String str) {
		if (str == null || str.equalsIgnoreCase("null") || str.equals(""))
			return true;
		return false;
	}
	public static boolean checkUnityCreate(Path path) {
		File[] children = path.toFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.equals(UnityApplication.UNITY_PACK_FILE)) {
					return true;
				}
				return false;
			}
		});
		if (children.length > 0) {
			return true;
		}
		return false;
	}
	
	public static void extractApplication(UnityApplication app) {
		AntZip az = new AntZip();
		az.unZip(app.getFilePath().toString(), Configs.getUnzipPath()+"\\"+app.getUUID());
	}
	
}
