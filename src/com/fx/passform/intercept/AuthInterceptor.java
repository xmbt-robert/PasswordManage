package com.fx.passform.intercept;

import com.fx.passform.model.OptLog;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class AuthInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		String loginPath = "/common/login.jsp";
		Object obj = controller.getSession().getAttribute("name");
		if(obj == null){
			controller.redirect(loginPath);
		} else {
			ai.invoke();
		}
	}
}
