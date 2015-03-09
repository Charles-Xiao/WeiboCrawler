package com.xy.weibocrawler.utils;

import java.util.List;

import com.xy.weibocrawler.db.Weibo;

public class Utils {
	/**
	 * WeiBo对象转化为List
	 * @param weibo
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<Object> WeiboToList(Weibo weibo) {
		List<Object> list = null;
		if (weibo == null) {
			return list;
		}
		list.add(weibo.getmId());
		list.add(weibo.getmUserName());
		if (weibo.ismIsUserVip()) {
			list.add("true");
		} else {
			list.add("false");
		} 
		list.add(weibo.getmContent());
		list.add(weibo.getmTime());
		list.add(weibo.getmFruitNum()+"");
		list.add(weibo.getmWineNum()+"");
		list.add(weibo.getmMilkNum()+"");
		list.add(weibo.getmSafetyNum()+"");
		list.add(weibo.getmCategory());
		return list;
		
	}
}
