package com.cyou.mrd.disunityweb.core.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.cyou.mrd.disunityweb.conf.Configs;
import com.cyou.mrd.disunityweb.core.domain.application.UnityApplication;

public class ThreadData {

	public static void write2File(int userId, String id, UnityApplication ua) {
//		if () {
//			
//		}
		
		//TODO 写文件,从配置文件里面读出来的（意思就是还要搞一个读配置文件的东东。。。），另如果存放的路径是非工程目录，估计还要搞一搞这个权限问题
		File resule = new File(Configs.contextPath+"txt\\resource\\"+ua.getUUID());
		
		try {
			if (!resule.exists()) {
				resule.createNewFile();
			}
			BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resule), "utf-8"));
			
//			osw.write(str);
			
			
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
