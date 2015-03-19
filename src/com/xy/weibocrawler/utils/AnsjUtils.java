
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
        
    }
    public static void testAnsj() {
        UserDefineLibrary.insertWord("地沟油", "UD_safety", 1000);   //[自定义词]  [词性]  [词频]
        UserDefineLibrary.insertWord("保健食品食品", "UD_safety", 1000);
        String str = "保健食品食品保健地沟油油地沟果蔬蔬菜白菜青菜白萝卜萝卜梨子食品安全过期变质超标色素健康有害有利有损致癌标准监管掺假防腐剂质量问题";
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
                System.out.println("主题相关微博： " + weibo.toString());
            }
           
        }
        return list;
    }
}
