package com.cyou.mrd.disunityweb.core.disunity;

import info.ata4.unity.cli.DisUnityCli;

import java.util.UUID;

import com.beust.jcommander.ParameterException;

public class DisUnity {

	private UUID uuid = null;
	private DisUnityCli disunityThread = null;
	
	public DisUnity (UUID uid) {
		this.uuid = uid;
		disunityThread = new DisUnityCli();
		disunityThread.setFileID(this.uuid);
	}
	
	public void setFileID(UUID uid) {
		
	}
	
	public void parse(String[] args) throws ParameterException,NullPointerException {
		disunityThread.parse(args);
	}
	
	public void start() throws ParameterException,NullPointerException {
		disunityThread.run();
	}

}
