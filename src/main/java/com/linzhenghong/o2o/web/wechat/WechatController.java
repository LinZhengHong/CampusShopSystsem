package com.linzhenghong.o2o.web.wechat;

import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.WechatAuth;
import com.linzhenghong.o2o.service.PersonInfoService;
import com.linzhenghong.o2o.service.WechatAuthService;
import com.linzhenghong.o2o.util.wechat.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author LinZhenHong
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

    private static Logger logger= LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatAuthService WechatAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(method = {RequestMethod.GET})
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        logger.debug("Wechat get...");
        //微信加密签名
        String signature=request.getParameter("signature");
        //时间戳
        String timestamp=request.getParameter("timestamp");
        //随机数
        String nonce=request.getParameter("nonce");
        //随机字符串
        String echostr=request.getParameter("echostr");

        //通过检验signature对请求进行校验，若校验成功则原样返回echostr,表示接入成功，否则失败
        PrintWriter out=null;
        try {
            out=response.getWriter();
            if (SignUtil.checkSignature(signature,timestamp,nonce)){
                logger.debug("wechat get success");
                out.println(echostr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (out!=null){
                out.close();
            }
        }
    }
}
