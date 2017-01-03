package com.fx.passform.control;

import com.jfinal.core.Controller;

/**
 *  管理员端控制器
 *
 * Created by fan.xu on 2014/10/24.
 */
public class AdminController extends Controller  {

    public void index() {
        render("/common/login.jsp");
    }

}
