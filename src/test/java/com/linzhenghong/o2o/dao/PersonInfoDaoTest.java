package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonInfoDaoTest extends BaseTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testAInsertPersonInfo() throws Exception{
        //设置新增的用户信息
        PersonInfo personInfo=new PersonInfo();
        personInfo.setName("我恨你");
        personInfo.setGender("女");
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setEnableStatus(1);
        int effectedNum=personInfoDao.insertPersonInfo(personInfo);
        System.out.println(effectedNum==1);
    }

    @Test
    public void testQueryPersonInfoById(){
        long userId=1;
        //查询Id为1的用户信息
        PersonInfo personInfo=personInfoDao.queryPersonInfoById(userId);
        System.out.println(personInfo.getName());
    }


}