package com.cyou.x.disunityweb.core.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observer;
import java.util.UUID;

import com.cyou.mrd.disunityweb.conf.Configs;

public abstract class UnityApplication {

	public static final String UNITY_PACK_FILE = "unity default resources";
	public static final String UNITY_PACK_FILEPATH_IPA = "/Data/";
	public static final String UNITY_PACK_FILEPATH_AKP = "/assets/bin/Data/";

	public static final byte APP_TYPE_NOT_UNITY = -2;
	public static final byte APP_TYPE_NOT_ZIP = -1;
	public static final byte APP_TYPE_NULL = 0;
	public static final byte APP_TYPE_IPA = 1;
	public static final byte APP_TYPE_APK = 2;
	
	
	private final UUID uuid = UUID.randomUUID();
	
	// 文件类型 
	private byte applicationType;
	// 文件路径
	private Path filePath = null;
	
	// Data的路径
	private Path dataRoot = null;
	
	/**
	 * 构造器
	 * 
	 * @param path
	 */
	public UnityApplication(Path path) {
		this.filePath = path;
	}
	
	public UnityApplication(String filePath) {
		this(Paths.get(filePath));
	}
	
	public byte getApplicationType() {
		return this.applicationType;
	}

	public void setApplicationType(byte applicationType) {
		this.applicationType = applicationType;
	}

	public Path getFilePath() {
		return filePath;
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public Path getDataRoot() {
		return dataRoot;
	}

	// 解压到的路径
	public String getUnzipPath() {
		return Configs.getUnzipPath() + "\\" + getUUID();
	}
	
	public abstract Path findDataRoot();

	
}
