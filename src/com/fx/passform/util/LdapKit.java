package com.fx.passform.util;

import java.util.Hashtable;

import javax.naming.Context;

/**
 * Created by fan.xu on 2014/10/23.
 */
public class LdapKit {

    public static Hashtable env = new Hashtable();

    static {
    	if(env == null){
    		env = new Hashtable();
    	}
    	 env.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别         
         env.put(Context.REFERRAL, "follow");
         env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    }
    
    /**
     * 初始化
     *
     * @param env
     */
    public static void init(Hashtable env) {
        LdapKit.env = env;
    }

    public static Hashtable getEnv() {
        return env;
    }
    
    public static void addDnPath(String dn, String path){
    	if(env == null){
    		env = new Hashtable();
    	}
    	env.put("searchBaseDn", dn);
        env.put("certs", path);
    }
    /**
     * 
     * @param url
     * @param port
     * @param user
     * @param pass
     */
    public static void updateLdap(int type, String url, int port, String user, String pass) {  
    	if(env == null){
    		env = new Hashtable();
    	}
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        env.put(Context.PROVIDER_URL, "ldap://" + url + ":" + port);        
        env.put("url", url);
        env.put("port", port);
        env.put("type", type);
    }
}
