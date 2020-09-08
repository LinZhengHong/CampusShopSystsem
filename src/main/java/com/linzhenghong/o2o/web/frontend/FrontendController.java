package com.linzhenghong.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author LinZhenHong
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    private String index(){
        return "frontend/index";
    }


    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    private String shoplist(){
        return "frontend/shoplist";
    }
}
