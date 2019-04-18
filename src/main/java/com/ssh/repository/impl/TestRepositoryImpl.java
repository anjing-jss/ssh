package com.ssh.repository.impl;

import com.ssh.repository.PersonRepository;
import com.ssh.repository.TestRepository;
import com.ssh.entity.AlarmRule;
import com.ssh.entity.Devicehistoryexplan;
import com.ssh.entity.Person;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by XRog
 * On 2/2/2017.2:30 PM
 */
@Repository
public class TestRepositoryImpl implements TestRepository {
  
    @Autowired  
    private JdbcTemplate jdbcTemplate; 
    
    @Resource  
    private DataSource dataSource; 

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private PersonRepository personRepository;

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    public Devicehistoryexplan load(Long id) {
        return (Devicehistoryexplan)getCurrentSession().load(Person.class,id);
    }

    public Devicehistoryexplan get(Long id) {
        return (Devicehistoryexplan)getCurrentSession().get(Devicehistoryexplan.class,id);
    }

    public List<Devicehistoryexplan> findAll() {
      List<Person> p=personRepository.findAll();
      String hql = "from Devicehistoryexplan u order by u.id asc";
      Query query=getCurrentSession().createQuery(hql);
      List<Devicehistoryexplan> list =query.list();
      return list;
    }
    public List<AlarmRule> findAllAlarmRules(){
      String hql = "from AlarmRule u order by u.id asc";
      Query query=getCurrentSession().createQuery(hql);
      List<AlarmRule> list =query.list();
      return list;
    }
    @Transactional
    public int batchInsert(List<Map<String,Object>> insertList){
//      final int maxId=jdbcTemplate.queryForObject("select IFNULL(max(id),2636) from op_device_fault_explan", java.lang.Integer.class);
      int maxId=2636;
      final List<Map<String,Object>> list=insertList;
//      //批量操作    适合于增、删、改操作  
//        int[] updateCounts = jdbcTemplate.batchUpdate(  
////                "insert into OP_DEVICE_FAULT_EXPLAN (id,DEVICE_TYPE,ALARMNAME,ALARMEXPLAN,ALARMALIAS,ALARMLEVEL,CREATOR,CREATE_TIME) values(?,?,?,?,?,?,?,?)",  
//            "insert into OP_DEVICE_FAULT_EXPLAN (DEVICE_TYPE,ALARMNAME,ALARMEXPLAN,ALARMALIAS,ALARMLEVEL,CREATOR,CREATE_TIME) values(?,?,?,?,?,?,?)",  
//                new BatchPreparedStatementSetter() {  
//                      @Override  
//                      public void setValues(PreparedStatement ps, int i)   
//                          throws SQLException {   
//                            int id=maxId+i+1;
//                            String deviceType=(String) list.get(i).get("DEVICE_TYPE");
//                            String ALARMNAME=(String) list.get(i).get("ALARMNAME");
//                            String ALARMEXPLAN=(String) list.get(i).get("ALARMEXPLAN");
//                            String ALARMALIAS=(String) list.get(i).get("ALARMALIAS");
//                            Integer ALARMLEVEL=(Integer) list.get(i).get("ALARMLEVEL");
//                            String CREATOR=(String) list.get(i).get("CREATOR");
//                            String CREATE_TIME=(String) list.get(i).get("CREATE_TIME");
//                            ps.setString(1,deviceType );  
//                            ps.setString(2, ALARMNAME);  
//                            ps.setString(3, ALARMEXPLAN); 
//                            ps.setString(4, ALARMALIAS); 
//                            ps.setInt(5, ALARMLEVEL); 
//                            ps.setString(6, CREATOR); 
//                            ps.setString(7, CREATE_TIME); 
////                            System.out.println((i+1)+",id,"+id+",deviceType,"+deviceType+"ALARMNAME,"+ALARMNAME+"ALARMEXPLAN,"+ALARMEXPLAN
////                                +"ALARMALIAS,"+ALARMALIAS+"ALARMLEVEL,"+ALARMLEVEL);
//                        }  
//                          
//                        @Override  
//                        public int getBatchSize() {  
//                            return list.size();  
//                        }  
//                }   
//        );  
//          
//        return updateCounts.length; 

        int row = 0;
        PreparedStatement pstmt=null;
        Connection con=null;
        try{
          con = dataSource.getConnection();
          con.setAutoCommit(false);//将自动提交关闭
          String sql = "insert into OP_DEVICE_FAULT_EXPLAN (DEVICE_TYPE,ALARMNAME,ALARMEXPLAN,ALARMALIAS,ALARMLEVEL,CREATOR,CREATE_TIME) values(?,?,?,?,?,?,?)";
          pstmt = (PreparedStatement) con.prepareStatement(sql);
          Random random = new Random();
          for(int i = 0; i < insertList.size(); i++){
            int id=maxId+i+1;
            String deviceType=(String) list.get(i).get("DEVICE_TYPE");
            String ALARMNAME=(String) list.get(i).get("ALARMNAME");
            String ALARMEXPLAN=(String) list.get(i).get("ALARMEXPLAN");
            String ALARMALIAS=(String) list.get(i).get("ALARMALIAS");
            Integer ALARMLEVEL=(Integer) list.get(i).get("ALARMLEVEL");
            String CREATOR=(String) list.get(i).get("CREATOR");
            String CREATE_TIME=(String) list.get(i).get("CREATE_TIME");
            pstmt.setString(1,deviceType );  
            pstmt.setString(2, ALARMNAME);  
            pstmt.setString(3, ALARMEXPLAN); 
            pstmt.setString(4, ALARMALIAS); 
            pstmt.setInt(5, ALARMLEVEL); 
            pstmt.setString(6, CREATOR); 
            pstmt.setString(7, CREATE_TIME); 
            pstmt.addBatch();
            if(i>0 && i%500==0){
              int[] sount=pstmt.executeBatch();
              row+=sount.length;
              //如果不想出错后，完全没保留数据，则可以没执行一次提交一次，但得保证数据不会重复
//              con.commit();
    
            }
          }
          int[] sount=pstmt.executeBatch();//执行最后剩下不够500条的
          row+=sount.length;
          pstmt.close();
          con.commit();//执行完后，手动提交事务
          con.setAutoCommit(true);//在把自动提交打开
          con.close();
        }catch(Exception e){
          e.printStackTrace();
        }finally{
          try {
            pstmt.close();
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          try {
            con.close();
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        return row;
    }
    
    public int batchInsertAlarmRule(List<Map<String,Object>> insertList){
//      final int maxId=jdbcTemplate.queryForObject("select IFNULL(max(id),135) from op_device_alarm_rule", java.lang.Integer.class);
      int maxId=135;
      final List<Map<String,Object>> list=insertList;
      //批量操作    适合于增、删、改操作  
//        int[] updateCounts = jdbcTemplate.batchUpdate(  
////                "insert into OP_DEVICE_FAULT_EXPLAN (id,DEVICE_TYPE,ALARMNAME,ALARMEXPLAN,ALARMALIAS,ALARMLEVEL,CREATOR,CREATE_TIME) values(?,?,?,?,?,?,?,?)",  
//            "insert into op_device_alarm_rule (RULENAME,DEVICE_TYPE,ALARMLEVEL,FLAG,CREATOR,CREATE_TIME) values(?,?,?,?,?,?)",  
//                new BatchPreparedStatementSetter() {  
//                      @Override  
//                      public void setValues(PreparedStatement ps, int i)   
//                          throws SQLException {   
//                            int id=maxId+i+1;
//                            String RULENAME=(String) list.get(i).get("RULENAME");
//                            String DEVICE_TYPE=(String) list.get(i).get("DEVICE_TYPE");
//                            int ALARMLEVEL=(Integer) list.get(i).get("ALARMLEVEL");
//                            int FLAG=(Integer) list.get(i).get("FLAG");
//                            String CREATOR=(String) list.get(i).get("CREATOR");
//                            String CREATE_TIME=(String) list.get(i).get("CREATE_TIME");
//                            ps.setString(1,RULENAME );  
//                            ps.setString(2, DEVICE_TYPE);  
//                            ps.setInt(3, ALARMLEVEL); 
//                            ps.setInt(4, FLAG); 
//                            ps.setString(5, CREATOR); 
//                            ps.setString(6, CREATE_TIME); 
//                            System.out.println((i+1)+",id,"+id+",RULENAME,"+RULENAME+"DEVICE_TYPE,"+DEVICE_TYPE+"ALARMLEVEL,"+ALARMLEVEL
//                                +"FLAG,"+FLAG);
//                        }  
//                          
//                        @Override  
//                        public int getBatchSize() {  
//                            return list.size();  
//                        }  
//                }   
//        );  
      int row = 0;
      PreparedStatement pstmt=null;
      Connection con=null;
      try{
        con = dataSource.getConnection();
        con.setAutoCommit(false);//将自动提交关闭
        String sql = "insert into op_device_alarm_rule (RULENAME,DEVICE_TYPE,ALARMLEVEL,FLAG,CREATOR,CREATE_TIME) values(?,?,?,?,?,?)";
        pstmt = (PreparedStatement) con.prepareStatement(sql);
        Random random = new Random();
        for(int i = 0; i < insertList.size(); i++){
          int id=maxId+i+1;
          String RULENAME=(String) list.get(i).get("RULENAME");
          String DEVICE_TYPE=(String) list.get(i).get("DEVICE_TYPE");
          int ALARMLEVEL=(Integer) list.get(i).get("ALARMLEVEL");
          int FLAG=(Integer) list.get(i).get("FLAG");
          String CREATOR=(String) list.get(i).get("CREATOR");
          String CREATE_TIME=(String) list.get(i).get("CREATE_TIME");
          pstmt.setString(1,RULENAME );  
          pstmt.setString(2, DEVICE_TYPE);  
          pstmt.setInt(3, ALARMLEVEL); 
          pstmt.setInt(4, FLAG); 
          pstmt.setString(5, CREATOR); 
          pstmt.setString(6, CREATE_TIME); 
          pstmt.addBatch();
          if(i>0 && i%500==0){
            int[] sount=pstmt.executeBatch();
            row+=sount.length;
            //如果不想出错后，完全没保留数据，则可以没执行一次提交一次，但得保证数据不会重复
//            con.commit();
  
          }
        }
        int[] sount=pstmt.executeBatch();//执行最后剩下不够500条的
        row+=sount.length;
        pstmt.close();
        con.commit();//执行完后，手动提交事务
        con.setAutoCommit(true);//在把自动提交打开
        con.close();
      }catch(Exception e){
        e.printStackTrace();
      }finally{
        try {
          pstmt.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        try {
          con.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return row;
          
//        return updateCounts.length;  
    }
    
    public void persist(Devicehistoryexplan entity) {
        getCurrentSession().persist(entity);
    }

    public Long save(Devicehistoryexplan entity) {
        return (Long)getCurrentSession().save(entity);
    }

    public void saveOrUpdate(Devicehistoryexplan entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    public void delete(Long id) {
      Devicehistoryexplan person = load(id);
        getCurrentSession().delete(person);
    }

    public void flush() {
        getCurrentSession().flush();
    }
}