package com.leyou.untils;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsUtils {

    public static String accesskey ="LTAI4G7KeHkUag48G5WejNgP";
    public static String accessSecret = "cKPzFK2SFUtHYjaBwhJFWnzYXi0PIl";

    public static void sendMsg(String phone,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accesskey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");

        //发送短信
        request.putBodyParameter("PhoneNumbers",phone);
        request.putBodyParameter("SignName","乐优商城");
        request.putBodyParameter("TemplateCode","SMS_192831469");
        request.putBodyParameter("TemplateParam","{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}