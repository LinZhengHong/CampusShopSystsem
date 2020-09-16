package com.linzhenghong.o2o.util.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 微信用户实体类
 * @author LinZhenHong
 */
public class WechatUser implements Serializable {

    private static final long serialVersionUID = -5597870075603388500L;

    /**
     * id
     */
    @JsonProperty("id")
    private int id;

    /**
     *openID,微信账号唯一标识
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 微信姓名
     */
    @JsonProperty("nickname")
    private String nickName;

    /**
     * 性别
     */
    @JsonProperty("sex")
    private int sex;

    /**
     * 省份
     */
    @JsonProperty("province")
    private String province;

    /**
     * 城市
     */
    @JsonProperty("city")
    private String city;

    /**
     * 国家
     */
    @JsonProperty("country")
    private String country;

    /**
     * 头像图片地址
     */
    @JsonProperty("headimgurl")
    private String headimgurl;

    /**
     * 语言
     */
    @JsonProperty("language")
    private String language;

    /**
     * 用户权限
     */
    @JsonProperty("privilege")
    private String[] privilege;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "WechatUser{" +
                "openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
