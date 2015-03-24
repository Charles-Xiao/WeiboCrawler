
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
        // 增加食品安全词汇
        UserDefineLibrary.insertWord("地沟油", "UD_safety", 1000); // [自定义词] [词性]
                                                                // [词频]
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
        // 剔除歧义词汇
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
            // 去除主题无关的weibo
            if (fNum == 0 && mNum == 0 && wNum == 0 || sNum == 0) {
                list.remove(weibo);
            } else {
                weibo.setmFruitNum(fNum);
                weibo.setmMilkNum(mNum);
                weibo.setmWineNum(wNum);
                weibo.setmSafetyNum(sNum);
                // System.out.println("主题相关微博： " + weibo.toString());
            }

        }
        return list;
    }
}
