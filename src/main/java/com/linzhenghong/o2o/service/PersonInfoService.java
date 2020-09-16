package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.entity.PersonInfo;

/**
 * @author LinZhenHong
 */
public interface PersonInfoService {

    /**
     *根据用户id获取personInfo信息
     * @param userId
     * @return
     */
    PersonInfo getPersonInfoById(Long userId);
}
