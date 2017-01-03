package com.fx.passform.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * AuditLockTime's Model
 * <p/>
 * Created by fan.xu on 2014/10/21.
 */
public class AuditLockTime extends Model<AuditLockTime> {
    public static final AuditLockTime dao = new AuditLockTime();

    /**
     * 修改审计策略
     *
     * @param type      审计类型，1ip+user，2，ip，3，user
     * @param lockTime  锁定时间
     * @param failCount 失败重试次数
     * @return
     */
    public boolean updLockTime(int type, int lockTime, int failCount) {
        return dao.findById(type).set("lockTime", lockTime)
                .set("sendCount", failCount).update();
    }

    /**
     * 取得所有值
     *
     * @return
     */
    public List<AuditLockTime> findAll() {
        return find("select * from sys_lock_time_gap");
    }
}
