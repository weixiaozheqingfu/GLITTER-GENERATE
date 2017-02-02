package jinhetech.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jinhetech.oracle.generation.common.DateUtils;
import jinhetech.oracle.generation.entity.controller.EntityController;

import org.apache.log4j.helpers.LogLog;

public class Log4jListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent event) {
		LogLog.setInternalDebugging(false);
		String rootPath = event.getServletContext().getRealPath("/");
		System.setProperty("rootPath", rootPath);
		
		Date startTime = DateUtils.parse(new Date(),"yyyy-MM-dd HH:mm:ss");
		System.out.println("自动生成开始时间："+startTime.toString());
		
		EntityController entityGenerate = new EntityController();
		try {
			entityGenerate.generate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date endTime = DateUtils.parse(new Date(),"yyyy-MM-dd HH:mm:ss");
		System.out.println("自动生成结束时间："+endTime.toString());
		
	}

}
