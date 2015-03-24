
package com.xy.weibocrawler;

import com.xy.weibocrawler.db.JDBC;
import com.xy.weibocrawler.utils.AnsjUtils;
import com.xy.weibocrawler.utils.Constants;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Jdk14Logger;

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

        JDBC.INSTANCE.getDbConnection();
        // 多线程爬取线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        while (!waitUrlList.isEmpty() || Thread.activeCount() > 3) {
            if (waitUrlList.peek() != null) {
                crawledUrlSet.add(waitUrlList.peek());
                CrawlThread crawlThread = new CrawlThread(waitUrlList.poll());
                pool.execute(crawlThread);
                // System.out.println("Thread.activeCount(): " +
                // Thread.activeCount());
            }

        }
        pool.shutdown();
        JDBC.INSTANCE.dbClose();

        // HtmlClient.loginSinaWeibo(wc, Constants.URl_LOGIN);

    }

}
