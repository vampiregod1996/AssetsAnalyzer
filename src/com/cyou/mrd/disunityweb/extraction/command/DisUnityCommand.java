package com.cyou.mrd.disunityweb.extraction.command;

import info.ata4.io.file.FilenameSanitizer;
import info.ata4.io.util.PathUtils;
import info.ata4.unity.asset.AssetFile;
import info.ata4.unity.asset.bundle.AssetBundle;
import info.ata4.unity.cli.DisUnityCli;
import info.ata4.unity.cli.cmd.AssetCommand;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.Parameters;
import com.cyou.mrd.disunityweb.core.domain.application.UnityApplication;
import com.cyou.x.disunityweb.event.DisUnityTaskCompleteEvent;
import com.cyou.x.disunityweb.task.NotificationCenter;

/**
 * 
 * @author huyang
 *
 */
@Parameters(commandNames = "DisUnity", commandDescription = "Lists all objects inside asset files.")
public class DisUnityCommand extends AssetCommand {
	public String taskId;
	public long disUnityTaskId;
	public String taskType = "";
	public UnityApplication application = null;
	public DisUnityCli cli = null;
	public File file = null;

	@Override
	protected void processAssetFile(Path file) throws IOException {
		AssetFile[] assets = null;
		try {
			AssetFile asset = new AssetFile();
			asset.open(file);
			setOutputDir(PathUtils.removeExtension(file));
			processAsset(asset);
			assets = new AssetFile[] { asset };
		} catch (Exception e) {
			System.err.print(e.getMessage());
		} finally {
			if (application != null) {
				application.onCompleted(this, assets);
				
				// x 发送任务完成消息
				DisUnityTaskCompleteEvent event = new DisUnityTaskCompleteEvent();
				event.asset = assets;
				event.taskType = taskType;
				event.taskId = this.taskId;
				event.fileId = application.getUUID().toString();
				NotificationCenter.instance().postEvent(event);
			}
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
		} catch (Exception e) {
			System.err.print(e.getMessage());
		} finally {
			application.onCompleted(this, assets);
		}
	}
}
