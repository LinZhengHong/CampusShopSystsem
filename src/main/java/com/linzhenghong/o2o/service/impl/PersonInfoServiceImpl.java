package com.linzhenghong.o2o.service.impl;

import com.linzhenghong.o2o.dao.PersonInfoDao;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LinZhenHong
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;


    /**
     * 根据用户id获取personInfo信息
     *
     * @param userId
     * @return
     */
    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }
}
