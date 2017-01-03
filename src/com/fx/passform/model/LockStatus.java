package com.fx.passform.model;

import com.fx.passform.util.StringUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LockStatus's Model
 * <p/>
 * Created by fan.xu on 2014/10/21.
 */
public class LockStatus extends Model<LockStatus> {
    public static final LockStatus dao = new LockStatus();

    /**
     * 增加锁定记录
     *
     * @param ip
     * @param user
     * @return
     */
    public boolean addLockStatus(String ip, String user) {
        LockStatus lockStatus = new LockStatus();
        lockStatus.set("operateTime",new Timestamp(new Date().getTime())).set("lockStatus", 0);
        return lockStatus.set("IP", ip).set("userName", user).set("sendCount", 1).save();
    }

    /**
     * 根据审计策略，找到当前用户的审计结果
     *
     * @param ip
     * @param user
     * @return
     */
    public List<LockStatus> findLockReco(String ip, String user) {
        List<AuditLockTime> locks = AuditLockTime.dao.findAll();
        if (locks.size() > 0) {
            StringBuilder builder = new StringBuilder(256);
            builder.append("select * from data_audit ");
            AuditLockTime lockTime = locks.get(0);
            int type = lockTime.getInt("type");
            switch (type) {
                case 1:
                    builder.append(" where userName = ");
                    builder.append("'"+user+"'");
                    builder.append(" and ");
                    builder.append(" IP = ");
                    builder.append("'"+ip+"'");
                    break;
                case 2:
                    builder.append(" where ip = ");
                    builder.append("'"+ip+"'");
                    break;
                case 3:
                    builder.append(" where userName = ");
                    builder.append("'"+user+"'");
            }
            return find(builder.toString());
        }
        return null;
    }

    /**
     * 根据用户名，ip等更新用户锁定状态。
     * 如果失败次数大于
     *
     * @param ip
     * @param user
     * @return
     */
    public boolean updLockStatus(String ip, String user) {
        List<LockStatus> locks = findLockReco(ip, user);
        if (locks.size() > 0) {
            LockStatus lock = locks.get(0);
            int count = lock.getInt("sendCount");
            AuditLockTime locktime = AuditLockTime.dao.findAll().get(0);
            int sendCount = locktime.getInt("sendCount");
            lock.set("sendCount", count + 1).set("operateTime", new java.sql.Timestamp(new Date().getTime()));
            if (count + 1 >= sendCount) {
                lock.set("lockStatus", 1);
            }
            return lock.update();
        }
        return false;
    }

    /**
     * 返回所有审计数据
     * @return
     */
     public List<LockStatus> findAll(){
         return find("select * from data_audit");
     }
     
     /**
      * 返回查询符合的数据
      * @return
      */
	public List<LockStatus> searchResult(String content){
    	 StringBuffer sql = new StringBuffer("select * from data_audit where 1=1 ");
    	 if(content != null || content.length()>0){
    		 sql.append(" or IP= '").append(content).append("' ");
    		 sql.append(" or userName= '").append(content).append("' ");
    		 sql.append(" or sendCount= '").append(content).append("' ");
    		 sql.append(" or failCount= '").append(content).append("' ");
    		 sql.append(" or operateTime= '").append(content).append("' ");
    		 sql.append(" or lockStatus= '").append(content).append("' ");
    	 }
         return find(sql.toString());
     }
	
	public Page<LockStatus> findResult(int pageNum, int pageCount,String content) {
		// 组装条件
		StringBuffer sql = new StringBuffer("from data_audit ");

		if (StringUtil.isValid(content)) {   //值不为空时
			sql.append("where ");
			if (StringUtil.isContains(content)) { // 判断是否包含字符‘.’
				sql.append(StringUtil.isValid(content) ? " IP =  ?" : "");
				sql.append(StringUtil.isValid(content) ? " or userName =  ?" : "");
				sql.append("order by id desc");
				ArrayList vals = new ArrayList();
				for (int i = 0; i < 2; i++) {
					vals.add(content);
				}
				return dao.paginate(pageNum, pageCount, "select * ",sql.toString(), vals.toArray());
			}else if (StringUtil.isDigital(content)) { // 判断是否是数字
				sql.append(StringUtil.isValid(content) ? " sendCount =  ?" : "");
				sql.append(StringUtil.isValid(content) ? " or failCount =  ?" : "");
				sql.append("order by id desc");
				ArrayList vals = new ArrayList();
				for (int i = 0; i < 2; i++) {
					vals.add(content);
				}
				return dao.paginate(pageNum, pageCount, "select * ",sql.toString(), vals.toArray());
			}else if (StringUtil.isChineseChar1(content)) { // 判断是否是中文“正常”
				sql.append(" lockStatus =  0");
				sql.append(" order by id desc");
				return dao.paginate(pageNum, pageCount, "select * ",sql.toString());
			} else if (StringUtil.isChineseChar2(content)) { // 判断是否是中文“锁定”
				sql.append(" lockStatus =  1");
				sql.append(" order by id desc");
				return dao.paginate(pageNum, pageCount, "select * ",sql.toString());
			}else if (StringUtil.isChineseChar3(content)) { // 判断是否是搜索框默认值，是就查询出所有值
				sql.append(" 1=1 ");
				sql.append(" order by id desc");
				return dao.paginate(pageNum, pageCount, "select * ",sql.toString());
			}else {
				/*sql.append(StringUtil.isValid(content) ? " IP =  ?" : "");
				sql.append(StringUtil.isValid(content) ? " or userName =  ?" : "");
				sql.append(StringUtil.isValid(content) ? " or sendCount = ?" : "");
				sql.append(StringUtil.isValid(content) ? " or failCount = ?" : "");
				sql.append(StringUtil.isValid(content) ? " or lockStatus = ?" : "");
				// 组装值
				ArrayList vals = new ArrayList();
				for (int i = 0; i < 5; i++) {
					vals.add(content);
				}
				return dao.paginate(pageNum, pageCount, "select * ",sql.toString(), vals.toArray());*/
				sql.append(" 1!=1 ");
				return dao.paginate(pageNum, pageCount, "select * ", sql.toString());
			}
		} else {
			sql.append("where 1=1 ");
			sql.append("order by id desc");
			return dao.paginate(pageNum, pageCount, "select * ", sql.toString());
		}
	}
     
    public Page<LockStatus> findAll(int pageNum, int pageCount) {
        return dao.paginate(pageNum, pageCount, "select * ", "from data_audit");
    }

}
