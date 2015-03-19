
package com.xy.weibocrawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.xy.weibocrawler.db.JDBC;
import com.xy.weibocrawler.db.Weibo;
import com.xy.weibocrawler.utils.AnsjUtils;
import com.xy.weibocrawler.utils.Constants;
import com.xy.weibocrawler.utils.Utils;

import org.apache.http.client.params.CookiePolicy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
    /** 等待爬取的url队列 */
    public static ConcurrentLinkedQueue<String> waitUrlList = new ConcurrentLinkedQueue<>();

    /** 已经被爬取过的url集合 */
    public static Set<String> crawledUrlSet = new HashSet<>();

    public static void main(String args[]) throws Exception {

         AnsjUtils.testAnsj();
        // 初始化等待爬取url队列
//        for (String url : Constants.getUrlArr()) {
//            waitUrlList.offer(url);
//        }
//        // 初始化httpunit模块
//        WebClient wc = new WebClient();
//        initWebClient(wc);
//
//        JDBC.INSTANCE.getDbConnection();
//        // 多线程爬取线程池
//        ExecutorService pool = Executors.newFixedThreadPool(50);
//
//        while (!waitUrlList.isEmpty() || Thread.activeCount() > 3) {
//            if (waitUrlList.peek() != null) {
//                crawledUrlSet.add(waitUrlList.peek());
//                CrawlThread crawlThread = new CrawlThread(waitUrlList.poll(), wc);
//                pool.execute(crawlThread);
//                System.out.println("Thread.activeCount(): " + Thread.activeCount());
//            }
//
//        }
//        pool.shutdown();
//        JDBC.INSTANCE.dbClose();

        // HtmlClient.loginSinaWeibo(wc, Constants.URl_LOGIN);

    }

    private static void initWebClient(WebClient wc) {
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); // 禁用css支持
        // wc.setAjaxController(new NicelyResynchronizingAjaxController());
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setTimeout(5000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.addRequestHeader("User-Agent", "spider");
        System.setProperty("apache.commons.httpclient.cookiespec",
                CookiePolicy.BROWSER_COMPATIBILITY);
        // 读取cookies添加到header
        // http://changfengmingzhi.blog.163.com/blog/static/16710528820136255217235/
        // http://ksblog.org/index.php?q=htmlunit-cookies-handling&id=49
        wc.getCookieManager().setCookiesEnabled(true);
        String cookiesStr = "SINAGLOBAL=5392184734810.144.1417057472710; TC-Ugrow-G0=e66b2e50a7e7f417f6cc12eec600f517; TC-V5-G0=5fc1edb622413480f88ccd36a41ee587; _s_tentry=login.sina.com.cn; Apache=6281214079353.958.1426235748213; ULV=1426235748303:78:13:10:6281214079353.958.1426235748213:1426233128832; TC-Page-G0=9183dd4bc08eff0c7e422b0d2f4eeaec; login_sid_t=b20777dffa435b6a0e860516bf908f37; YF-Page-G0=f0e89c46e7ea678e9f91d029ec552e92; YF-V5-G0=c072c6ac12a0526ff9af4f0716396363; YF-Ugrow-G0=98bd50ad2e44607f8f0afd849e67c645; WBtopGlobal_register_version=50a1e1c073454fe6; myuid=2262300105; UOR=,,login.sina.com.cn; SUS=SID-2262300105-1426243093-JA-j5kn8-fa6ae247fe63ae7a5ed2041f57064a1e; SUE=es%3D2d384dc1f97d0151b32b3e2ba8b053e9%26ev%3Dv1%26es2%3Db0d2460e59eddf78ba22366e61a2cb58%26rs0%3D2bWtT3JAI83Cpp%252BL5ALHaQJaYlHTs97V5D84YoGYC0Sghu4fAaSH4ZXM83RvdY3axF%252Fn1vUGnV4HF0PEzCiZgoehpz34PdIicIjhqhST9FhoGznaSt%252B7gS84mBZ9j4mN90nvdH2B5BJ2lDS95ksDvkRK%252F8AyLdqLt3y5F%252B4lIVA%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1426243093%26et%3D1426329493%26d%3Dc909%26i%3D4a1e%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D2262300105%26name%3D931017xy%2540sina.com%26nick%3DCharles_Xiao%26fmp%3D%26lcp%3D2011-12-30%252015%253A24%253A58; SUB=_2A254Bs5FDeTxGeRM7VAS8C7NyzmIHXVbdbiNrDV8PUNbuNAMLUrRkW8h8XasHpuEIwGuKGeJskFjVWsLng..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWxiUSIj8l5IOGeOUbquPLl5JpX5K2t; SUHB=0KAg5ZfShfRSzI; ALF=1457779093; SSOLoginState=1426243093; un=931017xy@sina.com; wvr=6";
        String[] cookiesKvs = cookiesStr.split("; ");
        for (String str : cookiesKvs) {
            String[] kvs = str.split("=");
            wc.getCookieManager().addCookie(new Cookie(Constants.URl_TEST, kvs[0], kvs[1]));
            // System.out.println(kvs[0] + "   " + kvs[1]);
        }
        // wc.getCookieManager()
        // .addCookie(
        // new Cookie(Constants.URl_INDEX, "SUB",
        // "_2A254B5k2DeTxGeRM7VAS8C7NyzmIHXVbdI3-rDV8PUNbvtAPLUPSkW-Bj4LyLB-XH8D1dy1Y284opQl6Zg.."));
        // System.out.println(wc.getCookieManager().getCookies().toString());

    }

}
