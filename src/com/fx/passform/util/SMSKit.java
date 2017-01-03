package com.fx.passform.util;

import com.fx.passform.plugin.SMSInfo;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;

import javax.naming.Context;

/**
 * Created by fan.xu on 2014/10/24.
 */
public class SMSKit {
    /**
     * SMS信息
     */
    private static SMSInfo info = new SMSInfo();;
    /**
     * 连接池
     */
    private static ExecutorService pool;
    /**
     * 初始化
     *
     * @param pool
     */
    public static void init(ExecutorService pool, SMSInfo info) {
        SMSKit.pool = pool;
//        info.setTmpId(SMSKit.info.getTmpId());        
//    	info.setSmstime(SMSKit.info.getSmstime());
//        SMSKit.info = info;
        
        SMSKit.info.setIp(info.getIp());
        SMSKit.info.setPort(info.getPort());
        SMSKit.info.setUser(info.getUser());
        SMSKit.info.setPass(info.getPass());
        SMSKit.info.setRestAPI(info.getRestAPI());
        
    }

    public static ExecutorService getPool() {
        return pool;
    }

    public static SMSInfo getInfo() {
        return info;
    }
    
    public static void addTmpidTime(String smstmpid, String smstime, String appId){
    	if(info == null){
    		info = new SMSInfo();
    	}
    	info.setTmpId(smstmpid);
    	info.setSmstime(smstime);
    	info.setAppId(appId);
    	
    	SMSInfo.restAPI.setAppId(appId);
    }
    
    
//    
//    public static void updateSms(int type, String url, int port, String user, String pass) {  
//    	if(env == null){
//    		env = new Hashtable();
//    	}
//        env.put(Context.SECURITY_PRINCIPAL, user);
//        env.put(Context.SECURITY_CREDENTIALS, pass);
//        env.put("url", url);
//        env.put("port", port);
//        env.put("type", type);
//    }
}
