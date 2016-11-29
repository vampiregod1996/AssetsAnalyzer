package com.cyou.mrd.disunityweb.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.cyou.mrd.disunityweb.conf.Configs;
import com.cyou.mrd.disunityweb.core.disunity.DisUnityThreadPool;
import com.cyou.mrd.disunityweb.data.Database;

/**
 * Application Lifecycle Listener implementation class InitConfigListener
 *
 */
@WebListener
public class InitConfigListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitConfigListener() {
        
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	Configs.contextPath = arg0.getServletContext().getRealPath("/");
    	DisUnityThreadPool.init();
    	Database.Init();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	Database.Destroy();
    }
	
}
