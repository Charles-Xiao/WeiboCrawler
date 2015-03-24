
package com.xy.weibocrawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.xy.weibocrawler.db.JDBC;
import com.xy.weibocrawler.utils.AnsjUtils;
import com.xy.weibocrawler.utils.Constants;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Jdk14Logger;
import org.apache.http.client.params.CookiePolicy;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Crawler {
    /** 等待爬取的url队列 */
    public static ConcurrentLinkedQueue<String> waitUrlList = new ConcurrentLinkedQueue<>();

    /** 已经被爬取过的url集合 */
    public static Set<String> crawledUrlSet = new HashSet<>();

    public static void main(String args[]) throws Exception {

        AnsjUtils.userDefineLib();
        org.apache.commons.logging.impl.Jdk14Logger logger = (Jdk14Logger) LogFactory
                .getLog("com.gargoylesoftware.htmlunit");
        logger.getLogger().setLevel(Level.OFF);

        // 初始化等待爬取url队列
        for (String url : Constants.getUrlArr()) {
            waitUrlList.offer(url);
        }
        // 初始化httpunit模块
        WebClient wc = new WebClient();
        initWebClient(wc);

        JDBC.INSTANCE.getDbConnection();
        // 多线程爬取线程池
        ExecutorService pool = Executors.newFixedThreadPool(20);

        while (!waitUrlList.isEmpty() || Thread.activeCount() > 3) {
            if (waitUrlList.peek() != null) {
                crawledUrlSet.add(waitUrlList.peek());
                CrawlThread crawlThread = new CrawlThread(waitUrlList.poll(), wc);
                pool.execute(crawlThread);
                // System.out.println("Thread.activeCount(): " +
                // Thread.activeCount());
            }

        }
        pool.shutdown();
        JDBC.INSTANCE.dbClose();

        // HtmlClient.loginSinaWeibo(wc, Constants.URl_LOGIN);

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
