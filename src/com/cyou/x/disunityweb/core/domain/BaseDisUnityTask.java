package com.cyou.x.disunityweb.core.domain;

import info.ata4.unity.cli.cmd.Command;

public class BaseDisUnityTask extends BaseTask {
	
	private Command cmd;
	
	/** 参数 */
	private String[] args;
	private boolean isDone = false;
	
	public BaseDisUnityTask(Command cmd) {
		this.cmd = cmd;
	}
	
	public Command getCmd() {
		return cmd;
	}

	public String[] getArgs() {
		return this.args;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
}
