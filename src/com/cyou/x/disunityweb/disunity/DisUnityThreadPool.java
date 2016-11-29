package com.cyou.x.disunityweb.disunity;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisUnityThreadPool {

//	private static ConcurrentHashMap<UUID, DisUnity> hashmap = new ConcurrentHashMap<UUID, DisUnity>();
//	public static DisunityThread getThread(UUID uid) {
//		return new DisunityThread(uid);
//	}
//	
	private ExecutorService pool = Executors.newFixedThreadPool(5);
	
	private static DisUnityThreadPool instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new DisUnityThreadPool();
		}
	}
	
	public static void addTask(Runnable task) {
		try {
			instance.pool.execute(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	public static DisUnity getThread(UUID uid) {
//		if (!hashmap.containsKey(uid))
//			hashmap.put(uid, new DisUnity(uid));
//		return hashmap.get(uid);
//	}
//	
//	public static DisUnity getThreadByUUID(UUID uid) {
//		return hashmap.get(uid);
//	}
}
