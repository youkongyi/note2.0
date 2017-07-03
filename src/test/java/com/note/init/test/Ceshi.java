package com.note.init.test;

import com.note.entity.UserBO;
import com.note.user.controller.IUserLoginController;
import com.note.user.dao.IUserLoginDAO;
import com.note.user.service.IUserLoginService;
import com.note.utils.ConvertMap;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */
public class Ceshi {

    ClassPathXmlApplicationContext ctx;
    @Before
    public void init(){
        ctx = new ClassPathXmlApplicationContext("spring-properties.xml","spring-web.xml","spring-mybatis.xml");
    }

    @Test
    public void test1(){
        DataSource ds = ctx.getBean("dataSource",DataSource.class);
        System.out.println(ds);

        SqlSessionFactory sql = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
        System.out.println(sql);

        MapperScannerConfigurer map = (MapperScannerConfigurer) ctx.getBean("mapperScanner");
        System.out.println(map);
    }

    @Test
    public void test2(){
        IUserLoginDAO user = ctx.getBean("IUserLoginDAO",IUserLoginDAO.class);
        UserBO bo = user.queryUserByPassport("123213");
        System.out.println(bo.getCreateDate());
    }

    @Test
    public void test3(){
        IUserLoginService user = ctx.getBean("userLoginService",IUserLoginService.class);
    }

    @Test
    public void test4(){
        IUserLoginController user = ctx.getBean("userLoginController",IUserLoginController.class);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("passport",new String[]{"123213"});
        map.put("password",new String[]{"阿斯顿阿斯顿"});
        ConvertMap con = new ConvertMap(map);
        user.queryUserByPassport(con);
    }

}
