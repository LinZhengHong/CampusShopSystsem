package com.linzhenghong.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linzhenghong.o2o.dto.ShopExecution;
import com.linzhenghong.o2o.entity.Area;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.entity.ShopCategory;
import com.linzhenghong.o2o.enums.ShopStateEnum;
import com.linzhenghong.o2o.exception.ShopOperationException;
import com.linzhenghong.o2o.service.AreaService;
import com.linzhenghong.o2o.service.ShopCategoryService;
import com.linzhenghong.o2o.service.ShopService;
import com.linzhenghong.o2o.util.CodeUtil;
import com.linzhenghong.o2o.util.HttpServletRequestUtil;
import com.linzhenghong.o2o.util.ImageUtil;
import com.linzhenghong.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**店铺管理员
 * @author LinZhenHong
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     *获取店铺信息
     * @return modelMap
     */
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getshopinitinfo(){
        Map<String,Object> modelMap=new HashMap<String,Object>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        List<Area> areaList=new ArrayList<Area>();
        try {
            shopCategoryList=shopCategoryService.queryShopCategory(new ShopCategory());
            areaList=areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception  e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }


    /**
     * 注册店铺
     * @param request r
     * @return madelMap
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //判断验证码
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入错误验证码");
            return modelMap;
        }
        //1接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //上传的图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2 注册店铺、
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            //Sessiontodo
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution shopExecution = null;
            try {
                shopExecution = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            }catch (ShopOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", shopExecution.getStateInfo());
            }
            catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
        //3 返回结果
    }
}

    /**
     * File转成XCommonsMultipartFile
     * @param inputStream ins
     * @param file file
     */
    /*private static void inputStreamToFile(InputStream inputStream, File file){
        OutputStream outputStream=null;
        try{
            outputStream = new FileOutputStream(file);
            int byteRead=0;
            byte[] buffer=new byte[1024];
            while ((byteRead=inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,byteRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用inputStreamToFile产生异常"+e.getMessage());
        }finally {
            try{
                if(outputStream!=null)
                    outputStream.close();
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭io产生异常"+e.getMessage());
            }
        }
    }
}*/
