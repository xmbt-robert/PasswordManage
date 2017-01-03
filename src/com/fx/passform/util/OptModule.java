package com.fx.passform.util;

import java.lang.reflect.Method;

import com.fx.passform.plugin.Module;
import com.jfinal.core.Controller;

public class OptModule {

	//获取操作模块的名字
	public static String getModuleName(Controller controller, String actionKey,String title) {
		Class<? extends Controller> class1 = controller.getClass();
		Method[] declaredMethods = class1.getDeclaredMethods();		
		for(Method method : declaredMethods){
			if(actionKey.endsWith(method.getName())){
				Module moduleAnnotation = method.getAnnotation(Module.class);
				title = moduleAnnotation.value();
				break;
			}
		}
		return title;
	}
}
