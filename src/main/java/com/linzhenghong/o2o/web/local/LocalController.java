package com.linzhenghong.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author LinZhenHong
 */
@Controller
@RequestMapping(value = "/local",method = RequestMethod.GET)
public class LocalController {

    /**
     * 账号绑定路由
     * @return
     */
    @RequestMapping(value = "/accountbind")
    public String AccountBind(){
        return "/local/accountbind";
    }


    /**
     * 修改密码页路由
     * @return
     */
    @RequestMapping(value = "/changepsw")
    public String ChangePsw(){
        return "/local/changepsw";
    }


    /**
     * 登录页路由
     * @return
     */
    @RequestMapping(value = "/login")
    public String Login(){
        return "/local/login";
    }


}
