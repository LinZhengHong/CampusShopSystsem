package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.dto.WechatAuthExecution;
import com.linzhenghong.o2o.entity.WechatAuth;
import com.linzhenghong.o2o.exception.WechatAuthOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author LinZhenHong
 */
public interface WechatAuthService {

    /**
     *通过openId获得其微信账号
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     *注册本平台微信账号
     * @param wechatAuth
     * @return
     * @throws WechatAuthOperationException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;
}
