package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**区域
 * @author LinZhenHong
 */
@Repository
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
