package com.linzhenghong.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LinZhenHong
 */
public class HttpServletRequestUtil {

    public static int getInt(HttpServletRequest request,String key){
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }
    public static long getLong(HttpServletRequest request,String key){
        try{
            return Long.parseLong(request.getParameter(key));
        }catch (Exception e){
            return -1L;
        }
    }
    public static double getDouble(HttpServletRequest request,String key){
        try{
            return Double.parseDouble(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }
    public static boolean getboolean(HttpServletRequest request,String key){
        try{
            return Boolean.parseBoolean(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String key){
        try{
            String result=request.getParameter(key);
            if(result!=null){
                result=result.trim();
            }
            if("".equals(result)){
                result=null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
