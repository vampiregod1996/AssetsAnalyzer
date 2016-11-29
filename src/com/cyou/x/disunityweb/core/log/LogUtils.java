package com.cyou.x.disunityweb.core.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
    
    private LogUtils() {
    }
    
    public static XLogger getLogger() {
        String className = new Throwable().getStackTrace()[1].getClassName();
        return new XLogger(className);
    }
    
    static class XLogger {
    	private Logger L;
    	
    	public XLogger(String className) {
    		this.L = Logger.getLogger(className);
    	}
    	
    	public void info(String log) {
    		L.log(Level.INFO, log);
    	}
    	public void debug(String log) {
    		L.log(Level.INFO, log);
    	}
    	public void warning(String log) {
    		L.log(Level.WARNING, log);
    	}
    	public void error(String log) {
    		L.log(Level.SEVERE, log);
    	}
    }
}
