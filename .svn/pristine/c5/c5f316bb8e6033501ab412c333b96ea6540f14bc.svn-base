package com.fx.passform.util;

import com.fx.passform.plugin.MailInfo;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;

import javax.naming.Context;

/**
 * Created by fan.xu on 2014/10/24.
 */
public class MailKit {

	public static Hashtable env = new Hashtable();
    /**
     * 邮件信息
     */
    private static MailInfo info;
    /**
     * 连接池
     */
    private static ExecutorService pool;

    /**
     * 初始化
     *
     * @param pool
     */
    public static void init(ExecutorService pool, MailInfo info) {
        MailKit.pool = pool;
        MailKit.info=info;
    }

    public static ExecutorService getPool() {
        return pool;
    }

    public static MailInfo getInfo() {
        return info;
    }
    
    /**
     * 
     * @param url
     * @param port
     * @param user
     * @param pass
     */
    public static void updateEmail(int type, String url, int port, String user, String pass) {  
    	if(env == null){
    		env = new Hashtable();
    	}
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        env.put("url", url);
        env.put("port", port);
        env.put("type", type);
    }
}
