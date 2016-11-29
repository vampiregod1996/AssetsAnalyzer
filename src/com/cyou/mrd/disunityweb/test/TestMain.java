package com.cyou.mrd.disunityweb.test;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class TestMain {

	public static void main(String[] args) {
		
		String zipPath = "D:\\workspace_unity\\unzip\\df_dev_orange.ipa";
		String unzipPath = "D:\\workspace_unity\\unzip";
		// get uuid
		UUID id = UUID.randomUUID();
		//TODO unzip
		
		
		String[] testArgs = {"list","E:/sharedassets0.assets"};
//		String[] testArgs = {"list","D:/workspace_unity/CangShan.unity"};
//		String[] testArgs = {};
//		DisUnity test = DisUnityThreadPool.getThread(id);
//		
//		test.parse(testArgs);
//		test.start();
		
		String testLevel = "E:/level0";
		String testMainData = "E:/mainData";
		String testshare = "E:/sharedassets0.assets";
		
		Path test1 = new File(testLevel).toPath();
		Path test2 = new File(testMainData).toPath();
		Path test3 = new File(testshare).toPath();
		
//		SceneCommand sc = new SceneCommand();
//		System.out.println("E:/level0  "+ sc.getScenesNumber(test1));
//		System.out.println("E:/mainData  "+ sc.getScenesNumber(test2));
//		System.out.println("E:/sharedassets0.assets  "+ sc.getScenesNumber(test3));
		
//		String[] scene1= testshare.split("\\.");
//		String[] sceneid = testshare.split("sharedassets");
//		String.valueOf(scene1[0].charAt(scene1[0].length()-1));
//		
//		String.valueOf(Integer.valueOf(sceneid[1].substring(0, sceneid[1].indexOf("\\."))));
		
		
	}

}
