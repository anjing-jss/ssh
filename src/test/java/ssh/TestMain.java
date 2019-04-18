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

import antlr.collections.List;

import com.hshbic.haigeek.modelparser.convert.ViewSendValConverter;
import com.hshbic.haigeek.modelparser.exception.JsonFileException;
import com.hshbic.haigeek.modelparser.model.Property;
//对接口进行测试  
public class TestMain {     
    private String url = "https://10.159.59.11:6263";  
    private String charset = "utf-8";  
    private RestClient                                 restClient = null;  
      
    public TestMain(){  
      restClient=new SimpleJsonRestClient();  
    }  
    private static Logger log = LoggerFactory.getLogger(TestMain.class);
     
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



    public void testPost() throws RestClientException, MalformedURLException, JsonFileException{  
        String httpOrgCreateTest = url ;  
        Map<String,String> createMap = new HashMap<String,String>(); 
         
//        createMap.put("deviceId","D0B7C05344FCD081002592183D5E1");  
//        createMap.put("name","04FA83069A26");  
//        createMap.put("data","70c9bce7ce25cd7ce1151367946206cc"); 
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
        Map<String, String> header=new HashMap<String,String>();
//        [/uds/v1/protected/bindDevice
//         {"data":"a30bec2b73e823dd71f16fd5c95e4f46363ed4708e9cc063c2b3df407fd5d5b3","deviceId":"FA35CBA6A199D123","name":"设备_D123"}
//        MB-FRIDGEGENE1-0000cb28f3e8a8296dde100ada7fd95906e41529979347073]
        String appId="MB-UZHSH-0000";
        String appKey="f50c76fbc8271d361e1f6b5973f54585";
        String timestamp="1534748392495";
//        String addurl="/ssh/devices/DC330DA31FD7/op/18c55e00763b4bc9b784926c74467395/result";
        String addurl="/uds/v1/protected/D0B7C05344FCD081002592183D5E1/deviceNetQuality";
        JSONObject jsonObject=JSONObject.fromObject(createMap);
        String body=jsonObject.toString();
//        body="{\"deviceId\":\"04FA83069A26\",\"name\":\"04FA83069A26\",\"data\":\"70c9bce7ce25cd7ce1151367946206cc\"}";
        String sign=getSign(appId,appKey,timestamp,"",url+addurl);
        System.out.println("sign:"+sign);
        header.put("appId", appId);
        header.put("appKey", appKey);
        header.put("timestamp", timestamp); 
        header.put("clientId", "2014022801010"); 
        header.put("sign", sign);
        header.put("accessToken", "TGT47G8Z0ADJLCX2BCZ5XNLYP7H100");
        header.put("Content-type", "text/html;charset=UTF-8");
//        header.put("Content-type", "application/json; charset=UTF-8");
//        ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, body, Object.class);
//        ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, body, Object.class);
//        ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, createMap, Object.class);
//        url="http://resource.haigeek.com:80/download/resource/selfService/hardware/funcmodelfile/201c12002400081005010021800067414800bc00000000000000000000000040_20180104015831557.json";
        RestClientResponse<Object> result=restClient.get(url+addurl, null, header, null, Object.class);
//        ssh.RestClientResponse<Object> result1 = restClient.post(url, null, new HashMap<String,String>(), new HashMap<String,String>(), Object.class);
       
        System.out.println(result+"----- "+result.getBody());
//        String typeId="201c12002400081005010021800067414800bc00000000000000000000000040";
//        ViewSendValConverter.loadJsonContent(typeId,(String) result1.getBody());
//        Property ppp=ViewSendValConverter.getViewValue(typeId, "laundryCycle", "1");
//        long end=System.currentTimeMillis();
//        System.out.println("result:"+result1);  
    }  
//  public void testPost() throws RestClientException, MalformedURLException{  
//    String httpOrgCreateTest = url ;  
//    Map<String,String> createMap = new HashMap<String,String>(); 
////    createMap.put("timestamp","1528958076467");  
////    createMap.put("sn","18c55e00763b4bc9b784926c74467395");  
////    createMap.put("issuerSystem",null);  
////    createMap.put("issuerType",null);  
////    createMap.put("deviceId","DC330DA31FD7");  
////    createMap.put("targetSystem","SV-UWSE-0000");  
////    createMap.put("targetType",null);  
////    createMap.put("targetId","SV-DRWXAIRKISS-0000");  
////    createMap.put("category","StdDevAttrW"); 
////    createMap.put("name","time"); 
////    createMap.put("args","{}"); 
////    
////    createMap.put("resCode","00000"); 
//    
////    createMap.put("key","*****"); 
//    Map<String, String> header=new HashMap<String,String>();
//    
//    String appId="MB-UZHSH-0000";
//    String appKey="6bda204e2fa884c175fde09f185ec790";
//    String timestamp="1528958076467";
//    String addurl="/uds/v1/protected/deviceinfos";
//    JSONObject jsonObject=JSONObject.fromObject(createMap);
//    String body=jsonObject.toString();
////    createMap.put("body",body);  
//    String sign=getSign(appId,appKey,timestamp,"",url+addurl);
//    header.put("appId", appId);
//    header.put("appKey", appKey);
//    header.put("appVersion", "appVersion");
//    header.put("timestamp", timestamp); 
//    header.put("clientId", "123456"); 
//    header.put("body",body);  
//    System.out.println(sign);
//    header.put("sign", sign);
//    header.put("accessToken", "TGT263PO154W49XO2NXQTE4TR15D10");
//    header.put("Content-type", "text/html;charset=UTF-8");
////    header.put("Content-type", "application/json; charset=UTF-8");
////    ssh.RestClientResponse<Object> result = restClient.post(url+addurl, null, header, createMap, Object.class);
//    ssh.RestClientResponse<Object> result = restClient.get(url+addurl, null, header, createMap, Object.class);
//    System.out.println("SendDataCallback result : URL "+url+",    ----- "+result);
//    System.out.println("result:"+result);  
//} 
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
    public static void main(String[] args) throws RestClientException, MalformedURLException, JsonFileException{  
        TestMain main = new TestMain();  
        main.testPost();  
    }  
}  