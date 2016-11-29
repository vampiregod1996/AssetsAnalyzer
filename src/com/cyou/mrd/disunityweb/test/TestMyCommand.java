package com.cyou.mrd.disunityweb.test;

import java.util.UUID;

import info.ata4.unity.cli.DisUnityCli;

import com.beust.jcommander.ParameterException;

public class TestMyCommand {
	
	
	
	public static void main(String[] args) {
//		String[] testArgs = {"list","E:/sharedassets0.assets"};
//		String[] testArgs = {"-f", "JSON","weblist","D:/workspace_unity/unzip/5d0e2082-6b8b-4599-bd03-975f12342cb1/Payload/orange.app/Data/resources.assets"};
//		String[] testArgs = {"-f", "JSON","list","D:/workspace_unity/unzip/5d0e2082-6b8b-4599-bd03-975f12342cb1/Payload/orange.app/Data/sharedassets0.assets"};
//		String[] testArgs = {"-f", "JSON","weblist","E:/resources.assets"};
//		String[] testArgs = {"-f", "JSON","weblist","E:/f5da3eb5-f91f-4b6d-8789-ef92e798ac6e/resources.assets"};
//		String[] testArgs = {"-f", "JSON","weblist","D:\\workspace_unity\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\AssetsAnalyze\\unzip\\420349eb-a385-4af3-8f1c-1dbe7fc66d5e\\Payload\\xkx.app\\Data\\resources.assets"};
//		String[] testArgs = {"weblist","E:/fun/2015-2-12/assets/bin/Data/sharedassets0.assets.split0"};
//		String[] testArgs = {"-f", "JSON","weblist","E:/testscene/sharedassets0.assets","E:/testscene/level1"};
//		String[] testArgs = {"-f", "JSON","assetbundle","D:\\workspace_unity\\unzip\\ab\\TLBB3D\\Payload\\mldj.app\\Data\\Raw\\Effect\\baoshi_wuqi_baoji_001.data"};
//		String[] testArgs = {"list","E:\\level31"};
		String[] testArgs = {"scene","E:\\level31"};
		DisUnityCli cli = new DisUnityCli();

//		SceneCommand scmd = new SceneCommand();
//		scmd.fileID = UUID.randomUUID();
//		scmd.taskId = System.currentTimeMillis()+"";
//		scmd.disUnityTaskId = System.currentTimeMillis();
//		cli.setCmd(scmd);
//		
//		AssetBundleCommand abcmd = new AssetBundleCommand();
//		abcmd.fileID = UUID.randomUUID();
//		cli.setCmd(abcmd);
        
        try {
            cli.parse(testArgs);
            cli.run();
        } catch (ParameterException ex) {
            ex.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}
}
