package com.ssh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="OP_DEVICE_FAULT_EXPLAN")
public class Devicehistoryexplan extends CreateEntity
  implements Serializable
{
  private static final long serialVersionUID = 6981747417507901715L;

  
  
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  private int id;
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name="DEVICE_TYPE")
  private String deviceType;

  @Column(name="ALARMNAME")
  private String alarmname;

  @Column(name="ALARMEXPLAN")
  private String alarmexplan;

  @Column(name="ALARMALIAS")
  private String alarmalias;

  @Column(name="ALARMLEVEL")
  private Integer alarmlevel;

  public Devicehistoryexplan()
  {
    this.alarmlevel = Integer.valueOf(0);
  }

  public String getAlarmname()
  {
    return this.alarmname;
  }

  public void setAlarmname(String alarmname) {
    this.alarmname = alarmname;
  }

  public String getAlarmexplan() {
    return this.alarmexplan;
  }

  public void setAlarmexplan(String alarmexplan) {
    this.alarmexplan = alarmexplan;
  }

  public String getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getAlarmalias() {
    return this.alarmalias;
  }

  public void setAlarmalias(String alarmalias) {
    this.alarmalias = alarmalias;
  }

  public Integer getAlarmlevel() {
    return this.alarmlevel;
  }

  public void setAlarmlevel(Integer alarmlevel) {
    this.alarmlevel = alarmlevel;
  }
}
