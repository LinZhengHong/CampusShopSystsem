package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.PersonInfo;
import org.springframework.stereotype.Repository;

/**
 * @author LinZhenHong
 */
@Repository
public interface PersonInfoDao {


    /**
     * 通过用户Id查询用户
     * @param userId
     * @return
     */
    PersonInfo queryPersonInfoById(long userId);


    /**
     * |添加用户信息
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);
}
