package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.LocalAuth;
import com.linzhenghong.o2o.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    private static final String username="testusername";
    private static final String password="testpassword";

    @Test
    public void testAInsertLocalAuth() throws Exception{
        //新增一条平台账号信息
        LocalAuth localAuth=new LocalAuth();
        PersonInfo personInfo=new PersonInfo();
        personInfo.setUserId(1L);
        //给平台账号绑定上用户信息
        localAuth.setPersonInfo(personInfo);
        //设置上用户名和密码
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        int effectedNum=localAuthDao.insertLocalAuth(localAuth);
        System.out.println(effectedNum==1);
    }

    /**
     * java.lang.NullPointerException空指针异常是像我一样新手很容易出现的问题，这个问题一般情况都是不细心的时候出现的
     * 1、业务层面的错误：
     *a、没有写非空验证：if(list.size()>0 && !"".equal(list));
     *b、service方法引用注解的，有可能没有写注解（这个问题以前没有用代码模板工具经常出现）；
     * 2、sql方法层面的错误：
     *a、mapper文件的dao路径引用路径写错，如下图（仅供参考）
     * b、sql语句写错，（这个问题你在写sql语句的时候应该跑一下看看有没有错误，我觉得对于像我一样的萌新很有必要）
     * @throws Exception
     */
    @Test
    public void testCQueryLocalByUserNameAndPwd() throws Exception{
        //按照账号和密码查询用户信息
        LocalAuth localAuth=localAuthDao.queryLocalByUserNameAndPwd(username,"testnowpassword");
        System.out.println(localAuth.getPersonInfo().getName());
    }


    @Test
    public void testCQueryLocalByUserId() throws Exception{
        //按照用户Id查询平台账号，进而获取用户信息
        LocalAuth localAuth=localAuthDao.queryLocalByUserId(1L);
        System.out.println(localAuth.getPersonInfo().getName());
    }


    @Test
    public void testDUpdateLocalAuth() throws Exception{
        //依据用户id,平台账号，以及旧密码修改平台账号密码
        Date now=new Date();
        int effectedNum=localAuthDao.updateLocalAuth(1L,username,password,"testnowpassword",now);
        System.out.println(effectedNum==1);
        //查询出该条平台账号的最新信息
        LocalAuth localAuth=localAuthDao.queryLocalByUserId(1L);
        //输出新密码
        System.out.println(localAuth.getPassword());
    }
}
