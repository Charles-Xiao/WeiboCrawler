
package com.xy.weibocrawler.html;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class HtmlClient {

    /**
     * 爬取某用户的所有微博
     * 
     * @param url 用户微博入口url
     * @return
     * @throws Exception
     */
    public static String getHTMLByUnit(String url) throws Exception {
        WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); // 禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.addRequestHeader("User-Agent", "spider");
        HtmlPage initPage = wc.getPage(url);
        // http://bbs.csdn.net/topics/390710871
        // http://blog.csdn.net/zhoujianfeng3/article/details/21395223
        // 模拟第一次滚动请求
        String firstRollUrl = createRollUrl("100505", "1", "0", "1005052262300105");
        WebRequest firstWebReq = new WebRequest(new URL(firstRollUrl), HttpMethod.GET);
        WebResponse firstWebRes = wc.loadWebResponse(firstWebReq);
        JSONObject firstJson = new JSONObject(firstWebRes.getContentAsString());
        String firstData = (String) firstJson.get("data");

        // 模拟第二次滚动请求
        String secRollUrl = createRollUrl("100505", "1", "1", "1005052262300105");
        WebRequest secWebReq = new WebRequest(new URL(secRollUrl), HttpMethod.GET);
        WebResponse sectWebRes = wc.loadWebResponse(secWebReq);
        JSONObject secJson = new JSONObject(sectWebRes.getContentAsString());
        String secData = (String) secJson.get("data");
        HtmlParser.parseWeibo(firstData);
        HtmlParser.parseWeibo(secData);

        // 获取分页列表，依次爬取该用户所有页面微博
        Document secDoc = Jsoup.parse(secData);
        Elements pages = secDoc.select("div.W_pages > span.list > div a");
        System.out.print("微博页数： " + pages.size());
        if (pages != null && pages.size() > 0) {
            for (int i = pages.size() - 2; i >= 0; i--) {
                Element page = pages.get(i);
                String pageHref = page.attr("href");
                // TODO 存储获取的url
                System.out.print("href: " + pageHref);
            }
        }
        return initPage.asXml();
    }

    private static String createRollUrl(String domainId, String pageNum, String pageBar,
            String userId) {
        return "http://weibo.com/p/aj/v6/mblog/mbloglist?domain=" + domainId + "&pre_page="
                + pageNum + "&page=" + pageNum + "&pagebar=" + pageBar + "&id=" + userId;
    }

    // 模拟登陆新浪微博 http://blog.csdn.net/bob007/article/details/29589059
    // http://www.douban.com/note/264976536/?type=like
    public static boolean loginSinaWeibo() {

        return false;

    }

}
