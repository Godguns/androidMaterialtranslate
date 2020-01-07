package com.example.ds6;

import java.io.Serializable;

public class GoodsEntity implements Serializable {

    public String goodsName;//货物名称
    public String goodsPrice;//货物价格
 public int  IMG;

    public GoodsEntity(String goodsName, String goodsPrice, int img) {

        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.IMG=img;
    }


    public int getIMG() {
        return IMG;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public void setIMG(int img) {
        this.IMG = img;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +

                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", IMG='" + IMG + '\'' +
                '}';
    }
}
