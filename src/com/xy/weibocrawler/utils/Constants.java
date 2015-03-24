
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
            "番茄", "马铃薯", "洋葱", "蘑菇", "地沟油", "潲水油", "毒豆芽", "果汁", "金针菇", "韭菜", "果脯", "干果", "扁豆",
            "红富士", "竹笋", "荔枝", "草莓", "酱腌菜", "黄花菜", "蛇果", "食用菌", "菌菇"
    };

    public static final String[] getFruitsArr() {
        return FRUITS_ARR.clone();
    };

    /** 酒类微博关键词数组 */
    private static final String[] WINES_ARR = {
            "酒", "红酒", "白酒", "啤酒", "酒精", "黄酒", "米酒", "药酒", "果露酒", "果酒", "烧酒", "洋酒", "葡萄酒", "奶酒",
            "青稞酒"
    };

    public static final String[] getWinesArr() {
        return WINES_ARR.clone();
    };

    /** 乳制品类微博关键词数组 */
    private static final String[] MILKS_ARR = {
            "乳制品", "奶酪", "干酪", "牛奶", "奶粉", "奶油", "炼奶", "乳汁", "乳脂", "酸奶", "乳粉", "乳糖", "乳酸", "复原乳",
            "发酵乳", "乳酪", "乳饮料", "奶制品", "皮革奶", "酸乳", "玉米奶", "乳钙", "牛乳", "灭菌奶"
    };

    public static final String[] getMilksArr() {
        return MILKS_ARR.clone();
    };

    /** 食品安全微博关键词数组 */
    private static final String[] SAFETY_ARR = {
            "食品安全", "安全", "过期", "变质", "超标", "色素", "健康", "有害", "有毒", "有利", "有损", "致癌", "标准", "监管",
            "掺假", "防腐剂", "质量", "转基因", "注水", "食物中毒", "造假", "发霉", "劣质", "激素", "霉变", "胭脂红", "霉菌",
            "染色", "硫磺", "勾兑", "塑化剂", "污染", "假冒", "合格", "毒性", "伪劣", "异味", "细菌", "残留", "添加剂", "毒素",
            "黑榜", "违禁", "检验", "抽检", "评审", "以次充好", "投诉", "风险", "致病", "达标", "保健", "改善", "功效", "保质期",
            "农药残留量", "投毒", "兑水", "虚假"
    };

    public static final String[] getSafetyArr() {
        return SAFETY_ARR.clone(); // java数组深度复制，可以用clone或者System.arrayCopy
    };

    /** 入口url数组 */
    private static final String[] URL_ARR = {
            "http://weibo.com/u/3632129945", "http://weibo.com/u/3276450133",
            "http://weibo.com/u/5359649889", "http://weibo.com/u/1238994073",
            "http://weibo.com/guangzhoufda", "http://weibo.com/u/3870982729",
            "http://weibo.com/u/1281493412", "http://weibo.com/njfda",
            "http://weibo.com/uc274286783", "http://weibo.com/u/3701880892",
            "http://weibo.com/u/5128175397", "http://weibo.com/u/1335661387",
            "http://weibo.com/foodscienceweb", "http://weibo.com/u/3208088385",
            "http://weibo.com/u/3991843274", "http://weibo.com/whfda", "http://weibo.com/foodstd",
            "http://weibo.com/237588", "http://weibo.com/foodsafety365",
            "http://weibo.com/u/2456637532"

    };

    public static final String[] getUrlArr() {
        return URL_ARR.clone();
    }

    /** 爬虫测试URL */
    public static final String URl_INDEX = "http://weibo.com";

    public static final String URl_LOGIN = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.3.16)";

}
