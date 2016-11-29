package com.cyou.mrd.disunityweb.core.domain.application;

import info.ata4.unity.asset.AssetFile;
import info.ata4.unity.asset.struct.ObjectPath;
import info.ata4.unity.cli.DisUnityCli;
import info.ata4.unity.cli.extract.AssetExtractor;
import info.ata4.unity.util.ClassID;
import info.ata4.util.string.StringUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.cyou.mrd.disunityweb.conf.Configs;
import com.cyou.mrd.disunityweb.core.disunity.DisUnityThreadPool;
import com.cyou.mrd.disunityweb.core.domain.task.BaseTask;
import com.cyou.mrd.disunityweb.data.Database;
import com.cyou.mrd.disunityweb.data.DatabaseAnalyzeResult;
import com.cyou.mrd.disunityweb.data.DatabaseOperator;
import com.cyou.mrd.disunityweb.extraction.command.DisUnityCommand;
import com.cyou.mrd.disunityweb.util.DisunityWebUtil;

public abstract class UnityApplication {

	public static final String UNITY_PACK_FILE = "unity default resources";
	public static final String UNITY_PACK_FILEPATH_IPA = "/Data/";
	public static final String UNITY_PACK_FILEPATH_AKP = "/assets/bin/Data/";

	public static final byte APP_TYPE_NOT_UNITY = -2;
	public static final byte APP_TYPE_NOT_ZIP = -1;
	public static final byte APP_TYPE_NULL = 0;
	public static final byte APP_TYPE_IPA = 1;
	public static final byte APP_TYPE_APK = 2;
	
	public Object addTaskLock = new Object();
	public Object dbWriteLock = new Object();

	/**
	 * 文件类型
	 */
	private byte applicationType;

	public byte getApplicationType() {
		return this.applicationType;
	}

	public void setApplicationType(byte applicationType) {
		this.applicationType = applicationType;
	}

	private Path filePath = null;

	public Path getFilePath() {
		return filePath;
	}

	private final UUID uuid = UUID.randomUUID();

	public UUID getUUID() {
		return uuid;
	}

	private Map<String, BaseTask> tasks = new HashMap<String, BaseTask>();

	public Map<String, BaseTask> getTasks() {
		return tasks;
	}

	// Data的路径
	private Path dataRoot = null;

	public Path getDataRoot() {
		return dataRoot;
	}

	// 解压到的路径
	public String getUnzipPath() {
		return Configs.getUnzipPath() + "\\" + getUUID();
	}
	

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

	public abstract Path FindDataRoot();

	public abstract File[] getResourceFiles();

	public abstract File[] getSceneFiles();

	public abstract File[] getAssetBundle();

	public void startExtract() {
		System.out.println("startExtract");
		dataRoot = FindDataRoot();

		if (dataRoot == null) {
			System.out.println("Error Data Root Path");
			return;
		}

		if (!DisunityWebUtil.checkUnityCreate(dataRoot)) {
			// 这不是一个DisUnity包，设置xx
			setApplicationType(UnityApplication.APP_TYPE_NOT_UNITY);
			return;
		}

		data = Database
				.Instane()
				.begin("INSERT INTO assets (uuid,type,pid,pname,cid,cname,offset,size,source,time,md5) VALUES (?,?,?,?,?,?,?,?,?,date(),?);");

		analResultData  = Database
				.Instane()
				.begin("INSERT INTO analresult (uuid,result) VALUES (?,?);");
		
		File[] files = null;

		files = this.getResourceFiles();
		if (files != null) {
			addFiles(files, "resource");
		}

		files = this.getSceneFiles();
		if (files != null) {
			addFiles(files, "scene");
		}

		files = this.getAssetBundle();
		if (files != null) {
			addFiles(files, "assetbundle");
		}

		synchronized (addTaskLock) {
			for (DisUnityCommand cmd : commands) {
				DisUnityThreadPool.addTask(cmd);
			}
		}
	}

	// 数据库操作对象
	private DatabaseOperator data = null;
	private Set<DisUnityCommand> commands = Collections.synchronizedSet(new HashSet<DisUnityCommand>());
	private String[] args = { "DisUnity", "" };
	private int batchCount = 0;
	private int state = 0;

	private void addFiles(File[] files, String taskType) {
		for (File file : files) {
			args[1] = file.getAbsolutePath();
			DisUnityCommand cmd = new DisUnityCommand();
			cmd.disUnityTaskId = System.currentTimeMillis();
			cmd.application = this;
			cmd.taskType = taskType;
			cmd.file = file;
			cmd.cli = new DisUnityCli();
			cmd.cli.setCmd(cmd);
			cmd.cli.parse(args);
			commands.add(cmd);
		}
	}

	public int getState() {
		return this.state;
	}

	private boolean checkAsset(String className) {
		if (className.equals("Texture2D")) {
			return true;
		}
		if (className.equals("AnimationClip")) {
			return true;
		}
		if (className.equals("AudioClip")) {
			return true;
		}
		if (className.equals("Mesh")) {
			return true;
		}
		if (className.equals("Shader")) {
			return true;
		}
		if (className.equals("TextAsset")) {
			return true;
		}
		if (className.equals("LightProbeCloud")) {
			return true;
		}
		if (className.contains("Font")) {
			return true;
		}
		if (className.contains("NavMesh")) {
			return true;
		}
		if (className.contains("Material")) {
			return true;
		}
		if (className.contains("ParticleSystem")) {
			return true;
		}
		return false;
	}

	public void onCompleted(DisUnityCommand cmd, AssetFile[] assets) {
		synchronized (dbWriteLock) {
			if (assets != null) {
				for (AssetFile asset : assets) {
					List<ObjectPath> paths = asset.getPaths();
					for (ObjectPath path : paths) {
						if (path.isScript()) {
							continue;
						}
						if (path.getClassID() == 1) {
							continue;
						}
						String name = AssetExtractor.getObjectName(asset, path);
						String className = ClassID.getNameForID(
								path.getClassID(), true);

						if (!checkAsset(className)) {
							continue;
						}

						String sourceFile = null;
						if (asset.getSourceFile() == null) {
							sourceFile = asset.getSourceBundle()
									.getSourceFile().getFileName().toString();
						} else {
							sourceFile = asset.getSourceFile().getFileName()
									.toString();
						}

						if (name == null) {
							name = "(unname)";
						}

						if (name.contains("Combined Mesh")) {
							className = "Combined Mesh";
						}
						
						//x 2015-7-28 17:47:09 将重名文件搞出来
//						if (!"(unname)".equals(name)) {
//							if (repeatMap.containsKey(name)) {
//								repeatMap.get(name).add(sourceFile);
//							} else {
//								//重名列表里面没有时，先去filename里面看是否是第一次出现
//								if (fileNames.contains(name)) {
//									//出现过
//									if (!repeatMap.containsKey(name)) {
//										System.out.println("onCompleted add name in fileNameMap : " + name);
//										repeatMap.put(name, Collections.synchronizedList(new ArrayList<String>()));
//									} else {
//										repeatMap.get(name).add(sourceFile);
//									}
//								} else {
//									//没有出现过
//									fileNames.add(name);
//								}
//							}
//						}
						
						//x 2015-8-3 14:11:23 根据文件体在内存的中数据，用md5计算出文件标识
						ByteBuffer fileBB = asset.getPathBuffer(path);
						byte[] md5Blob = null;
						try {
							MessageDigest md5 = MessageDigest.getInstance("MD5");
							md5.update(fileBB);
							md5Blob = md5.digest();
						} catch (Exception e) {
							e.printStackTrace();
						}

						batchCount++;
						int index = 0;
						// data.setString(++index,
						// this.getUUID().toString()+sourceFile+name);
						data.setString(++index, this.getUUID().toString());
						data.setString(++index, cmd.taskType);
						data.setLong(++index, path.getPathID());
						data.setString(++index, name);
						data.setLong(++index, path.getClassID());
						data.setString(++index, className);
						data.setString(++index,
								String.format("0x%x", path.getOffset()));
						data.setLong(++index, path.getLength());
						data.setString(++index, sourceFile);
						data.setBytes(++index, md5Blob);
						data.addBatch();
						if (batchCount % 2000 == 0) {
							data.Batch();
						}
					}
				}
			}
			commands.remove(cmd);
			if (commands.size() == 0) {
				data.Batch();
				// x 2015-9-21 13:33:38 在这里切断数据库的连接，会导致有数据还未写进数据库。
				//解决方案：将 data.Close(); 放在最后一条语句执行。。。
				//PS：这不是一个好的解决方案
				state = 1;
				deleteDir(new File(this.getUnzipPath()));
				System.out.println("All Completed");
				analysisAndInsert();
				data.Close();
				data = null;
			}
		}
	}

	private static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		dir.delete();
	}
	
	
	public static ConcurrentHashMap<String, List<String>> repeatMap = new ConcurrentHashMap<String, List<String>>();
	public static List<String> fileNames = Collections.synchronizedList(new ArrayList<String>());
	private DatabaseOperator analResultData;
	/**
	 * 
	 */
	private void analysisAndInsert() {
		// x 2015-7-28 17:30:17 结果存库
		try {
			DatabaseAnalyzeResult dar = new DatabaseAnalyzeResult(uuid);
			int index = 0;
			analResultData.setString(++index, this.getUUID().toString());
			analResultData.setString(++index, dar.getAnalResult().toString());
			analResultData.addBatch();
			analResultData.Batch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			analResultData.Close();
			analResultData = null;
		}
	}
}
