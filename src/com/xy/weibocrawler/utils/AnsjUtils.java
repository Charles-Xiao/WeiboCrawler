
package com.xy.weibocrawler.utils;

import com.xy.weibocrawler.db.Weibo;

import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnsjUtils {
    
    /**
     * 用户添加自定义词典
     */
    public static void userDefineLib() {
        //增加食品安全词汇
    	UserDefineLibrary.insertWord("地沟油", "UD_safety", 1000);   //[自定义词]  [词性]  [词频]
    	UserDefineLibrary.insertWord("乳饮料", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("蒙牛", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("乳钙", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("果露酒", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("玉米奶", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("复原乳", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("发酵乳", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("皮革奶", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("地沟油", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("毒豆芽", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("潲水油", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("酱腌菜", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("农药残留量", "UD_safety", 1000);
    	UserDefineLibrary.insertWord("食品安全", "UD_safety", 1000);
    	//剔除歧义词汇
    	UserDefineLibrary.insertWord("苹果公司", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果表", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果电脑", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果手表", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果ID", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果官网", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果系统", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("苹果手机", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("酒驾", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("醉酒", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("毒驾", "UD_ambi", 1000);
        UserDefineLibrary.insertWord("过期不候", "UD_ambi", 1000);
        
    }
    public static void testAnsj() {
        String str = "茅台酒很多人认为榨汁是最健康的食用水果的方式腊肠增加胃癌风险投诉无门以次充好灭菌奶地沟油毒豆芽潲水油毒胶囊油炸食品酱腌菜黄花菜白萝卜胡萝卜含有酒牛乳很健康乳钙玉米奶皮革奶发酵乳复原乳干酪茅台果露酒假冒塑化剂染色胭脂红霉变甘蔗假鸡蛋假牛肉假鸭血乳钙食物中毒有毒早餐奶茅台优酸乳酸酸乳转基因蒙牛伊利奶制品乳制品红富士乳饮料婴幼儿奶粉保健食品食品保健地沟油油地沟果蔬蔬菜白菜青菜白萝卜萝卜梨子食品安全过期变质超标色素健康有害有利有损致癌标准监管掺假防腐剂质量问题";
        Weibo weibo = new Weibo("xiao", true, str, "", 0, 0, 0, 0, null);
        List<Weibo> weibos = new ArrayList<>();
        weibos.add(weibo);
        AnsjUtils.getKeywordsNum(weibos);
        List<Term> parseResult = ToAnalysis.parse(str);
        System.out.println(parseResult + "\n");
        System.out.println(parseResult.get(1).getRealName());
        System.out.println(parseResult.get(1).getName());
        System.out.println(parseResult.get(1).getNatureStr());// 获得词性
        System.out.println(parseResult.get(1).getNext());
        System.out.println(parseResult.get(1).getOffe());
        System.out.println(parseResult.get(1).getSubTerm());
    }

    // http://blog.csdn.net/lazy_p/article/details/7365286
    public static List<Weibo> getKeywordsNum(List<Weibo> list) {
        List<String> fruitList = Arrays.asList(Constants.getFruitsArr());
        List<String> milkList = Arrays.asList(Constants.getMilksArr());
        List<String> wineList = Arrays.asList(Constants.getWinesArr());
        List<String> safetyList = Arrays.asList(Constants.getSafetyArr());
        for (Weibo weibo : list) {
            String content = weibo.getmContent();
            List<Term> parseResult = ToAnalysis.parse(content);
            List<String> keywordsf = new ArrayList<>();
            List<String> keywordsm = new ArrayList<>();
            List<String> keywordsw = new ArrayList<>();
            List<String> keywordss = new ArrayList<>();
            for (Term term : parseResult) {
                keywordsf.add(term.getRealName());
                keywordsm.add(term.getRealName());
                keywordsw.add(term.getRealName());
                keywordss.add(term.getRealName());
            }
            keywordsf.retainAll(fruitList);
            int fNum = keywordsf.size();
            keywordsm.retainAll(milkList);
            int mNum = keywordsm.size();
            keywordsw.retainAll(wineList);
            int wNum = keywordsw.size();
            keywordss.retainAll(safetyList);
            int sNum = keywordss.size();
            //去除主题无关的weibo
            if (fNum == 0 && mNum == 0 && wNum == 0 || sNum == 0) {
                list.remove(weibo);
            } else {
                weibo.setmFruitNum(fNum);
                weibo.setmMilkNum(mNum);
                weibo.setmWineNum(wNum);
                weibo.setmSafetyNum(sNum);
//                System.out.println("主题相关微博： " + weibo.toString());
            }
           
        }
        return list;
    }
}
