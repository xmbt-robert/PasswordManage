package com.fx.passform.intercept;
import java.lang.reflect.Method;

import com.fx.passform.model.OptLog;
import com.fx.passform.plugin.Module;
import com.fx.passform.util.OptModule;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;


public class AdminLogInterceptor extends Model<OptLog> implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		ai.invoke();
		Controller controller = ai.getController();
		
		//String operator = (String) controller.getSession().getAttribute("name");
		String operateIP = controller.getAttr("operateIP");
		String operator = controller.getAttr("operator");
		Integer optStatus = controller.getAttr("optStatus"); //操作状态
		String actionKey = ai.getActionKey(); //得到action名
		String title = "";
		title = OptModule.getModuleName(controller, actionKey, title);
		
		//存数据库
		OptLog optLog = new OptLog();
		optLog.set("moduleName", title).set("operateIP", operateIP).set("operator", operator).set("operateStatus",optStatus).save();
	}
}
