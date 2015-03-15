
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
        for (Weibo weibo : list) {
            String content = weibo.getmContent();
            List<Term> parseResult = ToAnalysis.parse(content);
            List<String> keywords = new ArrayList<>();
            for (Term term : parseResult) {
                keywords.add(term.getRealName());
            }
            keywords.retainAll(fruitList);
        }
        return list;
    }
}
