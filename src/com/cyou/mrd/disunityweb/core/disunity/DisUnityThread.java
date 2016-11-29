package com.cyou.mrd.disunityweb.core.disunity;

import info.ata4.unity.cli.DisUnityCli;

import java.util.UUID;

public class DisUnityThread extends Thread {

	private UUID uuid = null;
	private DisUnityCli disunityThread = null;
	
	public DisUnityThread (UUID uid) {
		this.uuid = uid;
		disunityThread = new DisUnityCli();
	}
	
	public void parse(String[] args) throws NullPointerException {
		disunityThread.parse(args);
	}
	
	@Override
	public void run() {
		//TODO 使DisUnityCli线程正常工作
			
	}
}
