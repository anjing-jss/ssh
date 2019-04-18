package com.ssh.repository;

import java.util.List;

import com.ssh.entity.Person;

/**
 * Created by XRog
 * On 2/2/2017.2:25 PM
 */
public interface PersonRepository extends DomainRepository<Person,Long> {

  List<Person> findAll();
}