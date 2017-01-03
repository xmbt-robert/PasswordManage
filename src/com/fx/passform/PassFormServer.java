package com.fx.passform;

import com.fx.passform.Timer.UnlockTimer;
import com.fx.passform.common.CommonController;
import com.fx.passform.control.AdminController;
import com.fx.passform.handler.ServletExcludeHadler;
import com.fx.passform.model.*;
import com.fx.passform.plugin.*;
import com.fx.passform.util.LdapKit;
import com.fx.passform.util.MailKit;
import com.fx.passform.util.SMSKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.*;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;

/**
 * API引导式配置
 */
public class PassFormServer extends JFinalConfig {
	/**
	 * 配置常量
	 */
    public void configConstant(Constants me) {
        loadPropertyFile("system.txt");                // 加载少量必要配置，随后可用getProperty(...)获取值
        //me.setDevMode(getPropertyToBoolean("devMode",true));
        me.setDevMode(true);
        me.setViewType(ViewType.JSP);                  // 设置视图类型为Jsp，否则默认为FreeMarker
        //单独从配置文件ldap中取dn 和 path
        LdapKit.addDnPath(getProperty("searchBase"), getProperty("keystore"));
        //单独从配置文件sms中取smstmpid 和 smstime(smstmpid=1  短信模板      smstime=30 发送时间)
        SMSKit.addTmpidTime(getProperty("smstmpid"), getProperty("smstime"), getProperty("smsappid"));
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        me.add("/", CommonController.class);
        me.add("/admin", AdminController.class);
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
                getProperty("user"), getProperty("password").trim());
        me.add(c3p0Plugin);

        //添加缓存插件
        me.add(new EhCachePlugin());
        
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setShowSql(true);//这句话就是ShowSql
        me.add(arp);
        arp.addMapping("admin", "id", AdminControl.class);
        arp.addMapping("data_audit", "id", LockStatus.class);
        arp.addMapping("sys_config_server", "sysType", Config.class);
        arp.addMapping("sys_lock_time_gap", "type", AuditLockTime.class);
        arp.addMapping("sys_send_time_gap", "type", AuditSendTime.class);
        arp.addMapping("sys_opt_logs", OptLog.class);
        arp.addMapping("data_shortmessage", "id", Sms.class);

        /*
        MailInfo info = new MailInfo(getProperty("emailurl"), getProperty("emailport"),
                getProperty("emailuser"), getProperty("emailpass"));
        EmailPlugin emailPlugin = new EmailPlugin(info, 10);
        me.add(emailPlugin);

        SMSInfo smsInfo = new SMSInfo(getProperty("smsurl"), getProperty("smsport"),
                getProperty("smsuser"), getProperty("smspass"), getProperty("smsappid"));
        smsInfo.setTmpId(getProperty("smstmpid"));
        smsInfo.setValTime(getProperty("smstime"));
        SMSPlugin smsPlugin = new SMSPlugin(smsInfo, 10);
        me.add(smsPlugin);*/

        //定时任务
        new UnlockTimer(new Timer());
        
    }

    
    @Override
    /**
     * 加载框架后，就开始初始化数据库中的ldap配置信息
     */
	public void afterJFinalStart() {
		super.afterJFinalStart();
		
	}

	/**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {

    	/*me.add(new Interceptor() {
			
			@Override
			public void intercept(ActionInvocation ai) {
				// TODO Auto-generated method stub
				
			}
		});*/
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
    	me.add(new ServletExcludeHadler());
    }
   
    /**
     * 建议使用 JFinal 手册推荐的方式启动项目
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            JFinal.start("WebRoot", 80, "/", 3);            
        } else {
            try {
                int port = Integer.valueOf(args[0]);
                JFinal.start("WebRoot", port, "/", 3);                
            } catch (Exception e) {
                System.out.println("The port number must be a value type of Integer ["+args[0]+"]");
            }
        }
    }
}
