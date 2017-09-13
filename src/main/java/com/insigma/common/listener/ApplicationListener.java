package com.insigma.common.listener;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.common.util.DateUtil;
import com.insigma.common.util.EhCacheUtil;
import com.insigma.mvc.component.appcontext.MyApplicationContextUtil;
import com.insigma.mvc.model.Aa01;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.init.InitService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeValueService;

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
	
	
	/**
	 * 基于ehcache
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//通过MyApplicationContextUtil获取bean
		InitService initservice= MyApplicationContextUtil.getContext().getBean(InitService.class);
		SysCodeTypeService sysCodeTypeService= MyApplicationContextUtil.getContext().getBean(SysCodeTypeService.class);
		SysCodeValueService sysCodeValueService= MyApplicationContextUtil.getContext().getBean(SysCodeValueService.class);
		//是否同步标志 如果上一次同步时间是1小时之内，不同步下载代码
		boolean syn_flag=true;
		Element element=EhCacheUtil.getManager().getCache("webcache").get("code_value_last_update_time");
		if(element!=null){
			Date code_value_last_update_time=(Date)element.getValue();
			if(code_value_last_update_time!=null){
				if(!DateUtil.compare(new Date(), code_value_last_update_time, 3600*1000)){
					syn_flag=false;
				}
			}
		}
		if(syn_flag){
			//aa01同步
			List <Aa01> list_aa01=initservice.getInitParamList();
			for(Aa01 aa01:list_aa01){
				EhCacheUtil.getManager().getCache("webcache").put(new Element(aa01.getAaa001(),aa01.getAaa005()));
			}
			
			//code_type code_value同步
			List <CodeType> list_code_type=sysCodeTypeService.getInitcodetypeList().getObj();
			for(CodeType codetype : list_code_type){
				CodeValue codevalue=new CodeValue();
				codevalue.setCode_type(codetype.getCode_type());
				List<CodeValue> list_code_value =sysCodeValueService.getInitCodeValueList(codevalue).getObj();
				if (list_code_value.size() > 0) {
					//将代码参加加载到redis缓存中
					try{
						//将代码参加加载到ehcache缓存中
						EhCacheUtil.getManager().getCache("webcache").put(new Element(codevalue.getCode_type(),list_code_value));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			//上一次更新时间
			EhCacheUtil.getManager().getCache("webcache").put(new Element("code_value_last_update_time",new Date()));
			
		}
	}
}
