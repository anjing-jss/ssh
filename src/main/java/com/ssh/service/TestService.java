/**
 * Copyright @2017 海尔集团 All rights reserved.
 * 优家智能科技（北京）有限公司 专有 /保密源代码 ,未经许可禁止任何人通过渠道使用、修改源代码 .
 * @create 2017年9月12日 上午11:30:39
 */

package com.ssh.service;

import java.util.List;
import java.util.Map;

import com.ssh.entity.AlarmRule;
import com.ssh.entity.Devicehistoryexplan;

/**
 * @description: 
 * @author jiashanshan
 * @update: 2017年9月12日上午11:30:39
 */
public interface TestService {
  public String test();
  public List<Devicehistoryexplan> findAll();
  public int batchInsert(List<Map<String,Object>> insertList);
  public List<AlarmRule> findAllAlarmRules();
  public int batchInsertAlarmRule(List<Map<String,Object>> insertList);
}
