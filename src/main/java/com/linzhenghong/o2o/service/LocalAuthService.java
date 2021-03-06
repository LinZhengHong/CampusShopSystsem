package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.dto.LocalAuthExecution;
import com.linzhenghong.o2o.entity.LocalAuth;
import com.linzhenghong.o2o.exception.LocalAuthOperationException;

/**
 * @author LinZhenHong
 */
public interface LocalAuthService {

    /**
     * 通过账号和密码获取平台账号信息
     * @param userName
     * @param password
     * @return
     */
    LocalAuth getLocalAuthByUsernameAndPwd(String userName,String password) throws LocalAuthOperationException;

    /**
     * 通过userId获取平台账号信息
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * 绑定微信，生成平台专属的账号
     * @param localAuth
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;


    /**
     * 修改平台账号的登录密码
     * @param userId
     * @param username
     * @param password
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution modifyLocalAuth(Long userId,String username,String password,String newPassword) throws LocalAuthOperationException;
}
