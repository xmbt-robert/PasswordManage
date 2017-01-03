package com.fx.passform.intercept;

import com.fx.passform.model.OptLog;
import com.fx.passform.util.OptModule;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;

public class OperationLogInterceptor extends Model<OptLog> implements Interceptor{

	
	@Override
	/**
	 *  ai.invoke() 这行代码放在最前面，
	 *  这样就会先去执行 action，
	 *  后面再去将 action 执行后的操作结果入库，
	 *  虽然 action 返回值是 void，
	 *  但可以通过 setAttr或者 ThreadLocal 等方式传值给 interceptor 使用
	 */
	
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		ai.invoke();
		Controller controller = ai.getController();
		
		//String ip = IpKit.getRealIp(controller.getRequest());
		//String optModuleName = controller.getAttr(Constant.MODULE_NAME); //操作模块名
		//String operator = ((Model<OptLog>) controller.getSessionAttr("name")).getStr("operator"); //操作人
		//Date operateTime = controller.getParaToDate();  //操作时间（根据系统当前时间自动生成）
		String operateIP = controller.getAttr("operateIP");
		String operator = controller.getAttr("operator");
		Integer optStatus = controller.getAttr("optStatus"); //操作状态
		//String controlName = controller.getClass().getName(); //得到全路径
		String actionKey = ai.getActionKey(); //得到action名
		String title = "";
		
		//title = getModuleName(controller, actionKey, title);
		title = OptModule.getModuleName(controller, actionKey, title);
		
		//将数据存入数据库
		OptLog optLog = new OptLog();
		//OperationLogInterceptor optLogInterceptor = new OperationLogInterceptor();
		//optLog.set("operateTime",new Timestamp(new Date().getTime()));
		optLog.set("moduleName", title).set("operateIP", operateIP).set("operator", operator).set("operateStatus",optStatus).save();
	}

//	private String getModuleName(Controller controller, String actionKey,String title) {
//		Class<? extends Controller> class1 = controller.getClass();
//		Method[] declaredMethods = class1.getDeclaredMethods();		
//		for(Method method : declaredMethods){
//			if(actionKey.endsWith(method.getName())){
//				Module moduleAnnotation = method.getAnnotation(Module.class);
//				title = moduleAnnotation.value();
//				break;
//			}
//		}
//		return title;
//	}
}
