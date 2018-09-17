package com.chaozhuo.familybrain.bean;

/**
 * Created by fewwind on 18-9-7.
 */

public class ThreeDBean {
    public float[] destPos;
    public String imgUrl;
    public int colorId;
    public String tag;
    public String name;

    public ThreeDBean(float[] destPos, String imgUrl, int colorId, String tag, String name) {
        this.destPos = destPos;
        this.imgUrl = imgUrl;
        this.colorId = colorId;
        this.tag = tag;
        this.name = name;
    }
}
