/**
 * Copyright @2017 海尔集团 All rights reserved.
 * 优家智能科技（北京）有限公司 专有 /保密源代码 ,未经许可禁止任何人通过渠道使用、修改源代码 .
 * @create 2017年9月12日 上午11:56:19
 */

package com.ssh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @description: 
 * @author jiashanshan
 * @update: 2017年9月12日上午11:56:19
 */
@Data
@Entity
@Table(name = "Person")
public class Person {
  @Id
  @GeneratedValue
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
  @Column(name = "created")
  private Long created = System.currentTimeMillis();

  @Column(name = "username")
  private String username;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "remark")
  private String remark;
}
