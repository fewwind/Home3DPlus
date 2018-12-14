package com.chaozhuo.threed.net;

import com.chaozhuo.threed.bean.AppUpdateBean;
import com.chaozhuo.threed.bean.request.QueryGameImg;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by fewwind on 18-2-26.
 */

public interface HttpService {

    String URL_GET_GAME_COVER = "/v1/one/covers"; //获取游戏封面图

    @POST(URL_GET_GAME_COVER)
    Observable<BaseResponse<List<AppUpdateBean>>> getGamesCover(@Body QueryGameImg body);
}
