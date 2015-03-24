
package com.xy.weibocrawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.xy.weibocrawler.db.JDBC;
import com.xy.weibocrawler.db.Weibo;
import com.xy.weibocrawler.html.HtmlClient;
import com.xy.weibocrawler.html.HtmlParser;
import com.xy.weibocrawler.utils.AnsjUtils;
import com.xy.weibocrawler.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.http.client.params.CookiePolicy;

import java.util.List;

public class CrawlThread extends Thread {
    private String url;

    public CrawlThread(String url) {
        this.url = url;
    }

    public void run() {
        String html;
        try {
            // 初始化httpunit模块
            WebClient wc = new WebClient();
            initWebClient(wc);
            html = HtmlClient.getHTMLByUnit(wc, url);
            List<Weibo> weibos = HtmlParser.parseWeibo(html);
            AnsjUtils.getKeywordsNum(weibos);

            // 数据库插入主题相关微博
            String sql = "insert into weiboinfo (name,vip,content,time,fruitnum,winenum,milknum,safetynum,category) values (?,?,?,?,?,?,?,?,?)";
            for (Weibo weibo : weibos) {
                if (JDBC.INSTANCE.dbInsertBySQL(sql, Utils.WeiboToList(weibo))) {
                    System.out.println("weibo插入数据库： " + weibo.toString());
                }

            }
            // url剪枝 --- 如果用户所有原创微博中没有一条主题相关微博，则不再爬取它产生的新url
            if (weibos.size() > 0) {
                List<String> urls = HtmlParser.parseUrls(html);
                // 去除重复和已经爬取过的url,有效url加入waitUrlList
                for (String newUrl : urls) {
                    if (Crawler.crawledUrlSet.contains(newUrl)
                            || Crawler.waitUrlList.contains(newUrl)) {
                        continue;
                    } else {
                        Crawler.waitUrlList.add(newUrl);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("deprecation")
    private static void initWebClient(WebClient wc) {
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); // 禁用css支持
        // wc.setAjaxController(new NicelyResynchronizingAjaxController());
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setTimeout(5000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.setJavaScriptTimeout(5000);
        wc.addRequestHeader("User-Agent", "spider");
        System.setProperty("apache.commons.httpclient.cookiespec",
                CookiePolicy.BROWSER_COMPATIBILITY);
        // wc.getCookieManager().setCookiesEnabled(true);
        
        // 读取cookies添加到header
        // http://changfengmingzhi.blog.163.com/blog/static/16710528820136255217235/
        // http://ksblog.org/index.php?q=htmlunit-cookies-handling&id=49
        // String[] cookiesKvs = cookiesStr.split("; ");
        // for (String str : cookiesKvs) {
        // String[] kvs = str.split("=");
        // wc.getCookieManager().addCookie(new Cookie(Constants.URl_TEST,
        // kvs[0], kvs[1]));
        // }

    }
}
