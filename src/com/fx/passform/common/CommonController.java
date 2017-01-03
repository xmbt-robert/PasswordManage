package com.fx.passform.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.fx.passform.intercept.AdminLogInterceptor;
import com.fx.passform.intercept.AuthInterceptor;
import com.fx.passform.intercept.OperationLogInterceptor;
import com.fx.passform.model.AdminControl;
import com.fx.passform.model.AuditLockTime;
import com.fx.passform.model.AuditSendTime;
import com.fx.passform.model.Config;
import com.fx.passform.model.JFreeBarChartRender;
import com.fx.passform.model.JFreeLineChartRender;
import com.fx.passform.model.LockStatus;
import com.fx.passform.model.OptLog;
import com.fx.passform.model.Sms;
import com.fx.passform.model.loginValidate;
import com.fx.passform.plugin.Module;
import com.fx.passform.util.GetDate;
import com.fx.passform.util.MailKit;
import com.fx.passform.util.SMSKit;
import com.fx.passform.util.SendUtil;
import com.fx.passform.util.StringUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * CommonController
 *
 * @param <HttpRequest>
 */
@Before(AuthInterceptor.class) //设置一个对所有action 的访问拦截
public class CommonController extends Controller {

    public static final String SUCCESS = "success";

    public static final String ERROR = "error";
    
    public static final String VALIDATE = "validate";

    private static Logger logger = Logger.getLogger(CommonController.class);
    
    private static final String CURRENTDATE = GetDate.getCurrentDate();
    
    public void renderSUCCESS() {
        renderJson(JsonKit.toJson(SUCCESS));
    }

    //验证通过
    public void renderERROR() {
        renderJson(JsonKit.toJson(ERROR));
    }
    
    public void renderVALIDATE(){
    	renderJson(JsonKit.toJson(VALIDATE));
    }
    
   /**
     * 管理员登陆首页
     */
    @ClearInterceptor
    public void login() {
        render("/common/login.jsp");
    }
    
    /**
     * 退出
     */
    @ClearInterceptor
    public void logout() {
    	getSession().removeAttribute("name");
    	getSession().removeAttribute("department");
    	login();
    }
    
    /**
     * 前台首页
     */
    @ClearInterceptor
    public void passwordService() {
    	index();
    }
    
    /**
     * 后台管理页面
     */
    public void manage() {
        render("/common/manage.jsp");
    }
    
    /**
     * 首页
     */
    @ClearInterceptor
    public void index() {
        render("/common/index.jsp");
    }

    /**
     * 解锁账号
     */
    @ClearInterceptor
    public void page01() {
    	Config.refreshConfig();
        render("/common/page01.jsp");
    }

    /**
     * 重置密码
     */
    @ClearInterceptor
    public void page02() {
    	Config.refreshConfig();
        render("/common/page02.jsp");
    }

    /**
     * 更改密码
     */
    @ClearInterceptor
    public void page03() {
    	Config.refreshConfig();
        render("/common/page03.jsp");
    }

    /**
     * 更新信息
     */
    @ClearInterceptor
    public void page04() {
        render("/common/page04.jsp");
    }

    /**
     * 验证码查询
     */
  
   /* @Before(CacheInterceptor.class)   //信息进行缓存
    @CacheName("cacheCheck")*/
    public void codeSearch() {
         render("/common/manage_codesearch.jsp");
    }
    public void codeSearchData() {
    	Integer pageNumber = getParaToInt("page",1);
    	Integer pageSize = getParaToInt("rows",1);
    	
    	String user = getPara("userName");
    	String startDate = "";
    	String endDate = "";
    	if(StringUtil.isValid(getPara("startDate"))){
    		startDate = getPara("startDate")+" 00:00:00";
    	}
    	if(StringUtil.isValid(getPara("endDate"))){
    		endDate = getPara("endDate")+" 23:59:59";
    	}
    	Integer status = getParaToInt("expiresStatus",0);
    	
    	Page<Sms> page = Sms.dao.findSms(pageNumber,pageSize, user ,startDate,endDate, status,null);
    	
	    Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
	       jsonMap.put("total", page.getTotalRow());//total键 存放总记录数，必须的  
	       jsonMap.put("rows", page.getList());//rows键 存放每页记录 list  
    	renderJson(JsonKit.toJson(jsonMap));
    }
    
    /**
     * 用户查询
     */
    public void userSearch() {
        render("/common/manage_usersearch.jsp");
    }
    //根据用户名,姓名   进行条件查询
	public void userSearchData() {
    	String userName = getPara("userName");
    	String realName = getPara("realName");
	    	List<Map<String,String>> list = SendUtil.getUserInfo(realName,userName);
		    Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
		       jsonMap.put("total", list.size());//total键 存放总记录数，必须的  
		       jsonMap.put("rows", list);//rows键 存放每页记录 list  
	    	renderJson(JsonKit.toJson(jsonMap));
    }
	
	 /**
     * 用户查询-编辑用户
     */
	@Before(AdminLogInterceptor.class)     //后台管理操作拦截
	@Module("编辑用户")
    public void editUser() {
    	String username = getPara("username");
    	String mobile = getPara("mobile");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	boolean isOk = SendUtil.modifyUserMobile(username, mobile);
    	String loginName = getSessionAttr("name");
    	this.setAttr("operator", loginName);
    	if(isOk){
    		renderSUCCESS();
    		this.setAttr("optStatus", 0);
    	}
    	else{
    		renderERROR();
    		this.setAttr("optStatus", 1);
    	}
    }
    

    /**
     * 系统设置-验证码异常报表
     */
    public void systemSet() {
        render("/common/manage_systemset.jsp");
    }
    
    /**
     * 报表审计统计
     */
    public void SmsReport(){
    	render("/common/manage_smscount.jsp");
    }
    
    
    /**
     * 生成柱状图的报表图片
     */
    
    public void doBarChart(){
    	String startDate = getPara("startDate");
    	if(StringUtil.isValid(startDate)){
    		startDate = getPara("startDate") ;
    	}
    	else{
    		startDate = GetDate.getYear(); //获取当前系统年份
    	}
    	
    	HttpServletRequest request = getRequest();
    	HttpServletResponse response = getResponse();
    	JFreeBarChartRender chartRender = new JFreeBarChartRender(request, response, startDate);
    	chartRender.render();
    	String filename = chartRender.getFilename();
    	String maparea = chartRender.getMap();
    	
//    	request.setAttribute("filename", filename);
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("maparea", maparea);
    	map.put("filename", filename);
    	
    	renderJson(JsonKit.toJson(map));
    }
    
    /**
     * 点击柱子生成所选月的每天的详细短信发送量
     */
    public void detailInfo(){
    	render("/common/manage_smsdetailcount.jsp");
    }
    
    /**
     * 生成折线图的报表图片
     */
    
    public void doLineChart(){
    		String selectYearMonth = getPara("selectYearMonth");
	    	JFreeLineChartRender chartRender = new JFreeLineChartRender(selectYearMonth);
	    	render(chartRender);
    }
    
    
    /**
     * 系统设置-AD服务设置
     */
  //初始化ldap配置
//    @Before(CacheInterceptor.class)   //信息进行缓存
//    @CacheName("cacheCheck")
    public void initAd(){
    	Config config = new Config();
    	List<Config> ldapList = Config.dao.findByType(1);
    	if(ldapList != null && ldapList.size() > 0){
    		config = ldapList.get(0);
    	}
    	renderJson(JsonKit.toJson(config));
    }
    
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("LDAP设置")
    public void adServiceSet() {
    	String ip = getPara("ip");
    	int port = getParaToInt("port");
    	String user = getPara("user");
    	String pass = getPara("pass");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	Config.dao.updCfg(1,ip,port,user,pass);
    	renderJson(JsonKit.toJson(SUCCESS));
    	this.setAttr("optStatus", 0);
    }
   
    /**
     * 系统设置-短信网关设置
     */
    //初始化短信配置
//    @Before(CacheInterceptor.class)   //信息进行缓存
//    @CacheName("cacheCheck")
    
    public void initSms(){
    	Config config = new Config();
    	List<Config> smsList = Config.dao.findByType(2);
    	if(smsList != null && smsList.size() > 0){
    		config = smsList.get(0);
    	}
    	renderJson(JsonKit.toJson(config));
    }
    
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("短信设置")
    public void smsGatewaySet() {
    	String ip = getPara("ip");
    	int port = getParaToInt("port");
    	String user = getPara("user");
    	String pass = getPara("pass");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	Config.dao.updCfg(2,ip,port,user,pass);
    	SMSKit.getInfo().setIp(ip);
    	SMSKit.getInfo().setPort(String.valueOf(port));
    	SMSKit.getInfo().setUser(user);
    	SMSKit.getInfo().setPass(pass);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	renderJson(JsonKit.toJson(SUCCESS));
    	this.setAttr("optStatus", 0);
    }

    /**
     * 系统设置-邮箱设置
     */
    //初始化邮箱配置
//    @Before(CacheInterceptor.class)   //信息进行缓存
//    @CacheName("cacheCheck")
    public void initEmail(){
    	Config config = new Config();
    	List<Config> emailList = Config.dao.findByType(3);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	if(emailList != null && emailList.size() > 0){
    		config = emailList.get(0);
    	}
    	renderJson(JsonKit.toJson(config));
    	this.setAttr("optStatus", 0);
    }
    
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("邮箱设置")
    public void emailSystemSet() {
    	String ip = getPara("ip");
    	int port = getParaToInt("port");
    	String user = getPara("user");
    	String pass = getPara("pass");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	Config.dao.updCfg(3,ip,port,user,pass);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	MailKit.getInfo().setMailServerHost(ip);
    	MailKit.getInfo().setMailServerPort(String.valueOf(port));
    	MailKit.getInfo().setUserName(user);
    	MailKit.getInfo().setPassword(pass);
    	renderJson(JsonKit.toJson(SUCCESS));
    	this.setAttr("optStatus", 0);
    }
    
    /**
     * 系统设置-修改短信发送间隔时间
     */
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("短信发送时间设置")
    public void messageTimeSet() {
    	Integer time = getParaToInt("message_time");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	AuditSendTime.dao.updGapTime(2, time);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	renderJson(JsonKit.toJson(SUCCESS));
    	this.setAttr("optStatus", 0);
    }
    
    /**
     * 系统设置-修改邮件发送间隔时间
     */
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("邮件发送时间设置")
    public void emailTimeSet() {
    	Integer time = getParaToInt("email_time");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	AuditSendTime.dao.updGapTime(1, time);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	renderJson(JsonKit.toJson(SUCCESS));
    	this.setAttr("optStatus", 0);
    }
    
    /**
     * 系统设置-审计设置
     */
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("审计设置")
    public void auditSet() {
    	Integer failCount = getParaToInt("failCount");
    	Integer lockTime = getParaToInt("lockTime");
    	Integer type = getParaToInt("choose");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	AuditLockTime.dao.updLockTime(type,lockTime, failCount);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	renderJson(JsonKit.toJson(SUCCESS));
    	this.setAttr("optStatus", 0);
    }
    
    /**
     * 系统设置-验证码异常报表
     */
    
    //根据输入内容查找数据
	public void authCodeAbnormalSearch(){
		Integer pageNumber = getParaToInt("page",1);
    	Integer pageSize = getParaToInt("rows",1);
    	//String content = "";
		String content = getPara("content");
			Page<LockStatus> page = LockStatus.dao.findResult(pageNumber, pageSize, content);
		    Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
		       jsonMap.put("total", page.getTotalRow());//total键 存放总记录数，必须的  
		       jsonMap.put("rows", page.getList());//rows键 存放每页记录 list  
	    	renderJson(JsonKit.toJson(jsonMap));
		
//			String content = getPara("content");
//	    	List<LockStatus> list = LockStatus.dao.searchResult(content);
//		    Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
//		       jsonMap.put("total", list.size());//total键 存放总记录数，必须的  
//		       jsonMap.put("rows", list);//rows键 存放每页记录 list  
//	    	renderJson(JsonKit.toJson(jsonMap));
	}
    
    /**
     * 系统设置-管理员设置列表
     */
    public void adminSetData() {
    	Integer pageNumber = getParaToInt("page",1);
    	Integer pageSize = getParaToInt("rows",1);
    	Page<AdminControl> page = AdminControl.dao.findAll(pageNumber,pageSize);
	    Map<String, Object> jsonMap = new HashMap<String, Object>();
	       jsonMap.put("total", page.getTotalRow());//total键 存放总记录数，必须的  
	       jsonMap.put("rows", page.getList());//rows键 存放每页记录 list  
    	renderJson(JsonKit.toJson(jsonMap));
    }
    
    /**
     * 系统设置-管理员增加
     */
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("增加管理员")
    public void adminAddData() {
    	String staffId = getPara("staffId");
    	String user = getPara("user");
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	AdminControl.dao.addAdmin(staffId, user);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	renderSUCCESS();
    	this.setAttr("optStatus", 0);
    }
    
    /**
     * 系统设置-管理员删除
     */
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("删除管理员")
    public void adminDeleteData() {
    	String id = getPara("id");  
    	String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
    	AdminControl.dao.delAdminById(id);
    	String loginName = getSessionAttr("name"); //得到当前登陆用户名
    	this.setAttr("operator", loginName);
    	renderSUCCESS();
    	this.setAttr("optStatus", 0);
    }
    
    
    /**
     * 报表审计统计
     */    
    public void tabCount() {
        render("/common/manage_tabcount.jsp");
    }
    public void tabCountData() {
    	Integer pageNumber = getParaToInt("page",1);
    	Integer pageSize = getParaToInt("rows",1);
    	Page<OptLog> page = OptLog.dao.findAll(pageNumber,pageSize);
	    Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
	       jsonMap.put("total", page.getTotalRow());//total键 存放总记录数，必须的  
	       jsonMap.put("rows", page.getList());//rows键 存放每页记录 list  
    	renderJson(JsonKit.toJson(jsonMap));
    }
    
    /**
     * '检查ldap账号是否正常
     */
    @ClearInterceptor
    public void checkValue() {         //查找用户账号在ldap上的状态
        String value = getPara("value");
        String[] val = SendUtil.getUserStatus(value);
        renderJson(JsonKit.toJson(val));
        logger.info("用户状态：["+val[0]+"]");
    }
    
    
    /**
     * 校验管理员登陆的用户名和密码是否正确
     */
    @ClearInterceptor
    @Before(AdminLogInterceptor.class)     //后台管理操作拦截
    @Module("管理员登陆")
    public void  loginCheck(){
    	Config.refreshConfig();
    	HttpSession session = getSession();     //获取登陆用户名用于存入session
        String name = getPara("name");
        String pass = getPara("password");
        String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
        Long count = loginValidate.dao.login(name);  //判断用户是否存在于管理员列表中
        //this.setAttr("operateIP", operateIP);
       
        if(count < 1 && name != ""){
        	renderVALIDATE();
        	this.setAttr("operator", name);
        	this.setAttr("optStatus", 1);
        	return ;
        } else {
            boolean isOk=SendUtil.verifyPass(name,pass);
            this.setAttr("operator", name);
            if(isOk && pass!=""){
            	session.setAttribute("name", name);        //存登陸成功的用戶名到session
            	String departName = SendUtil.getUserDepartNO(name); //存登陸成功的用戶的所屬部門信息到session
            	session.setAttribute("departName", departName);
            	/*int index = departName.lastIndexOf("-");
            	if(index > -1){
    	        	String userDepartName = departName.substring(index+1);
    	        	session.setAttribute("departName", userDepartName);
            	}*/
            	
            	 renderSUCCESS();
            	 this.setAttr("optStatus", 0);
            	 return ;
            }else{
                renderERROR();
                this.setAttr("optStatus", 1);
                return ;
            } 	
        }
    }
    

    /**
     * 校验用户名和密码
     */
    @ClearInterceptor
    public void  vifPassWord(){
        String name = getPara("name");
        String pass = getPara("oldPass");
        boolean isOk=SendUtil.verifyPass(name,pass);
        if(isOk){
            renderSUCCESS();
        }else{
            renderERROR();
        }
    }
    /**
     * 修改密码
     */
    @ClearInterceptor
    @Before(OperationLogInterceptor.class)     //操作拦截
    @Module("修改密码")
    public void modifyPass() {
        String name = getPara("name");
        String oldPass = getPara("oldPass");
        String newPass = getPara("newPass");
        String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
        boolean isOk = SendUtil.modifPassword(name, oldPass, newPass);
        renderJson(JsonKit.toJson(isOk));
        SendUtil.sendEmail(getEmalAdd(name), getTitle("修改密码", isOk), getContent(newPass, isOk));
        this.setAttr("operator", name);
        if (isOk) {
        	this.setAttr("optStatus", 0);
            logger.info("用户["+name+"]密码修改成功");
        } else {
        	this.setAttr("optStatus", 1);
            logger.info("用户["+name+"]密码修改失败");
        }
    }

    /**
     * 解锁用户账号
     */
    @ClearInterceptor
    @Before(OperationLogInterceptor.class)     //操作拦截
    @Module("解锁账号")
    public void unLockUser() {
        String username = getPara("name");
        int status = getParaToInt("status");
        String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
        boolean isOk;
        if(status==(512+16)){
            isOk = SendUtil.setUserStatus(username, "512");
        }else{
            isOk = SendUtil.setUserStatus(username, "66048");
        }
        //boolean isOk = SendUtil.setUserStatus(username, "512");
        renderJson(JsonKit.toJson(isOk));
        SendUtil.sendEmail(getEmalAdd(username), getTitle("解锁用户账号", isOk), getContentByUnlock(username, isOk));
        this.setAttr("operator", username);
        if (isOk) {
        	this.setAttr("optStatus", 0);
            logger.info("用户["+username+"]解锁成功");
        } else {
        	this.setAttr("optStatus", 1);
            logger.info("用户["+username+"]解锁失败");
        }
    }

    /**
     * 重置密码
     */
    @ClearInterceptor
    @Before(OperationLogInterceptor.class)     //操作拦截
    @Module("重置密码")
    public void resetPass() {
        String username = getPara("name");
        String newPass = StringUtil.genRandomNum(0);
        String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
        boolean isOk = SendUtil.resetPass(username, newPass);
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("isOk", isOk);
        values.put("newPass", newPass);
        renderJson(JsonKit.toJson(values));
        SendUtil.sendEmail(getEmalAdd(username), getTitle("重置密码", isOk), getContent(newPass, isOk));
        this.setAttr("operator", username);
        if (isOk) {
        	this.setAttr("optStatus", 0);
            logger.info("用户["+username+"]重置密码成功");
        } else {
        	this.setAttr("optStatus", 1);
            logger.info("用户["+username+"]重置密码重置失败");
        }
    }

    /**
     * 发送验证码
     */
    @ClearInterceptor
    @Before(OperationLogInterceptor.class)     //操作拦截
    @Module("发送短信验证码")
    public void sendPass() {
        String mobile = this.getPara("mobile");
        String username = getPara("name");
        String operateIP = getTrueIp();
        this.setAttr("operateIP", operateIP);
        this.setAttr("operator", username);
        if (!isAudit(getTrueIp(), username)) {
            renderERROR();
            logger.info("用户["+username+"]  手机["+mobile+"]验证码发送超过规定次数");
            this.setAttr("optStatus", 1); //1:失败
            return;
        }
        String valCode = SendUtil.genSMS();
        boolean isOk = SendUtil.sendSMS(valCode, mobile);      //打开将通过短信网关发送正式短信
        renderJson(JsonKit.toJson(isOk));
        
        if (isOk) {
        	this.setAttr("optStatus", 0);   //0:成功
            logger.info("用户["+username+"]  手机["+mobile+"]短信发送成功！");
            //如果找到之前有正常状态的SMS，更新为过期状态
            Sms.dao.updateSms(username, 1, 2);
            //重新生成一条
            Sms.dao.addSms(username, valCode, 1, mobile, username + "@feixun.com.cn");
            //renderSUCCESS();

        } else {
        	this.setAttr("optStatus", 1); //1:失败
            logger.info("用户["+username+"]  手机["+mobile+"]短信发送失败！");
            //renderERROR();  //提示短信发送错误
        }
    }

    /**
     * 验证sms短信验证码是否正确
     */
    @ClearInterceptor
    public void checkSms() {
        String code = getPara("code");
        String username = getPara("name");
        boolean isok = Sms.dao.findSms(username, code);
        renderJson(JsonKit.toJson(isok));
        if (isok) {
            logger.info("用户["+username+"]验证码匹配成功");
        } else {
            logger.info("用户["+username+"]验证码匹配失败");
        }
    }


    /**
     * 统一认证审计
     *
     * @param ip   ip地址
     * @param user 用户名
     * @return
     */
    @ClearInterceptor
    public boolean isAudit(String ip, String user) {
        List<LockStatus> locks = LockStatus.dao.findLockReco(ip, user);
        if (locks == null || locks.size() == 0) {
            LockStatus.dao.addLockStatus(ip, user);
        } else {
            LockStatus lockStatus = locks.get(0);
            int status = lockStatus.getInt("lockStatus");
            //状态==1表示锁定
            if (status == 1) {
                return false;
            }
            int sendCount = lockStatus.getInt("sendCount");
            List<AuditLockTime> lockTimes = AuditLockTime.dao.findAll();
            if (lockTimes.size() > 0) {
                AuditLockTime auditLockTime = lockTimes.get(0);
                int sendCountCfg = auditLockTime.getInt("sendCount");
                //如果发送次数超过规定次数，更新审计表，返回审计失败
                LockStatus.dao.updLockStatus(ip, user);
                if (sendCount + 1 >= sendCountCfg) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 校验验证码是否正确
     */
    @ClearInterceptor
    public void checkCode() {    //图片验证码获取
        String captchaCode = getPara("inputCode");
        Object objMd5RandomCode = this.getSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
        String md5RandomCode = null;
        if (objMd5RandomCode != null) {
            md5RandomCode = objMd5RandomCode.toString();
            //removeSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
        }
        if (CaptchaRender.validate(md5RandomCode, captchaCode)) {
            renderSUCCESS();
        } else {
            renderERROR();
        }
    }

    /**
     * 生成验证码
     */
    @ClearInterceptor
    public void rendCode() {
        CaptchaRender img = new CaptchaRender(4);
        setSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY, img.getMd5RandonCode());
        render(img);
    }

   
    /**
     * java实现穿透代理获取客户端真实ip
     *
     * @return 真实的IP地址
     */
    @ClearInterceptor
    private String getTrueIp() {
        HttpServletRequest request = getRequest();
        String strClientIp = request.getHeader("x-forwarded-for");
        if (strClientIp == null || strClientIp.length() == 0 || "unknown".equalsIgnoreCase(strClientIp)) {
            strClientIp = request.getRemoteAddr();
        } else {
            String[] ipList = strClientIp.split("[,]");
            String strIp;
            for (int index = 0; index < ipList.length; index++) {
                strIp = ipList[0];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    strClientIp = strIp;
                    break;
                }
            }
        }
        return strClientIp;
    }


    /**
     * 得到用户email地址
     *
     * @param username
     * @return
     */
    @ClearInterceptor
    private String getEmalAdd(String username) {
        return username + "@feixun.com.cn";
    }

    /**
     * 得到邮件title
     *
     * @param title
     * @param isOk
     * @return
     */
    @ClearInterceptor
    private String getTitle(String title, boolean isOk) {
        if (isOk) {
            return title + "操作成功!";
        } else {
            return title + "操作失败!";
        }
    }

    /**
     * 得到邮件内容
     *
     * @param pass
     * @return
     */
    @ClearInterceptor
    private String getContent(String pass, boolean isOk) {
        if (isOk) {
        	return "您的密码于"+CURRENTDATE+"修改成功,请妥善保管好密码,请勿泄露！---信息平台"+msg();
        } else {
            return "您的密码于"+CURRENTDATE+"修改失败，请联系信息平台-ITS部门寻求支持!"+msg();
        }
    }

    /**
     * 得到邮件内容
     *
     * @param pass
     * @return
     */
    @ClearInterceptor
    private String getContentByUnlock(String pass, boolean isOk) {
        if (isOk) {
        	return "您的账号于"+CURRENTDATE+"已成功解锁,请妥善保管好密码,请勿泄露！---信息平台"+msg();
        } else {
            return "您的账号于"+CURRENTDATE+"解锁失败，请联系信息平台-ITS部门寻求支持!"+msg();
        }
    }
    
    /**
     * 邮件提示信息
     * @return
     */
	private String msg(){
    	StringBuffer sf=new StringBuffer();  
    	sf.append("<br><br><br><br>");
    	sf.append("我们的密码自助平台为:");
        sf.append("<a href=\"https://password.phicomm.com");  
        sf.append("\">");  
        sf.append(" <FONT   face=\"MS   UI   Gothic\"   size=\"3\" color=\"red\"><b>https://password.phicomm.com</b></FONT>");  
        sf.append("</a>");  
        sf.append("<br>");
        sf.append("您可以通过以上自助平台解锁、重置以及修改密码。");
        sf.append("<br><br>");
        sf.append("【技术支持】");
        sf.append("<br>");
        sf.append("如有任何IT相关问题，请及时联系我们以获得技术支持：");
        sf.append("<br>");
        sf.append("通过电话：80000");
        sf.append("<br>");
        sf.append("通过技术服务单:");
        sf.append("<a href=\"http://itsm.phicomm.com");  
        sf.append("\">");  
        sf.append("http://itsm.phicomm.com");  
        sf.append("</a>");
        sf.append("<br>");
        sf.append("通过邮件:");
        sf.append("<a href=\"mailto:itsupport@feixun.com.cn");  
        sf.append("\">itsupport@feixun.com.cn");  
        sf.append("</a>");
        sf.append("<br>");
        sf.append("通过OC:");
        sf.append("<a href=\"mailto:itsupport@feixun.com.cn");  
        sf.append("\">itsupport@feixun.com.cn");  
        sf.append("</a>");
        sf.append("<br>");
        sf.append("IT技术支持微信公众号：");
        sf.append("PHICOMM-ITsupport");  
        sf.append("<br>");
        sf.append("<img src=\"https://password.phicomm.com/images/weixin.png");
        sf.append("\">");
        return sf.toString();
    }
}
