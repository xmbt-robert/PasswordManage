package com.fx.passform.model;

import com.fx.passform.util.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.Year;

/**
 * Sms's Model
 * <p/>
 * Created by fan.xu on 2014/10/21.
 */
public class Sms extends Model<Sms> {
    public static final Sms dao = new Sms();

    /**
     * 根据用户名和状态，取得短信SMS数据
     *
     * @param username 用户名
     * @param status   状态(使用3，过期2，正常1)
     * @return
     */
    public List<Sms> getSmsByUsername(String username, int status) {
        StringBuilder builder = new StringBuilder(256);
        builder.append("select * from data_shortmessage where userName =");
        builder.append("'" + username + "'");
        builder.append(" and expiresStatus = ");
        builder.append(status);
        return find(builder.toString());
    }

    /**
     * 创建短信
     *
     * @param username 用户名
     * @param code     验证码
     * @param status   状态
     * @param phone    电话号码
     * @param email    邮箱
     */
    public boolean addSms(String username, String code, Integer status, String phone, String email) {
        Sms sms = new Sms();
        sms.set("userName", username).set("authCode", code).set("expiresStatus", status);
        return sms.set("emailAddress", email).set("phoneNum", phone).save();
    }

    /**
     * 根据用户名更新过期的短信验证码
     *
     * @param username 用户名
     */
    public void updateSms(String username) {
        List<Sms> smss = getSmsByUsername(username, 0);
        if (smss.size() > 0) {
            for (Sms sm : smss) {
                Timestamp time = sm.get("createTime");
                int gap = new AuditSendTime().getGapTime(2);
                if ((new Date().getTime() - time.getTime()) >= gap * 60 * 1000) {
                    sm.set("expiresStatus", 2).update();
                }
            }
        }
    }

    /**
     * 根据用户名更新过期的短信验证码
     *
     * @param username 用户名
     */
    public void updateSms(String username, int oldStatus, int newStatus) {
        List<Sms> smss = getSmsByUsername(username, oldStatus);
        if (smss.size() > 0) {
            for (Sms sm : smss) {
                sm.set("expiresStatus", newStatus).update();
            }
        }
    }

    /**
     * 根据用户名查找短信验证码
     *
     * @param username
     * @param code
     * @return
     */
    public boolean findSms(String username, String code) {
        boolean find = false;
        List<Sms> smss = getSmsByUsername(username, 1);
        if (smss.size() > 0) {
            for (Sms sm : smss) {
                String smscode = sm.getStr("authCode");
                if (smscode.equals(code)) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }
    
//  public List<Sms> getAllSms() {
//  StringBuilder builder = new StringBuilder(256);
//  builder.append("select count(*) from data_shortmessage where 1=1");
//  return find(builder.toString());
//}

//public static Long getAllSms(String startTime,String endTime){
//	StringBuilder builder = new StringBuilder(256);
//  builder.append("select * from data_shortmessage where 1=1");
//  builder.append(" and createTime>'" + startTime + "'");
//  builder.append(" and createTime<'" + endTime + "'");
//	List<Object> list = Db.query(builder.toString());
//	return (long)list.size();
//}
    
    /**
     * 分组查询出当年度每月的发送短信条数
     * @return 
     */
    public static ArrayList<Object> getToYearSms(){
    	StringBuilder builder = new StringBuilder(256);
    	//分组查询出每月的发送短信条数
        //builder.append("SELECT DATE_FORMAT(createTime, '%Y-%m') as date ,count(*) as count FROM data_shortmessage GROUP BY YEAR (createTime),MONTH (createTime);");
    	
    	//分组查询出当年度每月的发送短信条数
    	//builder.append("SELECT DATE_FORMAT(createTime, '%Y-%m') as date ,count(*) as count FROM data_shortmessage WHERE createTime > (CONCAT(DATE_FORMAT(now() ,'%Y'), '-01-01')) AND createTime <= (CONCAT(DATE_FORMAT(now() ,'%Y'),'-12-31')) GROUP BY YEAR (createTime),MONTH (createTime);");
    	
    	//分组查询出当年度每月的发送短信条数(没有的用0表示-改进sql)
    	builder.append("SELECT CONCAT( DATE_FORMAT( now(), '%Y'),'年',a.VMonth,'月') AS date, IFNULL(count,0) AS count from XNumber a left JOIN (SELECT createTime AS date,count(*) AS count FROM data_shortmessage  WHERE createTime > (CONCAT(DATE_FORMAT(now(), '%Y'),'-01-01')) AND createTime <= (CONCAT(DATE_FORMAT(now(), '%Y'),'-12-31')) GROUP BY YEAR (createTime), MONTH (createTime)) b on a.VMonth=cast(DATE_FORMAT(date,'%m') as signed integer) ORDER BY a.VMonth");
        ArrayList<Object> list = (ArrayList<Object>) Db.query(builder.toString());
        
		return list;
	}
    
    /**
     * 获取当前年月，统计当前月每天的短信发送量（缺点：每月全部是按31天计算，待完善。。）
     * @return
     */
//    public static ArrayList<Object> getToYearToMonthSms(){
//    	StringBuilder builder = new StringBuilder(256);
//    	//分组查询出当年度每月的发送短信条数(没有的用0表示-改进sql)
//		String sql = "SELECT CONCAT(DATE_FORMAT(now(), '%Y'),'年',DATE_FORMAT(now(), '%m'),'月',a.VDay,'日') AS date, " +
//				"IFNULL(count,0) AS count " +
//				"FROM xday a left JOIN " +
//				"(SELECT createTime AS date,count(*) AS count " +
//				"FROM data_shortmessage " +
//				"WHERE createTime > " +
//				"( CONCAT( DATE_FORMAT(now(), '%Y'),'-', DATE_FORMAT(now(), '%m'), '-01')) " +
//				"AND createTime <= " +
//				"(CONCAT(DATE_FORMAT(now(), '%Y'),'-',DATE_FORMAT(now(), '%m'),'-31')) " +
//				"GROUP BY YEAR (createTime), DATE (createTime)) b on a.VDay=cast(DATE_FORMAT(date,'%d') as signed integer) " +
//				"ORDER BY a.VDay";
//		builder.append(sql);
//        ArrayList<Object> list = (ArrayList<Object>) Db.query(builder.toString());
//		return list;
//	}
    
    /**
     * 根据选择的年月，展示出选择年月的每天短信发送量
     * @param year_Month
     * @return
     */
    public static ArrayList<Object> getToYearToMonthSms(String year_Month){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//设置日期格式
    	if(year_Month == null || year_Month.trim().length() < 1){
    		year_Month = sdf.format(new Date());
    	}
    	StringBuilder builder = new StringBuilder(256);
		String sql = "SELECT CONCAT(DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%Y'),'-',DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%m'),'-',a.VDay) AS date, " +
				"IFNULL(count,0) AS count " +
				"FROM xday a left JOIN " +
				"(SELECT createTime AS date,count(*) AS count " +
				"FROM data_shortmessage " +
				"WHERE createTime > " +
				"( CONCAT( DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%Y'),'-', DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%m'), '-01')) " +
				"AND createTime <= " +
				"(CONCAT(DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%Y'),'-',DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%m'),'-31')) " +
				"GROUP BY YEAR (createTime), DATE (createTime)) b on a.VDay=cast(DATE_FORMAT(date,'%d') as signed integer) " +
				"ORDER BY a.VDay";
		builder.append(sql);
        ArrayList<Object> list = (ArrayList<Object>) Db.query(builder.toString());
		return list;
	}
    
    /**
     * 根据柱状图所选年月进行计算并返回月天数
     * @return
     */
	public static Integer getSelectYearMonth(String year_Month){
    	StringBuilder builder = new StringBuilder(256);
    	//截取从右往左的2个字符【2014-02-28】（即月的最后一天，也即当月天数）
    	String sql = "SELECT RIGHT (LAST_DAY(" +
    			"( CONCAT( DATE_FORMAT(" +
    			"(CONCAT('"+ year_Month +"','-01')), '%Y'),'-', " +
    			"DATE_FORMAT((CONCAT('"+ year_Month +"','-01')), '%m'), '-01'))), 2);";
    	builder.append(sql);
    	List<Integer> days =  Db.query(builder.toString());
    	return days.get(0);
//    	ArrayList<Object> days =  (ArrayList<Object>) Db.query(builder.toString());
//		return (ArrayList<Object>) days;
    }
    
    
    /**
     * 分组查询出所选年度每月的发送短信条数
     * @param startDate
     * @param endDate
     * @return
     */
    public static ArrayList<Object> getSelectYearSms(String year){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");//设置日期格式
    	if(year == null || year.trim().length() < 1){
    		year = sdf.format(new Date());
    	}
    	StringBuilder builder = new StringBuilder(256);
    	//分组查询出当年度每月的发送短信条数(没有的用0表示-改进sql)
		String sql = "SELECT CONCAT( '"+ year +"', '-',a.VMonth) AS date, " +
				"IFNULL(count,0) AS count from XMonth a left " +
				"JOIN (SELECT createTime AS date," +
				"count(*) AS count " +
				"FROM data_shortmessage  " +
				"WHERE createTime > (CONCAT('"+ year +"','-01-01')) " +
				"AND createTime <= (CONCAT('"+ year +"','-12-31')) " +
				"GROUP BY YEAR (createTime), MONTH (createTime)) b on a.VMonth=cast(DATE_FORMAT(date,'%m') as signed integer) " +
				"ORDER BY a.VMonth";
		builder.append(sql);
        ArrayList<Object> list = (ArrayList<Object>) Db.query(builder.toString());
		return list;
	}
    
    /**
     * 获取所选年份的目前短信发送总量
     * @param year
     * @return 
     * @return
     */
    public static long getSelectYearSmsCount(String year){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");//设置日期格式
    	if(year == null || year.trim().length() < 1){
    		year = sdf.format(new Date());
    	}
    	StringBuilder builder = new StringBuilder(256);
		String sql = "select id from data_shortmessage where createTime > (CONCAT('"+ year +"','-01-01')) AND createTime <= (CONCAT('"+ year +"','-12-31'))";
		builder.append(sql);
        List<Integer> list =  Db.query(builder.toString());
		return list.size();
	}
    
    
    
    
    
    
    
    
    
    
    //按日期分组查询出每日的发送短信条数
//    public static ArrayList<Object> getAllSms(String startTime,String endTime){
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//    	String today = sdf.format(new Date());
//    	StringBuilder builder = new StringBuilder(256);
//    	
//        builder.append("SELECT DATE_FORMAT(createTime, '%Y-%m-%d') as date ,count(*) as count FROM data_shortmessage where 1=1"); GROUP BY YEAR (createTime),MONTH (createTime),DAY (createTime);");
//        builder.append(StringUtil.isValid(startTime) ? " and createTime > ?" : today);
//        builder.append(StringUtil.isValid(endTime) ? " and createTime  <= ?" : today);
//        builder.append(c)
//        
//        ArrayList<Object> list = (ArrayList<Object>) Db.query(builder.toString());
//		return list;
//	}
    
    
    
    public Long getAllSmsData(String startTime,String endTime){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    	String today = sdf.format(new Date());
    	StringBuilder builder = new StringBuilder(256);
        builder.append("select * from data_shortmessage where 1=1");
        builder.append(StringUtil.isValid(startTime) ? " and createTime > ?" : today);
        builder.append(StringUtil.isValid(endTime) ? " and createTime  <= ?" : today);
        //组装
        ArrayList vals = new ArrayList();
        if (StringUtil.isValid(startTime)) vals.add(startTime);
        if (StringUtil.isValid(endTime)) vals.add(endTime);
        List<Object> list = Db.query(builder.toString(), vals.toArray());
		return (long)list.size();
	}
    
    

    /**
     * 分页查询所有短信
     *
     * @param pageNum   　页码
     * @param pageCount 　每页总条数
     * @return
     */
    public Page<Sms> findAll(int pageNum, int pageCount) {
        return dao.paginate(pageNum, pageCount, "select * ", "from data_shortmessage where 1=1 ");
    }

    /**
     * 根据条件进行查询分析
     *
     * @param pageNum   页码
     * @param pageCount 每页总条数
     * @param user
     * @param start
     * @param end
     * @param status
     * @param phone
     * @return
     */
    public Page<Sms> findSms(int pageNum, int pageCount, String user, String start,
    		String end, int status, String phone) {
        //组装条件
        StringBuilder builder = new StringBuilder(512);
        builder.append("from data_shortmessage where 1=1 ");
        builder.append(StringUtil.isValid(user) ? " and userName like ?" : "");
        builder.append(StringUtil.isValid(start) ? " and createTime  > ?" : "");
        builder.append(StringUtil.isValid(end) ? " and createTime <= ?" : "");
        builder.append(StringUtil.isValid(status) ? " and expiresStatus = ?" : "");
        builder.append(StringUtil.isValid(phone) ? " and phoneNum like ?" : "");
        builder.append(" order by id desc");
        
        //组装值
        ArrayList vals = new ArrayList();
        if (StringUtil.isValid(user)) vals.add("%" + user + "%");
        if (StringUtil.isValid(start)) vals.add(start);
        if (StringUtil.isValid(end)) vals.add(end);
        if (StringUtil.isValid(status)) vals.add(status);
        if (StringUtil.isValid(phone)) vals.add("%" + phone + "%");

        return dao.paginate(pageNum, pageCount, "select * ", builder.toString(), vals.toArray());
    }
}
