
package com.xy.weibocrawler.utils;

import com.xy.weibocrawler.db.Weibo;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnsjUtils {
    public static void testAnsj() {
        String str = "果蔬蔬菜白菜青菜白萝卜萝卜梨子食品安全过期变质超标色素健康有害有利有损致癌标准监管掺假防腐剂质量问题";
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
            System.out.println(keywordsf.toString());
            keywordsm.retainAll(milkList);
            int mNum = keywordsm.size();
            keywordsw.retainAll(wineList);
            int wNum = keywordsw.size();
            keywordss.retainAll(safetyList);
            int sNum = keywordss.size();
            System.out.println(keywordss.toString());
            weibo.setmFruitNum(fNum);
            weibo.setmMilkNum(mNum);
            weibo.setmWineNum(wNum);
            weibo.setmSafetyNum(sNum);
            System.out.println(weibo.toString());
        }
        return list;
    }
}
