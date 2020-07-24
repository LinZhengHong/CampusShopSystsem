package com.linzhenghong.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linzhenghong.o2o.dto.ImageHolder;
import com.linzhenghong.o2o.dto.ProductExecution;
import com.linzhenghong.o2o.entity.Product;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.enums.ProductStateEnum;
import com.linzhenghong.o2o.exception.ProductOperationException;
import com.linzhenghong.o2o.service.ProductCategoryService;
import com.linzhenghong.o2o.service.ProductService;
import com.linzhenghong.o2o.util.CodeUtil;
import com.linzhenghong.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LinZhenHong
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private static  final int IMAGEMAXCOUNT=6;

    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String, Object> modelMap=new HashMap<>();
        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //接收前端参数的变化量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper=new ObjectMapper();
        Product product=null;
        String productStr= HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest multipartHttpServletRequest=null;
        ImageHolder thumbnail=null;
        List<ImageHolder> productImgList=new ArrayList<>();
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());

        try{
            //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)){
                multipartHttpServletRequest=(MultipartHttpServletRequest) request;
                //取出缩略图并构建ImageHolder对象
                CommonsMultipartFile thumbnailFile=(CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                thumbnail=new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                //取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
                for(int i=0;i<IMAGEMAXCOUNT;i++){
                    CommonsMultipartFile productImgFile=(CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg"+i);
                    if (productImgFile!=null){
                        //若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                        ImageHolder productImg=new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        productImgList.add(productImg);
                    }else{
                        //若取出的第i个详情图片文件流为空，则终止循环
                        break;
                    }
                }
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        try{
            //尝试获取前端传过来的表单string流并将其转换Product实体类

            //在这里出现了一个坑，就是productStr传过来不能映射到product实体类，
            // 解决方法就是在对应的实体类上添加@JsonIgnoreProperties(ignoreUnknown = true)
            product=mapper.readValue(productStr,Product.class);

        }catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            e.printStackTrace();
            return modelMap;
        }

        //若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product!=null&&thumbnail!=null&&productImgList.size()>0){
            try{
                //从session中获取当前店铺的Id并赋值给product,减少对前端数据的依赖
                Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
                Shop shop=new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //执行添加操作
                ProductExecution productExecution=productService.addProduct(product,thumbnail,productImgList);
                if (productExecution.getState()== ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",productExecution.getStateInfo());
                }
            }catch (ProductOperationException e){
                    modelMap.put("success",false);
                    modelMap.put("errMsg",e.toString());
                    return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 通过商品id获取商品信息
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam Long productId){
        Map<String,Object> modelMap=new HashMap<>();
        //非空判断
        if (productId>-1){
            //获取商品信息
            Product product=productService.getProductById(productId);
            //获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList=productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty productId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        //是商品编辑时候调用还是上下架操作的时候调用
        //若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange=HttpServletRequestUtil.getboolean(request,"statusChange");
        //验证码判断
        if (!statusChange&&!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper=new ObjectMapper();
        Product product=null;
        ImageHolder thumbnail=null;
        List<ImageHolder> productImgList=new ArrayList<>();
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
        try{
            if (multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
                //取出缩略图并构建ImageHolder对象
                CommonsMultipartFile thumbnailFile=(CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                if (thumbnailFile!=null){
                    thumbnail=new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                }
                for (int i=0;i<IMAGEMAXCOUNT;i++){
                    CommonsMultipartFile productImgFile=(CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg"+i);
                    if (productImgFile!= null){
                        ImageHolder productImg=new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        productImgList.add(productImg);
                    }else{
                        //若取出的第i个详情图片文件流为空，则终止循环
                        break;
                    }
                }
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        try{
            String productStr=HttpServletRequestUtil.getString(request,"productStr");
            //尝试获取前端过来的表单string流并将其转换Product实体类
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        //非空判断
        if (product!=null){
            try{
                //从session中获取当前店铺的id并赋值给product,减少对前端数据的依赖
                Shop currentShop=(Shop)request.getSession().getAttribute("currentShop");
                Shop shop=new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //开始进行商品信息变更操作
                ProductExecution productExecution=productService.modifyProduct(product,thumbnail,productImgList);
                if (productExecution.getState()==ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",productExecution.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }
}
