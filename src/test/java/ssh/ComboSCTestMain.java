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
public class ComboSCTestMain {     
    private String url = "http://10.159.32.192:8301/common/devices/MOCKDEV_JSS-MODEL/statuses/online";  
    private String charset = "utf-8";  
    private RestClient                                 restClient = null;  
      
    public ComboSCTestMain(){  
      restClient=new SimpleJsonRestClient();  
    }  
    private static Logger log = LoggerFactory.getLogger(ComboSCTestMain.class);
     
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



//    public void testPost() throws RestClientException, MalformedURLException{  
//        String httpOrgCreateTest = url ;  
//        Map<String,String> createMap = new HashMap<String,String>(); 
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
//        
////        createMap.put("key","*****"); 
//        Map<String, String> header=new HashMap<String,String>();
//        
//        String appId="MB-UZHSH-0000";
//        String appKey="6bda204e2fa884c175fde09f185ec790";
//        String timestamp=System.currentTimeMillis()+"";
//        String addurl="/ssh/devices/DC330DA31FD7/op/18c55e00763b4bc9b784926c74467395/result";
//        
//        JSONObject jsonObject=JSONObject.fromObject(createMap);
//        String body=jsonObject.toString();
//        String sign=getSign(appId,appKey,timestamp,body,url+addurl);
//        header.put("appId", appId);
//        header.put("appKey", appKey);
//        header.put("timestamp", timestamp); 
//        header.put("clientId", "clientId"); 
//        header.put("sign", sign);
//        header.put("accessToken", "TGT34EGXEB337FUM2AL3WP1OM1ZAZ0");
//        header.put("Content-type", "text/html;charset=UTF-8");
////        header.put("Content-type", "application/json; charset=UTF-8");
////        ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, createMap, Object.class);
//        ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, createMap, Object.class);
//        System.out.println("SendDataCallback result : URL "+url+",    ----- "+result);
//        System.out.println("result:"+result);  
//    }  
  public void testPost() throws RestClientException, MalformedURLException{  
    String httpOrgCreateTest = url ;  
    Map<String,String> createMap = new HashMap<String,String>(); 
//    createMap.put("timestamp","1515983695486");  
//    createMap.put("sn","18c55e00763b4bc9b784926c74467395");  
//    createMap.put("issuerSystem",null);  
//    createMap.put("issuerType",null);  
//    createMap.put("deviceId","DC330DA31FD7");  
//    createMap.put("targetSystem","SV-UWSE-0000");  
//    createMap.put("targetType",null);  
//    createMap.put("targetId","SV-DRWXAIRKISS-0000");  
//    createMap.put("category","StdDevAttrW"); 
//    createMap.put("name","time"); 
//    createMap.put("args","{}"); 
//    
//    createMap.put("resCode","00000"); 
    
//    createMap.put("key","*****"); 
    Map<String, String> header=new HashMap<String,String>();
    
//    String appId="SV-SMARTROUTER1-0000";
//    String appKey="ef247a82a109294fc4fb751c7aa4890f";
//    String timestamp=System.currentTimeMillis()+"";
//    String addurl="/udse/v1/devBindUsers";
    JSONObject jsonObject=JSONObject.fromObject(createMap);
    String body=jsonObject.toString();
//    String sign=getSign(appId,appKey,timestamp,body,url+addurl);
//    header.put("appId", appId);
//    header.put("appKey", appKey);
//    header.put("appVersion", "appVersion");
//    header.put("timestamp", timestamp); 
//    header.put("clientId", "110001"); 
//    header.put("sign", sign);
//    header.put("accessToken", "TGT34EGXEB337FUM2AL3WP1OM1ZAZ0");
//    header.put("Content-type", "text/html;charset=UTF-8");
//    header.put("Content-type", "application/json; charset=UTF-8");
//    ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, createMap, Object.class);
    RestClientResponse<T> result = restClient.get(url, null, header, createMap, Object.class);
    System.out.println("SendDataCallback result : URL "+url+",    ----- "+result.getBody());
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
        ComboSCTestMain main = new ComboSCTestMain();  
        main.testPost();  
    }  
}  