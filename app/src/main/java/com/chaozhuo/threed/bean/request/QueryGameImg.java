package com.chaozhuo.threed.bean.request;

/**
 * Created by fewwind on 18-7-13.
 * 查询所有游戏的封面图
 */

public class QueryGameImg {
    String[] app_ids;

    public QueryGameImg(String[] app_ids) {
        this.app_ids = app_ids;
    }
}
