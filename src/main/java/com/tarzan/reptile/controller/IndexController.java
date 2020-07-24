package com.tarzan.reptile.controller;

import com.tarzan.reptile.domain.dto.PlatformDTO;
import com.tarzan.reptile.domain.entity.PlatformEntity;
import com.tarzan.reptile.service.IndexService;
import com.tarzan.reptile.utils.ImageUtil;
import com.tarzan.reptile.utils.SmartBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页控制器
 *
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/21$ 8:58$
 * @since JDK1.8
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/")
    private String index(Model model){
        List<PlatformEntity> platformList=indexService.platformList();
        List<PlatformDTO> resultList=platformList.stream().map(e->{
            PlatformDTO dto=SmartBeanUtil.copy(e,PlatformDTO.class);
            dto.setImgBase64("data:image/png;base64,"+ImageUtil.getImgUrlToBase64(e.getRoundHeadImg()));
            return dto;
        }).collect(Collectors.toList());
        model.addAttribute("platformList", resultList);
        return "/index";
    }

    @GetMapping("/about")
    private String about(Model model){
        return "/about";
    }



}
