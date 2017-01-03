package com.fx.passform.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class loginValidate extends Model<loginValidate>{
	public static final loginValidate dao = new loginValidate();

	/**
	 * 校验输入的管理员用户名是否存在数据库中
	 * @param username
	 */
	public Long login(String username){
		List<Object> lv = Db.query("select * from admin where user = '" +username+"'");
		return (long)lv.size();
		
	}

}
