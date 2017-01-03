package com.fx.passform.plugin;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 发送短信验证码
 * <p/>
 * Created by fan.xu on 2014/10/24.
 */
public class SMSSender implements Callable<HashMap<String, Object>> {

    /**
     * SMS配置信息
     */
    private SMSInfo info;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 验证码
     */
    private String valCode;

    public SMSSender(SMSInfo info, String phone, String valCode) {
        this.info = info;
        this.phone = phone;
        this.valCode = valCode;
    }

    @Override
    public HashMap<String, Object> call() throws Exception {
        String time = info.getSmstime();
        String id = info.getTmpId();
        CCPRestSmsSDK api = info.getRestAPI();
        HashMap<String, Object> result = api.sendTemplateSMS(phone, id, new String[]{valCode, time});
        return result;
    }

    public static void main(String[] args) {
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8a48b55148fe486001491cd5f20c1442", "782d777296c34266bc4b6c733a5b338e");
        restAPI.setAppId("8a48b55148fe486001491cd639d41444");
        //CCPRestSmsSDK api = info.getRestAPI();
        HashMap<java.lang.String, java.lang.Object> result = restAPI.sendTemplateSMS("15618533436", "1", new String[]{"123456", "3"});
        System.out.println("SDKTestGetSubAccounts result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
    }
}
