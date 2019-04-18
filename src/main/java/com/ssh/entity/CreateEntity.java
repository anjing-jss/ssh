package com.ssh.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CreateEntity
  implements Serializable
{
  private static final long serialVersionUID = 4058911042167442508L;

  @Column(name="CREATOR")
  private String creator;

  @Column(name="CREATE_TIME")
  private Timestamp createTime;

  public String getCreator()
  {
    return this.creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Timestamp getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
}
