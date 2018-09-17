package com.chaozhuo.familybrain.net;

import com.chaozhuo.familybrain.bean.AppUpdateBean;
import com.chaozhuo.familybrain.bean.request.QueryGameImg;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by fewwind on 18-2-26.
 */

public interface HttpService {

    String URI_GAME_TYPE = "/v1/one/game/cate";
    String URI_APP_TYPE = "/v1/one/app/cate";
    String URI_GAME_TYPE_LIST = "/v1/one/games";
    String URI_APP_TYPE_LIST = "/v1/one/apps";
    String URI_GAME_BANNER = "/v1/one/game/banner";// 热门4张banner
    String URI_GAME_RANKING = "/v1/one/game/ranking";// 热门排行
    String URI_GAME_SUBJECY = "/v1/one/game/topic";// 热门专题

    String URI_GAME_SELECT = "/v1/one/game/cate-sel";// 精选

    String URI_GAME_SUBJECY_DETAIL = "/v1/one/game/topic-info";// 专题详情
    String URI_HOME_RECOMMENDED = "/v1/one/home/recommend";// 首页推荐

    String URI_GAME_DETAIL = "/v1/one/game/info";// 游戏详情
    String URI_APP_DETAIL = "/v1/one/app/info";// 应用详情

    String URL_SEARCH_KEYWORD = "/v1/one/search"; // 关键词搜索
    String URL_GET_ALL_GAME_PKG = "/v1/one/game/appids";  //获取所有游戏列表
    String URL_GET_SCREEN_SAVER = "/v1/one/screen/saver";  //获取屏保
    String URL_GET_SEARCH_RECOMMEND = "/v1/one/search/recommend";  //获取搜索推荐列表
    String URL_DOWNLOAD_STATIS = "/v1/one/notify/download";  //获取搜索推荐列表

    String URL_UPDATE_APPS = "/v1/one/updates";  //更新应用列表
    String URL_UPDATE_GAMES = "/v1/one/game-updates"; //获取游戏列表的信息
    String URL_GET_GAME_COVER = "/v1/one/covers"; //获取游戏封面图


    @POST(URL_GET_GAME_COVER)
    Observable<BaseResponse<List<AppUpdateBean>>> getGamesCover(@Body QueryGameImg body);
}
