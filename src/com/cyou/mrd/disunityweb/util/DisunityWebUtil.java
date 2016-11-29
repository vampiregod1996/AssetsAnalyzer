package com.cyou.mrd.disunityweb.util;

import info.ata4.unity.asset.struct.ObjectPath;
import info.ata4.unity.util.ClassID;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cyou.mrd.disunityweb.conf.Configs;
import com.cyou.mrd.disunityweb.core.domain.application.UnityApplication;

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
		if (str == null || str.equalsIgnoreCase("null") || str.equals("") || str.equalsIgnoreCase("undefined"))
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
	
}
