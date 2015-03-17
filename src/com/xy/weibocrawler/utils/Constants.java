
package com.xy.weibocrawler.utils;

/**
 * @author xiaoyong 全局常量类
 *         对于包括可变对象的引用的final常量(Array),不可以直接设置为public，而应该如下设置以防止对象被更改
 */
public class Constants {

    /** 果蔬类微博关键词数组 */
    private static final String[] FRUITS_ARR = {
            "果蔬", "水果", "蔬菜", "柑橘", "菠萝", "芒果", "柠檬", "葡萄", "西瓜", "梨", "梨子", "苹果", "香蕉", "西红柿",
            "冬瓜", "南瓜", "萝卜", "胡萝卜", "白萝卜", "白菜", "黄瓜", "玉米", "大豆", "番薯", "青菜", "莲藕", "茄子", "辣椒",
            "番茄", "马铃薯", "洋葱", "蘑菇", ""
    };

    public static final String[] getFruitsArr() {
        return FRUITS_ARR.clone();
    };

    /** 酒类微博关键词数组 */
    private static final String[] WINES_ARR = {
            "酒", "红酒", "白酒", "啤酒", "酒精", "黄酒", "米酒", "药酒", "果露酒", "果酒", "烧酒", "洋酒"
    };

    public static final String[] getWinesArr() {
        return WINES_ARR.clone();
    };

    /** 乳制品类微博关键词数组 */
    private static final String[] MILKS_ARR = {
            "乳制品", "奶酪", "干酪", "牛奶", "奶粉", "奶油", "炼奶", "乳汁", "乳脂", "酸奶", "乳粉", "乳糖", "乳酸", "复原乳",
            "发酵乳", "乳酪"
    };

    public static final String[] getMilksArr() {
        return MILKS_ARR.clone();
    };

    /** 食品安全微博关键词数组 */
    private static final String[] SAFETY_ARR = {
            "食品", "安全", "过期", "变质", "超标", "色素", "健康", "有害", "有利", "有损", "致癌", "标准", "监管", "掺假",
            "防腐剂", "质量", "问题"
    };

    public static final String[] getSafetyArr() {
        return SAFETY_ARR.clone(); // java数组深度复制，可以用clone或者System.arrayCopy
    };

    /** 入口url数组 */
    private static final String[] URL_ARR = {
            "http://weibo.com/u/3632129945",
            "http://weibo.com/u/3276450133#_loginLayer_1426411111788",
            "http://weibo.com/u/5359649889", "http://weibo.com/xtjtjyjst?from=usercardnew",
            "http://weibo.com/foodstandard?from=hissimilar_home"
    };

    public static final String[] getUrlArr() {
        return URL_ARR.clone();
    }

    /** 爬虫测试URL */
    public static final String URl_INDEX = "http://weibo.com";

    public static final String URl_LOGIN = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.3.16)";

    public static final String URl_TEST_V = "http://weibo.com/u/3632129945?is_search=0&visible=0&is_tag=0&profile_ftype=1&page=1#feedtop";

    public static final String URl_TEST = "http://weibo.com/2262300105/profile?topnav=1&wvr=6";

    public static final String URl_TEST_S = "http://s.weibo.com/wb/%25E9%25A3%259F%25E5%2593%2581%25E5%25AE%2589%25E5%2585%25A8&xsort=time&Refer=user_wb";
}
