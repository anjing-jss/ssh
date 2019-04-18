/**
 * Copyright @2017 海尔集团 All rights reserved.
 * 优家智能科技（北京）有限公司 专有 /保密源代码 ,未经许可禁止任何人通过渠道使用、修改源代码 .
 * @create 2017年9月12日 上午11:15:06
 */

package com.ssh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssh.entity.AlarmRule;
import com.ssh.entity.Devicehistoryexplan;
import com.ssh.entity.Person;
import com.ssh.entity.RestResponse;
import com.ssh.service.PersonService;
import com.ssh.service.TestService;
import com.ssh.utils.POIUtil;

/**
 * @description: 
 * @author jiashanshan
 * @update: 2017年9月12日上午11:15:06
 */
@Controller
public class MainController {
//  @Autowired
//  private PersonService personService;
  @Autowired
  private TestService testService;
  private static Logger logger  = LoggerFactory.getLogger(MainController.class);  
  
  /**
   * 获取操作响应回调
   *
   * @param deviceId
   * @param usn
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/devices/{deviceId}/op/{usn}/result", method = RequestMethod.POST)
  public @ResponseBody RestResponse callbackDeviceResult(@PathVariable("deviceId") String deviceId,
                                           @PathVariable("usn") String usn,
                                           HttpServletRequest request) {
//      PrintWriter out = null;
      BufferedReader bufferedReader = null;
      RestResponse respBack = new RestResponse();
      try {
          
          StringBuffer stringBuffer = new StringBuffer();
          InputStream inputStream = request.getInputStream();
          if (inputStream != null) {
              bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
              char[] charBuffer = new char[128];
              int bytesRead = -1;
              while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                  stringBuffer.append(charBuffer, 0, bytesRead);
              }
          } else {
              stringBuffer.append("");
          }
          String requestBody = stringBuffer.toString();
          logger.info("deviceId={"+deviceId+"},usn={"+usn+"},callbackResult={"+requestBody+"}");
          // 记录DI日志
          
          respBack.setRetCode("00000");
          respBack.setRetInfo("ErrorCode.INFO_00000");
//          out = response.getWriter();
//          out.println(JSONObject.fromObject(respBack).toString());  
//          out.close();
//          response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8

          String data = JSONObject.fromObject(respBack).toString();

//          OutputStream stream = response.getOutputStream();
//
//          stream.write(data.getBytes("UTF-8"));
//          PrintWriter writer = response.getWriter();

//          writer.write(data);
          return respBack;

      } catch (Exception e) {
          respBack.setRetCode("UwsException.CODE_A00001");
          respBack.setRetInfo("UwsException.INFO_A00001");
          logger.error("callback error", e);
          return respBack;
      }
  }
  
  @RequestMapping(value = "savePerson", method = RequestMethod.GET)
  @ResponseBody
  public String savePerson(){
//      personService.savePerson();
      return "success!";
  }
  
  @RequestMapping(value = "test/{sn:.+}/jss/{test:.+}", method = RequestMethod.GET)
  public String test(@PathVariable("sn") String sn,@PathVariable("test") String test){
    System.out.println("sn："+sn);
    System.out.println("test："+test);
//    testService.findAll();
//      实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
      return "test";
  }
  @RequestMapping(value = "testPage", method = RequestMethod.GET)
  public String testPage(){
    logger.info("看下成功了吗");
    System.out.println("sn：");
    System.out.println("test：");
//    testService.findAll();
//      实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
      return "test";
  }
  @RequestMapping(value = "/upload",method = RequestMethod.POST)  
  public void readExcel(@RequestParam(value = "excelFile") MultipartFile excelFile,HttpServletRequest req,HttpServletResponse resp){  
      Map<String, Integer> param = new HashMap<String, Integer>();  
      List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
      List<String[]> saveList = POIUtil.readExcelToObj(excelFile); 
      SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      String date=s.format(new Date());
      int sum=0;
      int count=0;
      Set<Map<String,Object>> set = new  HashSet<Map<String,Object>>(); 
      Map<String,Integer> errorMap=new HashMap<String,Integer>();
      errorMap.put("0", 0);
      errorMap.put("1", 0);
      errorMap.put("2", 0);
      errorMap.put("3", 0);
      Map<String,Object> beforeInsertMap = new HashMap<String,Object>(); 
      for(int i = 0;i<saveList.size();i++){ 
        Map<String,Object> user = new HashMap<String,Object>();  
        String[] data = saveList.get(i); 
        count+=data.length-3;
        if(data[1]==null||"".equals(data[1])){
          errorMap.put("0", errorMap.get("0")+data.length-3);
          continue;
        }
        if((!"警告".equals(data[1].trim()))&&(!"警报".equals(data[1].trim()))){
          errorMap.put("1", errorMap.get("1")+data.length-3);
          continue;
        }
        sum+=data.length-3;
        user.put("ALARMEXPLAN",data[0]);  
        user.put("ALARMALIAS",data[0]); 
        if("警告".equals(data[1].trim())){
          user.put("ALARMLEVEL",20);  
        }else if("警报".equals(data[1].trim())){
          user.put("ALARMLEVEL",30);  
        }
        user.put("ALARMNAME",data[2]);
        user.put("CREATOR","operationadmin");  
        user.put("CREATE_TIME",date);
        Set<String> typeSet=new HashSet<String>();
        for(int j=3;j<data.length-1;j++){
          if(typeSet.add(data[j])){
            user.put("DEVICE_TYPE",data[j]);
          }else{
            if(param.get(data[j])==null||"".equals(param.get(data[j]))){
              param.put(data[j], 1);
            }else{
              param.put(data[j], param.get(data[j])+1);
            }
            errorMap.put("2", errorMap.get("2")+1);
            continue;
          }
          
          if(set.add(user)){
            Map<String,Object> faultExplan = new HashMap<String,Object>(); 
            faultExplan.putAll(user);
            int result=j-2;
            faultExplan.put("RULENAME",data[data.length-1]+result);
            beforeInsertMap.put(data[2]+"#"+user.get("DEVICE_TYPE"),faultExplan);
          }else{
            errorMap.put("3", errorMap.get("3")+1);
            System.out.println("重复数据详细为："+data[0]+data[1]+data[2]+data[j]);
          }
          user.remove("RULENAME");
        }
        
       }  
      System.out.println("有效总长度："+count+",不能判断的"+errorMap.get("0")+",不是警告的"+errorMap.get("1")+"剩余："+sum+",去重之后的长度"+list.size()+"，type重复次数"+errorMap.get("2")+",详细数据重复的"+errorMap.get("3") );
      int repeatCount=0;
      for(String key:param.keySet()){
        System.out.println("type："+key+"重复"+param.get(key));
        repeatCount+=param.get(key);
        System.out.println("重复数据个数为："+repeatCount);
      }
      List<Devicehistoryexplan>  devicehistoryexplanList=testService.findAll();
//      int id=devicehistoryexplanList.get(devicehistoryexplanList.size()-1).getId();
      Map<String,Object> devicehistoryexplanAllMap=new HashMap<String,Object>();
      for(int i=0;i<devicehistoryexplanList.size();i++){
        Devicehistoryexplan dhp=devicehistoryexplanList.get(i);
        devicehistoryexplanAllMap.put(dhp.getAlarmname()+"#"+dhp.getDeviceType(), dhp);
      }
      
      List<AlarmRule>  alarmRuleList=testService.findAllAlarmRules();
      Map<String,Object> alarmRuleAllMap=new HashMap<String,Object>();
      for(int i=0;i<alarmRuleList.size();i++){
        AlarmRule dhp=alarmRuleList.get(i);
        alarmRuleAllMap.put(dhp.getAlarmlevel()+"#"+dhp.getDeviceType(), dhp);
      }
      
      List<Map<String,Object>> insertlist=new ArrayList<Map<String,Object>>();
      int keySum=0;
      Map<String,Object> beforeInsertAlarmRuleMap = new HashMap<String,Object>(); 
      for(String key:beforeInsertMap.keySet()){
        if(devicehistoryexplanAllMap.get(key)==null){
          Map<String,Object> insertMap=(Map<String, Object>) beforeInsertMap.get(key);
//          if(insertMap.get("ALARMEXPLAN").toString().trim().equals("烘干风机系统异常")){
//            System.out.println("---"+insertMap.get("ALARMEXPLAN")+"==="+key+"*****"+insertMap);
//          }
          Map<String,Object> insertRuleMap=new HashMap<String, Object>();
          insertRuleMap.put("RULENAME", insertMap.get("RULENAME"));
          insertRuleMap.put("DEVICE_TYPE", insertMap.get("DEVICE_TYPE"));
          insertRuleMap.put("ALARMLEVEL", insertMap.get("ALARMLEVEL"));
          insertRuleMap.put("FLAG", 0);
          insertRuleMap.put("CREATOR", insertMap.get("CREATOR"));
          insertRuleMap.put("CREATE_TIME", insertMap.get("CREATE_TIME"));
          insertlist.add(insertMap);
//          insertAlarmRulelist.add(insertRuleMap);
          beforeInsertAlarmRuleMap.put(insertRuleMap.get("ALARMLEVEL")+"#"+insertRuleMap.get("DEVICE_TYPE"), insertRuleMap);
        }else{
          keySum++;
        }
      }
//      List<Map<String,Object>> insertlist1=new ArrayList<Map<String,Object>>();
//      insertlist1.add(insertlist.get(0));
      String start=s.format(new Date());
      int insertResult=testService.batchInsert(insertlist);
      String end=s.format(new Date());
      System.out.println("--------------------------------------------"+end+","+start+","+keySum);
      System.out.println(insertResult);
      List<Map<String,Object>> insertAlarmRulelist=new ArrayList<Map<String,Object>>();
      for(String key:beforeInsertAlarmRuleMap.keySet()){
        if(alarmRuleAllMap.get(key)==null){
          Map<String,Object> insertMap=(Map<String, Object>) beforeInsertAlarmRuleMap.get(key);
          insertAlarmRulelist.add(insertMap);
        }
      }
      int insertAlarmRuleResult=testService.batchInsertAlarmRule(insertAlarmRulelist);
      System.out.println("----------------------"+insertAlarmRuleResult);
  }
}
