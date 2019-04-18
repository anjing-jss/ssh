/**
 * Copyright @2017 海尔集团 All rights reserved.
 * 优家智能科技（北京）有限公司 专有 /保密源代码 ,未经许可禁止任何人通过渠道使用、修改源代码 .
 * @create 2017年9月12日 下午12:11:50
 */

package com.ssh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.entity.Person;
import com.ssh.repository.PersonRepository;
import com.ssh.service.PersonService;

/**
 * @description: 
 * @author jiashanshan
 * @update: 2017年9月12日下午12:11:50
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Long savePerson() {
        Person person = new Person();
        person.setUsername("XRog");
        person.setPhone("18381005946");
        person.setAddress("chenDu");
        person.setRemark("this is XRog");
        return personRepository.save(person);
    }
    
    public List<Person> findAll(){
      return personRepository.findAll();
    }
}
