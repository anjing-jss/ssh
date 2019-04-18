/**
 * Copyright @2017 海尔集团 All rights reserved.
 * 优家智能科技（北京）有限公司 专有 /保密源代码 ,未经许可禁止任何人通过渠道使用、修改源代码 .
 * @create 2017年9月12日 下午12:10:58
 */

package com.ssh.service;

import java.util.List;

import com.ssh.entity.Person;

/**
 * @description: 
 * @author jiashanshan
 * @update: 2017年9月12日下午12:10:58
 */
public interface PersonService {
  Long savePerson();
  List<Person> findAll();
}
