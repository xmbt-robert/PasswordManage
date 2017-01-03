package com.fx.passform.Timer;

import com.fx.passform.model.LockStatus;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 定时更新登录失败锁定清理
 * <p/>
 * Created by fan.xu on 2014/10/22.
 */
public class UnlockTimer {
    /**
     * 定时器
     */
    private Timer timer;

    private static Logger logger = Logger.getLogger("UnlockTimer");

    /**
     * 构造
     *
     * @param timer
     */
    public UnlockTimer(Timer timer) {
        this.timer = timer;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        timer.scheduleAtFixedRate(new UnlockTask(),calendar.getTime(), 24*60*60*1000);
    }

    /**
     * 具体任务
     */
    class UnlockTask extends TimerTask {
        @Override
        public void run() {
            List<LockStatus> lockStatuses = LockStatus.dao.findAll();
            logger.info("Unlock account starting ..." + new Date().toString() +" size="+lockStatuses.size());
            if (lockStatuses.size() > 0) {
                for (LockStatus lock : lockStatuses) {
                    logger.info("Unlock account  ip[" + lock.getStr("IP") + "] user[" + lock.getStr("userName") + "]");
                    lock.set("sendCount", 0).set("lockStatus", 0).update();
                }
            }
            logger.info("Unlock account end" + new Date().toString());
        }
    }
}
