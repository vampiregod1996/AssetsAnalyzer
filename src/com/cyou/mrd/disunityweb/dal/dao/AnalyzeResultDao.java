package com.cyou.mrd.disunityweb.dal.dao;

import java.util.UUID;

public class AnalyzeResultDao {

	
	public static final int TYPE_top30 = 1;
	public static final int TYPE_Texture2D = 2;
	public static final int TYPE_AnimationClip = 3;
	public static final int TYPE_AudioClip = 4;
	public static final int TYPE_Mesh = 5;
	public static final int TYPE_CombineMesh = 6;
	public static final int TYPE_LightProb = 7;
	public static final int TYPE_percentage = 8;
	public static final int TYPE_size = 9;
	public static final int TYPE_repeatRes = 10;
	
	private UUID fileId;
	private int type;
	private String result;
	public UUID getFileId() {
		return fileId;
	}
	public void setFileId(UUID fileId) {
		this.fileId = fileId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
