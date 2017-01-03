package com.fx.passform.model;

import com.fx.passform.PassFormServer;
import com.fx.passform.plugin.LdapPlugin;
import com.fx.passform.plugin.MailInfo;
import com.fx.passform.plugin.SMSInfo;
import com.fx.passform.util.LdapKit;
import com.fx.passform.util.MailKit;
import com.fx.passform.util.SMSKit;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Config's Model
 * <p/>
 * Created by fan.xu on 2014/10/21.
 */
public class Config extends Model<Config> {	

    public static final Config dao = new Config();

    /**
     * 增加系统配置
     *
     * @param type 系统配置类型(1,ldap,2,sms,3,email)
     * @param ip   IP地址
     * @param port 端口
     * @param user 用户名
     * @param pass 密码
     * @return
     */
    public boolean addConfig(int type, String ip, int port, String user, String pass) {
        Config cfg = new Config();
        cfg.set("sysType", type).set("IP", ip).set("port", port);
        cfg.set("userName", user).set("userPassWd", pass);
        
        boolean flag = cfg.save();
        if(flag){
        	LdapKit.updateLdap(type, ip, port, user, pass);
        }
        return flag;
    }

    /**
     * 修改系统配置
     *
     * @param type
     * @param ip
     * @param port
     * @param user
     * @param pass
     * @return
     */
    public boolean updCfg(int type, String ip, int port, String user, String pass) {
        Config cfg = dao.findById(type);
        cfg.set("IP", ip).set("port", port);
        cfg.set("userName", user).set("userPassWd", pass);
        boolean flag = cfg.update();
        if(flag){
        	LdapKit.updateLdap(type, ip, port, user, pass);
        }
        return flag;
    }
    
    /**
     * 根据类型查找对应数据
     * @param type
     * @return
     */
    public List<Config> findByType(int type) {
        return find("select * from sys_config_server where sysType="+type);
    }
    
    
    /**
     * 得到所有系统配置信息
     *
     * @return
     */
    public List<Config> findAll() {
        return find("select * from sys_config_server");
    }

    public static void refreshConfig(){
 		List<Config> sysConfigList = Config.dao.findAll();
 		if(sysConfigList == null || sysConfigList.size() < 1 || sysConfigList.get(0) == null){
 			//异常处理
 		}
 		//得到Ldap 配置信息
 		Config ldapConfig = sysConfigList.get(0);
 		if(ldapConfig != null){
 			LdapKit.updateLdap(ldapConfig.getInt("sysType"), ldapConfig.getStr("IP"), 
 					ldapConfig.getInt("port"), ldapConfig.getStr("userName"), 
 					ldapConfig.getStr("userPassWd"));	
 		}
 		
 		//得到sms配置信息
 		Config smsConfig = sysConfigList.get(1);
 		if(smsConfig != null){
 			SMSInfo smsInfo = new SMSInfo(smsConfig.getStr("IP"),smsConfig.getInt("port").toString(),
 					smsConfig.getStr("userName"),smsConfig.getStr("userPassWd"));
 			SMSKit.init(Executors.newFixedThreadPool(10),smsInfo);
 		}
 		
 		//得到email配置信息
 		Config emailConfig = sysConfigList.get(2);
 		if(emailConfig != null){
 			MailInfo mailInfo = new MailInfo(emailConfig.getStr("IP"), emailConfig.getInt("port").toString(),
 					emailConfig.getStr("userName"), emailConfig.getStr("userPassWd")); 			
 			MailKit.init(Executors.newFixedThreadPool(10), mailInfo);
 		}
    }
}
