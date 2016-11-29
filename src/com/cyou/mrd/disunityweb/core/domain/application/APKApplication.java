package com.cyou.mrd.disunityweb.core.domain.application;

import info.ata4.unity.asset.AssetFile;
import info.ata4.unity.asset.bundle.AssetBundle;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cyou.mrd.disunityweb.conf.Configs;
import com.cyou.mrd.disunityweb.extraction.command.DisUnityCommand;
import com.cyou.mrd.disunityweb.extraction.uzip.AntZip;
import com.cyou.x.disunityweb.task.AnalysisAllTask;
import com.cyou.x.disunityweb.task.NotificationCenter;

public class APKApplication extends UnityApplication {
	private AntZip az = new AntZip();
	
	public APKApplication(String filePath) {
		super(filePath);
		setApplicationType(UnityApplication.APP_TYPE_APK);
		
		// x test 测试notificationcenter
		AnalysisAllTask listener = new AnalysisAllTask();
		NotificationCenter.instance().registEventListener(listener);
	}
	
	@Override
	public Path FindDataRoot()
	{
		az.unZip(getFilePath().toString(), Configs.getUnzipPath()+"\\"+getUUID());
		
		File file = new File(Configs.getUnzipPath()+"\\"+getUUID()+"\\assets\\bin\\Data\\");
		if (!file.exists()) {
			// 这东西解出来的包有问题,设置为null态
			setApplicationType(UnityApplication.APP_TYPE_NOT_ZIP);
			return null;
		}
		
		return file.toPath();
	}
	
	@Override
	public File[] getResourceFiles()
	{
		File resourceRoot = new File(this.getDataRoot().toString());
		File[] resources = resourceRoot.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (!name.contains(".") && name.length() == 32) {
					return true;
				}
				return false;
			}
		});
		return resources;
	}
	
	@Override
	public File[] getSceneFiles()
	{
		File resourceRoot = new File(this.getDataRoot().toString());
		File[] resources = resourceRoot.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.contains("mainData")) {
					return true;
				}
				if (name.contains("level")) {
					return true;
				}
				if (name.contains("sharedassets")) {
					if(name.contains("split"))
					{
						if(name.contains("split0"))
						{
							return true;
						}
						return false;
					}
					return true;
				}
				return false;
			}
		});
		return resources;
	}
	
	@Override
	public File[] getAssetBundle()
	{
		File abRoot = new File(this.getUnzipPath().toString() + "\\assets");
		if (!abRoot.exists()) {
			//TODO 没有ab包目录。。。
			return null;
		}
		
		// 1.遍历目录，得到所有文件
		List<File> allFiles = new LinkedList<File>();
		getAllFileExcpet(abRoot, allFiles, "bin");
		
		List<File> abFiles = new LinkedList<File>();
		// 2.读文件判断是不是ab包
		for (File tmp : allFiles) {
			if (AssetBundle.isAssetBundle(tmp.toPath())) {
				abFiles.add(tmp);
			}
		}
		File[] resources = new File[abFiles.size()];
		for(int index = 0; index < abFiles.size(); ++index)
		{
			resources[index] = abFiles.get(index);
		}
		return resources;
	}
	
	private void getAllFile(File path, List<File> files) {
		if (path.isFile()) {
			files.add(path);
			return;
		}

		if (path.isDirectory()) {
			for (File file : path.listFiles()) {
				getAllFile(file, files);
			}
		}

	}
	
	private void getAllFileExcpet(File path, List<File> files, String direct) {
		if (path.isFile()) {
			files.add(path);
			return;
		}

		if (path.isDirectory()) {
			if(path.getName().equals(direct))
			{
				return;
			}
			for (File file : path.listFiles()) {
				getAllFile(file, files);
			}
		}

	}
	
	@Override
	public void onCompleted(DisUnityCommand cmd, AssetFile[] assets) {
		if (assets == null || assets.length == 0) {
			System.out.println("----APKApplication---- onCompleted ---- AssetFile[] null");
			super.onCompleted(cmd, new AssetFile[0]);
			return;
		}
		try {
			
			//TODO 过滤所有splite内容
			List<AssetFile> newFiles = new ArrayList<AssetFile>(); 
			for (AssetFile asset : assets) {
				String sourceFile = null;
				if (asset.getSourceFile() == null) {
					sourceFile = asset.getSourceBundle()
							.getSourceFile().getFileName().toString();
				} else {
					sourceFile = asset.getSourceFile().getFileName()
							.toString();
				}
				
				//只有当名字不含有split，或含有split0的，才要存库
				if (!sourceFile.contains("split") || sourceFile.contains("split0")) {
					newFiles.add(asset);
				}
				AssetFile[] files = new AssetFile[newFiles.size()];
				super.onCompleted(cmd, newFiles.toArray(files));
			}
		} catch (Exception e) {
			System.out.println("----APKApplication---- onCompleted --Exception-- AssetFile[] " + assets.length);
			e.printStackTrace();
		}
		
		
	}
}
