package com.linzhenghong.o2o.service;


import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.dto.WechatAuthExecution;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.WechatAuth;
import com.linzhenghong.o2o.enums.WechatAuthStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class WechatAuthServiceImplTest extends BaseTest {

    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void testRegister(){
        //新增一条微信账号
        WechatAuth wechatAuth=new WechatAuth();
        PersonInfo personInfo=new PersonInfo();
        String openId="linzhenghongsdsdf";
        //给微信账号设置上用户信息，但不设置上用户Id
        //希望创建微信账号的时候自动创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("测试一下");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId(openId);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution wechatAuthExecution=wechatAuthService.register(wechatAuth);
        System.out.println(WechatAuthStateEnum.SUCCESS.getState()==wechatAuthExecution.getState());
        wechatAuth=wechatAuthService.getWechatAuthByOpenId(openId);
        //打印用户名字看看跟跟预期是否相符
        System.out.println(wechatAuth.getPersonInfo().getName());
    }
}
