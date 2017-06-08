package com.slimvan.xingyun.bean;

/**
 * Created by xingyun on 2017/6/7.
 */

public class BaseModel<T> {
    /**
     * error : 0
     * msg : success
     * iv : hE055gGm
     * xy : {"items":[{"user_id":"74","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170605/14966563295860.jpg","realname":"17688784666","work_time":"在公司工作时长9小时7分钟","order":1},{"user_id":"77","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170605/1496656210596.jpg","realname":"王2","work_time":"在公司工作时长0小时2分钟","order":2},{"user_id":"34","avatar":"http://static.tgy-test.xingyun.net/upload/images/avatar_default.png","realname":"刘8","work_time":"在公司工作时长0小时0分钟","order":3},{"user_id":"47","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170518/14950859685292.jpg","realname":"胡","work_time":"在公司工作时长0小时0分钟","order":4},{"user_id":"36","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170531/14962332223516.jpg","realname":"Bingo","work_time":"在公司工作时长0小时0分钟","order":5},{"user_id":"46","avatar":"http://static.tgy-test.xingyun.net/upload/images/avatar_default.png","realname":"18682452746","work_time":"在公司工作时长0小时0分钟","order":6},{"user_id":"81","avatar":"http://static.tgy-test.xingyun.net/upload/images/avatar_default.png","realname":"王4","work_time":"在公司工作时长0小时0分钟","order":7},{"user_id":"72","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170525/14957044216153.jpg","realname":"17766664444","work_time":"在公司工作时长0小时0分钟","order":8},{"user_id":"1","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170531/14962144108419.jpg","realname":"通知","work_time":"在公司工作时长0小时0分钟","order":9},{"user_id":"80","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170525/14956797646772.jpg","realname":"王3","work_time":"在公司工作时长0小时0分钟","order":10},{"user_id":"33","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170524/14956337576657.jpg","realname":"刘7","work_time":"在公司工作时长0小时0分钟","order":11},{"user_id":"73","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170523/14955242353349.jpg","realname":"13243887792","work_time":"在公司工作时长0小时0分钟","order":12},{"user_id":"76","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170525/14956794427736.jpg","realname":"王1","work_time":"在公司工作时长0小时0分钟","order":13},{"user_id":"87","avatar":"http://static.tgy-test.xingyun.net/upload/images/avatar_default.png","realname":"王5","work_time":"在公司工作时长0小时0分钟","order":14},{"user_id":"30","avatar":"http://static.tgy-test.xingyun.net/upload/images/avatar_default.png","realname":"刘3","work_time":"在公司工作时长0小时0分钟","order":15},{"user_id":"50","avatar":"http://static.tgy-test.xingyun.net/upload/images/avatar_default.png","realname":"13060557123","work_time":"在公司工作时长0小时0分钟","order":16},{"user_id":"51","avatar":"http://static.tgy-test.xingyun.net/upload/images/20170604/14965531894357.jpg","realname":"shady","work_time":"在公司工作时长0小时0分钟","order":17}]}
     */

    private String error;
    private String msg;
    private String iv;
    private T xy;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public T getXy() {
        return xy;
    }

    public void setXy(T xy) {
        this.xy = xy;
    }

}
