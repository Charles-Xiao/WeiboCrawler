
package com.xy.weibocrawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.xy.weibocrawler.db.JDBC;
import com.xy.weibocrawler.db.Weibo;
import com.xy.weibocrawler.html.HtmlClient;
import com.xy.weibocrawler.html.HtmlParser;
import com.xy.weibocrawler.utils.AnsjUtils;
import com.xy.weibocrawler.utils.Utils;

import java.util.List;

public class CrawlThread extends Thread {
    private String url;

    private WebClient wc;

    public CrawlThread(String url, WebClient wc) {
        this.url = url;
        this.wc = wc;
    }

    public void run() {
        String html;
        try {
            html = HtmlClient.getHTMLByUnit(wc, url);
            List<Weibo> weibos = HtmlParser.parseWeibo(html);
            AnsjUtils.getKeywordsNum(weibos);
            // url剪枝 --- 如果用户所有原创微博中没有一条主题相关微博，则不再爬取它产生的新url

            // 数据库插入主题相关微博
            String sql = "insert into weiboinfo (name,vip,content,time,fruitnum,winenum,milknum,safetynum,category) values (?,?,?,?,?,?,?,?,?)";
            for (Weibo weibo : weibos) {
                if (JDBC.INSTANCE.dbInsertBySQL(sql, Utils.WeiboToList(weibo))) {
                    System.out.println("weibo插入数据库： " + weibo.toString());
                }

            }
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

//            System.out.println("run() currentThreadName: " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
