package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.WechatAuth;
import org.springframework.stereotype.Repository;

/**
 * @author LinZhenHong
 */
@Repository
public interface WechatAuthDao {
    /**
     * 通过openId查询对应本平台的微信账号
     * @param openId
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    int insertWechatAuth(WechatAuth wechatAuth);
}
