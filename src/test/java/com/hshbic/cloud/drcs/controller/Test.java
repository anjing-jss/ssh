package com.hshbic.cloud.drcs.controller;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import ssh.RestClient;
import ssh.RestClientException;
import ssh.RestClientResponse;
import ssh.SimpleJsonRestClient;
import ssh.TaskUtil;

public class Test {

  public static void main(String[] args) {
    RestClient client = new SimpleJsonRestClient();
    String device_url = "https://uws.haier.net/uds/v1/protected/deviceinfos";
    TaskUtil taskUtil = new TaskUtil();
    
     Map<String,String> createMap = new HashMap<String,String>(); 
     Map<String, String> headers=new HashMap<String,String>();
        
        String appId="MB-FRIDGEGENE1-0000";
        String appKey="6cdd4658b8e7dcedf287823b94eb6ff9";
        String timestamp=System.currentTimeMillis()+"";
        String addurl="/uds/v1/protected/deviceinfos";
        
        JSONObject jsonObject=JSONObject.fromObject(createMap);
        String body=jsonObject.toString();
        System.out.println("body:"+body);
        String sign = null;
        try {
            sign = taskUtil.getSign(appId,appKey,timestamp,"",device_url);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        headers.put("appId", "MB-FRIDGEGENE1-0000");
        headers.put("appVersion", "99.99.99.99990");
        headers.put("clientId", "123");
        headers.put("sequenceId", "2014022801010");
        headers.put("accessToken","TGT1ANW5WCQ2SXRD2DGIYRRAVLOMS0" );
        headers.put("timestamp", timestamp);
        headers.put("language", "zh-cn");
        headers.put("timezone", "+8");
        headers.put("appKey", "6cdd4658b8e7dcedf287823b94eb6ff9");
        headers.put("sign", sign);
        headers.put("Content-Encoding","utf-8");
        headers.put("Content-type", "application/json");
        RestClientResponse<Object> result = null;
        try {
            result = client.get(device_url, null, headers, createMap, Object.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("SendDataCallback result : URL "+device_url+",    ----- "+result);
        System.out.println("result:"+result); 
}
//	public static void main(String[] args) {
//		RestClient client = new SimpleJsonRestClient();
//		String device_url = "https://uws.haier.net/uds/v1/protected/deviceinfos";
//		TaskUtil taskUtil = new TaskUtil();
//		 Map<String,String> createMap = new HashMap<String,String>(); 
//		 Map<String, String> headers=new HashMap<String,String>();
//	        
//	        String appId="MB-FRIDGEGENE1-0000";
//	        String appKey="6cdd4658b8e7dcedf287823b94eb6ff9";
//	        String timestamp=System.currentTimeMillis()+"";
//	        String addurl="/uds/v1/protected/deviceinfos";
//	        
//	        JSONObject jsonObject=JSONObject.fromObject(createMap);
//	        String body=jsonObject.toString();
//	        System.out.println("body:"+body);
//	        String sign = null;
//			try {
//				sign = taskUtil.getSign(appId,appKey,timestamp,body,device_url);
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        headers.put("appId", "MB-FRIDGEGENE1-0000");
//	        headers.put("appVersion", "99.99.99.99990");
//	        headers.put("clientId", "123");
//	        headers.put("sequenceId", "2014022801010");
//	        headers.put("accessToken","TGT1ANW5WCQ2SXRD2DGIYRRAVLOMS0" );
//	        headers.put("timestamp", timestamp);
//	        headers.put("language", "zh-cn");
//	        headers.put("timezone", "+8");
//	        headers.put("appKey", "6cdd4658b8e7dcedf287823b94eb6ff9");
//	        headers.put("sign", sign);
//	        headers.put("Content-Encoding","utf-8");
//	        headers.put("Content-type", "application/json");
//	        RestClientResponse<Object> result = null;
//			try {
//				result = client.post(device_url, null, headers, createMap, Object.class);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        System.out.println("SendDataCallback result : URL "+device_url+",    ----- "+result);
//	        System.out.println("result:"+result); 
//	}

}
