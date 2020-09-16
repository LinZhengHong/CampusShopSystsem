package com.linzhenghong.o2o.web.wechat;

import com.linzhenghong.o2o.dto.WechatAuthExecution;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.WechatAuth;
import com.linzhenghong.o2o.enums.WechatAuthStateEnum;
import com.linzhenghong.o2o.service.PersonInfoService;
import com.linzhenghong.o2o.service.WechatAuthService;
import com.linzhenghong.o2o.util.wechat.WechatUser;
import com.linzhenghong.o2o.util.wechat.WechatUtil;
import com.linzhenghong.o2o.util.wechat.message.pojo.UserAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LinZhenHong
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd7f6c5b8899fba83&redirect_uri=http://o2o.yitiaojieinfo.com/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code之后再可以通过code获取到access_token进而获取到用户信息
 */
@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {

    private static Logger logger= LoggerFactory.getLogger(WechatLoginController.class);
    private static final String FRONTEND="1";
    private static final String SHOPEND="2";


    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private WechatAuthService wechatAuthService;


    @RequestMapping(value = "/logincheck",method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response){
        logger.debug("weixin login get...");
        //获取微信公众号传输过来的code,通过code可获取access_token，进而获取用户信息
        String code=request.getParameter("code");
        //这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        String roleType=request.getParameter("state");
        logger.debug("weixin login code:"+code);
        WechatUser user = null;
        WechatAuth auth=null;
        String openId=null;
        if (null!=code){
            UserAccessToken token;
            try{
                //通过code获取access_token
                token= WechatUtil.getUserAccessToken(code);
                logger.debug("wechat login token:"+token.toString());
                //通过token获取accessToken
                String accessToken=token.getAccessToken();
                //通过token获取openId
                openId=token.getOpenId();
                //通过access_token和openId获取用户昵称等信息
                user=WechatUtil.getUserInfo(accessToken,openId);
                logger.debug("wechat login user:"+user.toString());
                request.getSession().setAttribute("openId",openId);
                auth=wechatAuthService.getWechatAuthByOpenId(openId);
            }catch (Exception e){
                logger.error("error in getUserAccessToken or getUserInfo or findByOpenId:"+e.toString());
                e.printStackTrace();
            }
        }
        //若微信账号为空则需要注册微信账号，同时注册用户信息
        if (auth==null){
            PersonInfo personInfo=WechatUtil.getPersonInfoFromRequest(user);
            auth=new WechatAuth();
            auth.setOpenId(openId);
            if (FRONTEND.equals(roleType)){
                personInfo.setUserType(1);
            }else{
                personInfo.setUserType(2);
            }
            auth.setPersonInfo(personInfo);
            //这里为啥是一个参数
            WechatAuthExecution wechatAuthExecution=wechatAuthService.register(auth);
            if (wechatAuthExecution.getState()!= WechatAuthStateEnum.SUCCESS.getState()){
                return null;
            }else{
                personInfo=personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user",personInfo);
            }
        }
        //若用户点击的是前端展示系统按钮则进入前端展示系统
        if (FRONTEND.equals(roleType)){
            return "frontend/index";
        }else{
            return "shopadmin/shoplist";
        }
    }


}
