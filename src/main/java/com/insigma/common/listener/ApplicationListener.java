package com.insigma.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 项目初始化
 * 
 * @author wengsh
 * 
 */
public class ApplicationListener implements   ServletContextListener  {
	Log log=LogFactory.getLog(ApplicationListener.class);


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}
	
}
