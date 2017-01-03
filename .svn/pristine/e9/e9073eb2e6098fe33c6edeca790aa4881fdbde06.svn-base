package com.fx.passform.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ldap connectionPoll
 * <p/>
 * Created by fan.xu on 2014/10/23.
 */
public class LDAPConectionDataPool {
    //ip
    private String url;
    //port
    private int port;
    //用户名
    private String user;
    //密码
    private String pass;
    //证书

    public int getPort() {
        return port;
    }

    private String certs;
    //搜索路径
    private String baseDn;
    //最大连接数
    private int maxCount;

    //生成的LDAPConectionData实例，统统的都放到该List中，进行管理。
    private List<LDAPConectionData> pool = new ArrayList<LDAPConectionData>();

    //把该类设置成一个单点模式。
    private static LDAPConectionDataPool LDAPConnDataPool = null;

    private LDAPConectionDataPool() {
    }

    public static synchronized LDAPConectionDataPool getInstance() {
        if (LDAPConnDataPool == null) {
            return new LDAPConectionDataPool();
        } else {
            return LDAPConnDataPool;
        }
    }

    public void add(LDAPConectionData aa) {
        aa.setConetIsFree(false);
        this.pool.add(aa);
    }

    /**
     * 用完后设置该连接为空闲状态，以便其他线程重用，并把阻塞的线程唤醒。
     *
     * @param da
     */
    public synchronized void setConectionDataFree(LDAPConectionData da) {
        if (!da.getConetIsFree()) {
            da.setConetIsFree(true);
            this.notifyAll();
        }
    }

    /**
     * 调用该方法返回一个LDAPConectionData
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized LDAPConectionData getLDAPConectionData() throws InterruptedException {
        LDAPConectionData datatemp = null;
        //如果产生的LDAPConectionData还没有达到最大连接数，则可以从工厂里获取。
        if (this.getSize() < maxCount) {
            datatemp = createLDAPConection();
            this.add(datatemp);
        } else {//如果已经达到最大连接数了。知道有空闲的LDAPConectionData可以使用。
            Iterator<LDAPConectionData> its = pool.iterator();
            while(its.hasNext()){
                LDAPConectionData data= its.next();
                if (data.getConetIsFree())
                    if (data.getConn().isConnectionAlive()) {
                        datatemp = data;
                        break;
                    } else {
                        its.remove();
                        datatemp = createLDAPConection();
                        this.add(datatemp);
                    }
            }
            //否则等待释放链接
            if (datatemp == null) {
                wait();
            } else {
                datatemp.setConetIsFree(false);
            }
        }
        return datatemp;
    }


    private LDAPConectionData createLDAPConection() {
        return new LDAPConectionData(url, port, user, pass);
    }

    public int getSize() {
        return pool.size();
    }

    public void removeAll() {
        closeAll();
        pool.clear();
    }

    public void closeAll() {
        for (LDAPConectionData data : pool) {
            data.closeLDAPConnection();
        }
    }

    public LDAPConectionData hasFree() {//这个方法几乎没有用。
        if (getSize() < 1) return null;
        LDAPConectionData datatemp = null;
        for (LDAPConectionData data : pool) {
            if (data.getConetIsFree())
                datatemp = data;
            break;
        }
        return datatemp;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
    }

    public String getCerts() {
        return certs;
    }

    public void setCerts(String certs) {
        this.certs = certs;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
