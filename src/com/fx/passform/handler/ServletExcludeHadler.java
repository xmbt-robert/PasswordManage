package com.fx.passform.handler;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class ServletExcludeHadler extends Handler {

	@SuppressWarnings("serial")
	public static final HashSet<String> servletSet = new HashSet<String>() {
		{
			add("/DisplayChart");
			add("/ShowChart");
			
		}
	};

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		System.out.println("ServletExcludeHadler ");
		if (servletSet.contains(target))
			return;
		nextHandler.handle(target, request, response, isHandled);

	}

}
