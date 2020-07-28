package com.tarzan.reptile.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarzan.reptile.domain.entity.InfoEntity;
import com.tarzan.reptile.domain.entity.PlatformEntity;
import com.tarzan.reptile.mapper.InfoDao;
import com.tarzan.reptile.mapper.PlatformDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页服务层
 *
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/21$ 9:38$
 * @since JDK1.8
 */
@Service
public class IndexService {

    @Autowired
    private PlatformDao platformDao;

    @Autowired
    private InfoDao infoDao;

    public List<InfoEntity> list(String appmsgid){
        QueryWrapper<InfoEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("appmsgid",appmsgid);
        return infoDao.selectList(null);
    }

    public List<PlatformEntity> platformList(){
        return platformDao.selectList(null);
    }

}
