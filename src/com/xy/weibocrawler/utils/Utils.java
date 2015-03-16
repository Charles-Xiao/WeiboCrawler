
package com.xy.weibocrawler.utils;

import com.xy.weibocrawler.db.Weibo;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * WeiBo对象转化为List<String>
     * 
     * @param weibo
     * @return
     */
    public static List<Object> WeiboToList(Weibo weibo) {
        List<Object> list = new ArrayList<>();
        if (weibo == null) {
            return list;
        }
        list.add(weibo.getmUserName());
        if (weibo.ismIsUserVip()) {
            list.add("true");
        } else {
            list.add("false");
        }
        list.add(weibo.getmContent());
        list.add(weibo.getmTime());
        list.add(weibo.getmFruitNum() + "");
        list.add(weibo.getmWineNum() + "");
        list.add(weibo.getmMilkNum() + "");
        list.add(weibo.getmSafetyNum() + "");
        list.add(weibo.getmCategory());
        return list;

    }
}
