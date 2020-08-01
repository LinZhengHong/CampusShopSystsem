package com.linzhenghong.o2o.service.impl;

import com.linzhenghong.o2o.dao.HeadLineDao;
import com.linzhenghong.o2o.entity.HeadLine;
import com.linzhenghong.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author LinZhenHong
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    /**
     * 根据传入的条件返回指定的头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
