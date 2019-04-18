package ssh;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;  
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//对接口进行测试  
public class UdsTestMain {     
//    private String url = "https://uws.haier.net";  
  private String url = "http://10.159.59.7:6260";  
    private String charset = "utf-8";  
    private RestClient                                 restClient = null;  
      
    public UdsTestMain(){  
      restClient=new SimpleJsonRestClient();  
    }  
    private static Logger log = LoggerFactory.getLogger(UdsTestMain.class);
     
    String getSign(String appId, String appKey, String timestamp, String body,String url) throws MalformedURLException{
      URL urlObj = new URL(url);
      url=urlObj.getPath();
      appKey = appKey.trim();
      appKey = appKey.replaceAll("\"", "");
      if (body != null) {
          body = body.trim();
      }
      if (!body.equals("")) {
          body = body.replaceAll(" ", "");
          body = body.replaceAll("\t", "");
          body = body.replaceAll("\r", "");
          body = body.replaceAll("\n", "");
      }
      log.info("body:"+body);
      StringBuffer sb = new StringBuffer();
      sb.append(url).append(body).append(appId).append(appKey).append(timestamp);

      MessageDigest md = null;
      byte[] bytes = null;
      try {
          md = MessageDigest.getInstance("SHA-256");
          bytes = md.digest(sb.toString().getBytes("utf-8"));
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      return BinaryToHexString(bytes);
  }

  String BinaryToHexString(byte[] bytes) {
      StringBuilder hex = new StringBuilder();
      String hexStr = "0123456789abcdef";
      for (int i = 0; i < bytes.length; i++) {        
          hex.append(String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4)));      
          hex.append(String.valueOf(hexStr.charAt(bytes[i] & 0x0F)));
      }
      return hex.toString();
  }



    public void testPost() throws RestClientException, MalformedURLException{  
        String httpOrgCreateTest = url ;  
        Map<String,String> createMap = new HashMap<String,String>(); 
//        createMap.put("timestamp","1515983695486");  
//        createMap.put("sn","18c55e00763b4bc9b784926c74467395");  
//        createMap.put("issuerSystem",null);  
//        createMap.put("issuerType",null);  
//        createMap.put("issuerId","DC330DA31FD7");  
//        createMap.put("targetSystem","SV-UWSE-0000");  
//        createMap.put("targetType",null);  
//        createMap.put("targetId","SV-DRWXAIRKISS-0000");  
//        createMap.put("category","StdDevAttrW"); 
//        createMap.put("name","time"); 
//        createMap.put("args","{}"); 
//        
//        createMap.put("resCode","00000"); 
        
//        createMap.put("key","*****"); 
        Map<String, String> headers=new HashMap<String,String>();
        
        String appId="MB-FRIDGEGENE1-0000";
        String appKey="6cdd4658b8e7dcedf287823b94eb6ff9";
        String timestamp=System.currentTimeMillis()+"";
        String addurl="/uds/v1/protected/deviceinfos";
        
        JSONObject jsonObject=JSONObject.fromObject(createMap);
        String body=jsonObject.toString();
        System.out.println("body:"+body);
        String sign=getSign(appId,appKey,timestamp,"",url+addurl);
//        header.put("appId", appId);
//        header.put("appKey", appKey);
//        header.put("timestamp", timestamp); 
//        header.put("clientId", "clientId"); 
//        header.put("sign", sign);
//        header.put("accessToken", "TGT34EGXEB337FUM2AL3WP1OM1ZAZ0");
//        header.put("Content-type", "text/html;charset=UTF-8");
        
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

//        restClient.get(url+addurl, null, headers, createMap, Object.class);
        
//        header.put("Content-type", "application/json; charset=UTF-8");
//        ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, createMap, Object.class);
        ssh.RestClientResponse<Object> result = restClient.get(url+addurl, null, headers, createMap, Object.class);
        System.out.println("SendDataCallback result : URL "+url+",    ----- "+result);
        System.out.println("result:"+result);  
    }  
      
    public void testGet(){  
      String httpOrgCreateTest = url ;  
      Map<String,String> createMap = new HashMap<String,String>();  
      createMap.put("authuser","*****");  
      createMap.put("authpass","*****");  
      createMap.put("orgkey","****");  
      createMap.put("orgname","****");  
//      String httpOrgCreateTestRtn = restClient.doGet(httpOrgCreateTest,charset);  
//      System.out.println("result:"+httpOrgCreateTestRtn);  
    }  
    public static void main(String[] args) throws RestClientException, MalformedURLException{  
        UdsTestMain main = new UdsTestMain();  
        main.testPost();  
    }  
}  