package com.ssh.repository;

import java.util.List;
import java.util.Map;

import com.ssh.entity.AlarmRule;
import com.ssh.entity.Devicehistoryexplan;

/**
 * Created by XRog
 * On 2/2/2017.2:25 PM
 */
public interface TestRepository extends DomainRepository<Devicehistoryexplan,Long> {

  List<Devicehistoryexplan> findAll();
  public int batchInsert(List<Map<String,Object>> insertList);
  public List<AlarmRule> findAllAlarmRules();
  public int batchInsertAlarmRule(List<Map<String,Object>> insertList);
}