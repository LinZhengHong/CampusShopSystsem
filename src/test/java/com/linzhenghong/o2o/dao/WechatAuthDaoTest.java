package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.WechatAuth;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest extends BaseTest {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    public void testAInsertWechatAuto() throws Exception{
        //新增一条微信账号
        WechatAuth wechatAuth=new WechatAuth();
        PersonInfo personInfo=new PersonInfo();
        personInfo.setUserId(1L);
        //给微信账号绑定上用户信息
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId("dadadjfasda");
        wechatAuth.setCreateTime(new Date());
        int effectedNum=wechatAuthDao.insertWechatAuth(wechatAuth);
        System.out.println(effectedNum==1);
    }

    @Test
    public void testBQueryWechatAuthByOpenId() throws Exception{
        WechatAuth wechatAuth=wechatAuthDao.queryWechatInfoByOpenId("dadadjfasda");
        System.out.println(wechatAuth.getPersonInfo().getName());
    }

}
