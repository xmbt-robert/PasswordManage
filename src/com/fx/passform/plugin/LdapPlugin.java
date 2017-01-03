package com.fx.passform.plugin;

import com.fx.passform.util.LdapKit;
import com.jfinal.plugin.IPlugin;

import javax.naming.Context;
import java.util.Hashtable;

/**
 * ldap plugin
 * <p/>
 * Created by fan.xu on 2014/10/23.
 */
public class LdapPlugin implements IPlugin {

    private Hashtable env;

    public LdapPlugin(String url, int port, String user, String pass, String dn, String path) {
        env = new Hashtable();
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        env.put(Context.REFERRAL, "follow");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + url + ":" + port);
        env.put("searchBaseDn", dn);
        env.put("certs", path);
        env.put("url", url);
        env.put("port", port);
        LdapKit.init(env);
    }
    
  /*  public LdapPlugin(String url, int port, String user, String pass) {
        env = new Hashtable();
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        env.put(Context.PROVIDER_URL, "ldap://" + url + ":" + port);
        env.put("url", url);
        env.put("port", port);
        LdapKit.init(env);
    }*/
    
    

    @Override
    public boolean start() {
        //LdapKit.init(env);
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
