package com.ssh.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="OP_DEVICE_ALARM_RULE")
public class AlarmRule extends CreateEntity
  implements Serializable
{
  private static final long serialVersionUID = 8689567925801082260L;

  
  
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  private int id;
  
  @Column(name="DEVICE_TYPE")
  private String deviceType;

  @Column(name="ALARMLEVEL")
  private int alarmlevel;

  @Column(name="FLAG")
  private int flag;

  @Transient
  private List<String> deviceTypes;

  @Transient
  private String alarmname;

  public AlarmRule()
  {
    this.alarmlevel = 0;
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name="RULENAME")
  private String rulename;

  public String getRulename() {
    return rulename;
  }

  public void setRulename(String rulename) {
    this.rulename = rulename;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public int getAlarmlevel() {
    return alarmlevel;
  }

  public void setAlarmlevel(int alarmlevel) {
    this.alarmlevel = alarmlevel;
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public List<String> getDeviceTypes() {
    return deviceTypes;
  }

  public void setDeviceTypes(List<String> deviceTypes) {
    this.deviceTypes = deviceTypes;
  }

  public String getAlarmname() {
    return alarmname;
  }

  public void setAlarmname(String alarmname) {
    this.alarmname = alarmname;
  }



}
