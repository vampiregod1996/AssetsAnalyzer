package com.cyou.mrd.disunityweb.core.domain.application;

import info.ata4.unity.asset.bundle.AssetBundle;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import com.cyou.mrd.disunityweb.conf.Configs;
import com.cyou.mrd.disunityweb.extraction.uzip.AntZip;

public class IPAApplication extends UnityApplication {

	private AntZip az = new AntZip();
	
	public IPAApplication(String filePath) {
		super(filePath);
		setApplicationType(UnityApplication.APP_TYPE_IPA);
	}
	
	@Override
	public Path FindDataRoot()
	{
		az.unZip(getFilePath().toString(), Configs.getUnzipPath()+"\\"+getUUID());
		File file = new File(Configs.getUnzipPath()+"\\"+getUUID()+"\\Payload\\");
		if (!file.exists()) {
			// 这东西解出来的包有问题,设置为null态
			setApplicationType(UnityApplication.APP_TYPE_NOT_ZIP);
			return null;
		}
		
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.contains(".app")) {
					return true;
				}
				return false;
			}
		});
		if (files == null || files.length <= 0) {
			// 这东西解出来的包有问题
			setApplicationType(UnityApplication.APP_TYPE_NOT_ZIP);
			return null;
		}
		return new File(files[0].getAbsolutePath()+"\\Data\\").toPath();
	}

	@Override
	public File[] getResourceFiles()
	{
		return new File[]{ new File(this.getDataRoot().toString() + "\\resources.assets")};
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
		File abRoot = new File(this.getDataRoot().toString() + "\\Raw\\");
		if (!abRoot.exists()) {
			//TODO 没有ab包目录。。。
			return null;
		}
		
		// 1.遍历目录，得到所有文件
		List<File> allFiles = new LinkedList<File>();
		getAllFile(abRoot, allFiles);
		
		List<File> abFiles = new LinkedList<File>();
		// 2.读文件判断是不是ab包
		for (File tmp : allFiles) {
			if (AssetBundle.isAssetBundle(tmp.toPath())) {
				abFiles.add(tmp);
			}
		}
		//释放allFiles
		allFiles = null;
		if (abFiles.size() <= 0) {
			return null;
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
}
