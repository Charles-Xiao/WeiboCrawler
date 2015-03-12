
package com.xy.weibocrawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.xy.weibocrawler.html.HtmlClient;
import com.xy.weibocrawler.html.HtmlParser;
import com.xy.weibocrawler.utils.Constants;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.List;

public class Crawler {
    public static void main(String args[]) throws Exception {

        String str = "果蔬蔬菜白菜青菜白萝卜萝卜梨子食品安全过期变质超标色素健康有害有利有损致癌标准监管掺假防腐剂质量问题";
        List<Term> parseResult = ToAnalysis.parse(str);
        System.out.println(parseResult + "\n");
        System.out.println(parseResult.get(1).getRealName());
        System.out.println(parseResult.get(1).getName());
        System.out.println(parseResult.get(1).getNatureStr());// 获得词性
        System.out.println(parseResult.get(1).getNext());
        System.out.println(parseResult.get(1).getOffe());
        System.out.println(parseResult.get(1).getSubTerm());

        // JDBC.INSTANCE.getDbConnection();

        WebClient wc = new WebClient();
        initWebClient(wc);

        // HtmlClient.loginSinaWeibo(wc, Constants.URl_LOGIN);
        String html = HtmlClient.getHTMLByUnit(wc, Constants.URl_TEST_V);
        // System.out.println(HtmlParser.parseUserName(html));
         HtmlParser.parseWeibo(html);
         HtmlParser.parseUrls(html);

    }

    private static void initWebClient(WebClient wc) {
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); // 禁用css支持
        // wc.setAjaxController(new NicelyResynchronizingAjaxController());
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.addRequestHeader("User-Agent", "spider");
    }
    
}
