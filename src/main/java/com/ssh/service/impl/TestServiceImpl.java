/**
 * Copyright @2017 海尔集团 All rights reserved.
 * 优家智能科技（北京）有限公司 专有 /保密源代码 ,未经许可禁止任何人通过渠道使用、修改源代码 .
 * @create 2017年9月12日 上午11:31:29
 */

package com.ssh.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.entity.AlarmRule;
import com.ssh.entity.Devicehistoryexplan;
import com.ssh.repository.DomainRepository;
import com.ssh.repository.PersonRepository;
import com.ssh.repository.TestRepository;
import com.ssh.service.TestService;

/**
 * @description: 
 * @author jiashanshan
 * @update: 2017年9月12日上午11:31:29
 */
@Transactional
@Service
public class TestServiceImpl implements TestService{
  @Autowired
  private TestRepository testRepository;
  public String test() {
    return "springtest";
  }
  public List<Devicehistoryexplan> findAll(){
    return testRepository.findAll();
  }
  public List<AlarmRule> findAllAlarmRules(){
    return testRepository.findAllAlarmRules();
  }
  public int batchInsert(List<Map<String,Object>> insertList){
    int result=testRepository.batchInsert(insertList);
    return result;
  }
  public int batchInsertAlarmRule(List<Map<String,Object>> insertList){
    int result=testRepository.batchInsertAlarmRule(insertList);
    return result;
  }
}
