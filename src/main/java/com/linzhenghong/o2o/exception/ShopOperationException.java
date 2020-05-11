package com.linzhenghong.o2o.exception;

/**对其进行简单的封装，意义在于当发生错误异常时就能够知道是哪个部分的异常
 * @author LinZhenHong
 */
public class ShopOperationException extends RuntimeException {

    public ShopOperationException(String msg){
        super(msg);
    }
}
