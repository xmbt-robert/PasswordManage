package com.fx.passform.plugin;

import com.fx.passform.util.SMSKit;
import com.jfinal.plugin.IPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  SMS plugin
 *
 * Created by fan.xu on 2014/10/24.
 */
public class SMSPlugin implements IPlugin {
    /**
     * 发送信息
     */
    private SMSInfo info;

    /**
     * 线程池
     */
    private ExecutorService pool;

    /**
     * SMS信息
     *
     * @return
     */
    public SMSInfo getInfo() {
        return info;
    }

    /**
     * 构造信息
     *
     * @param info
     * @param num
     */
    public SMSPlugin(SMSInfo info, int num) {
        this.info = info;
        // 创建一个可重用线程数的线程池
        pool = Executors.newFixedThreadPool(num);
    }

    public SMSPlugin(String host,int port,String user,String pass) {
		// TODO Auto-generated constructor stub
	}


	@Override
    public boolean start() {
        SMSKit.init(pool, info);
        return true;
    }

    @Override
    public boolean stop() {
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
        return true;
    }
}
