package com.cyou.x.disunityweb.disunity.command;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.Parameters;
import com.cyou.x.disunityweb.event.DisUnityTaskCompleteEvent;
import com.cyou.x.disunityweb.task.NotificationCenter;

import info.ata4.io.file.FilenameSanitizer;
import info.ata4.io.util.PathUtils;
import info.ata4.unity.asset.AssetFile;
import info.ata4.unity.asset.bundle.AssetBundle;
import info.ata4.unity.cli.cmd.AssetCommand;

@Parameters(commandNames = "DisUnity", commandDescription = "Lists all objects inside asset files.")
public class XAssetCommand extends AssetCommand {
	
	private String taskId = null;
	private String fileId = null;
	private String type = null;
	public void setTaskInfo(String taskId, String fileId, String type) {
		if (this.taskId == null && this.fileId == null && this.type == null) {
			this.taskId = taskId;
			this.fileId = fileId;
			this.type = type;
		}
	}

	@Override
	protected void processAssetFile(Path file) throws IOException {
		
		try {
			AssetFile asset = new AssetFile();
			asset.open(file);
			setOutputDir(PathUtils.removeExtension(file));
			processAsset(asset);
			// x 发送任务完成消息
			DisUnityTaskCompleteEvent event = new DisUnityTaskCompleteEvent();
			event.asset = new AssetFile[]{asset};
			event.taskType = this.type;
			event.taskId = this.taskId;
			event.fileId = this.fileId;
			NotificationCenter.instance().postEvent(event);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}
	
	@Override
	protected void processAssetBundleFile(Path file) {
		AssetFile[] assets = null;
		try {
			// load asset bundle
			AssetBundle ab = new AssetBundle();
			ab.open(file);

			// process bundle
			Path outDir = PathUtils.removeExtension(file);
			setOutputDir(outDir);
			processAssetBundle(ab);

			List<AssetFile> assetFiles = new LinkedList<AssetFile>();
			
			if (isProcessAssets() && isProcessBundledAssets()) {
				// process bundle entries
				for (Map.Entry<String, ByteBuffer> entry : ab.getEntries()
						.entrySet()) {
					String pathString = entry.getKey();

					// skip libraries
					if (pathString.endsWith(".dll")
							|| pathString.endsWith(".mdb")) {
						continue;
					}

					// skip dummy asset from Unity3D Obfuscator
					if (pathString.equals("33Obf")) {
						continue;
					}

					Path path = outDir;

					// split path string and assemble path
					String[] names = StringUtils.split(pathString, '/');
					for (String name : names) {
						path = path.resolve(FilenameSanitizer
								.sanitizeName(name));
					}

					// load asset
					ByteBuffer bb = entry.getValue();
					AssetFile asset = new AssetFile();
					asset.load(bb);
					asset.setSourceBundle(ab);

					// process asset
					setOutputDir(PathUtils.removeExtension(path));
					assetFiles.add(asset);
				}
			}
			if (!assetFiles.isEmpty()) {
				assets = new AssetFile[assetFiles.size()];
				for (int index = 0; index < assetFiles.size(); ++index) {
					assets[index] = assetFiles.get(index);
				}
			}
			
			// x 发送任务完成消息
			DisUnityTaskCompleteEvent event = new DisUnityTaskCompleteEvent();
			event.asset = assets;
			event.taskType = this.type;
			event.taskId = this.taskId;
			event.fileId = this.fileId;
			NotificationCenter.instance().postEvent(event);
			
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}
	
}
