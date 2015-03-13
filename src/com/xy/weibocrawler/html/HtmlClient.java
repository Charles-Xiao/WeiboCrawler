
package com.xy.weibocrawler.html;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlClient {

    /**
     * 爬取某用户的第一页所有微博
     * 
     * @param url 用户微博入口url
     * @return
     * @throws Exception
     */
    public static String getHTMLByUnit(WebClient wc, String url) throws Exception {

        HtmlPage initPage = wc.getPage(url);
        String domain = getMatcher("domain']='([\\d]+)", initPage.asXml().substring(2000, 5000));  
        String page_id = getMatcher("page_id']='([\\d]+)", initPage.asXml().substring(2000, 5000));
        // http://bbs.csdn.net/topics/390710871
        // http://blog.csdn.net/zhoujianfeng3/article/details/21395223
        
        // 模拟第一次滚动请求
        String firstRollUrl = createRollUrl(domain, "1", "0", page_id);
        WebRequest firstWebReq = new WebRequest(new URL(firstRollUrl), HttpMethod.GET);
        WebResponse firstWebRes = wc.loadWebResponse(firstWebReq);
        JSONObject firstJson = new JSONObject(firstWebRes.getContentAsString());
        String firstData = (String) firstJson.get("data");

        // 模拟第二次滚动请求
        String secRollUrl = createRollUrl(domain, "1", "1", page_id);
        WebRequest secWebReq = new WebRequest(new URL(secRollUrl), HttpMethod.GET);
        WebResponse sectWebRes = wc.loadWebResponse(secWebReq);
        JSONObject secJson = new JSONObject(sectWebRes.getContentAsString());
        String secData = (String) secJson.get("data");

        // 获取分页列表，依次爬取该用户所有页面微博 TODO 模拟登陆之后才能获取到
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
        return initPage.asXml() + firstData + secData;
    }

    private static String createRollUrl(String domainId, String pageNum, String pageBar,
            String userId) {
        return "http://weibo.com/p/aj/v6/mblog/mbloglist?domain=" + domainId + "&pre_page="
                + pageNum + "&page=" + pageNum + "&pagebar=" + pageBar + "&id=" + userId;
    }
    
    /**
     * 正则表达式查找字符串
     * @param regex
     * @param source
     * @return
     */
    private static String getMatcher(String regex, String source) {  
        String result = "";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(source);  
        while (matcher.find()) {  
            result = matcher.group(1);//只取第一组  
        }  
        return result;  
    } 

    // 模拟登陆新浪微博 http://blog.csdn.net/bob007/article/details/29589059
    // http://www.douban.com/note/264976536/?type=like
    public static boolean loginSinaWeibo(WebClient wc, String url) throws Exception {
        HtmlPage page = wc.getPage(url);
//         System.out.println(page.asText());
        // 登录
        HtmlInput ln = page.getHtmlElementById("username");
        HtmlInput pwd = page.getHtmlElementById("password");
        HtmlInput btn = page.getFirstByXPath(".//*[@id='vForm']/div[3]/ul/li[6]/div[2]/input");
        ln.setAttribute("value", "931017xy@sina.com");
        pwd.setAttribute("value", "10171993xy");
        HtmlPage page2 = btn.click();
        // 登录完成，现在可以爬取任意你想要的页面了。
        System.out.println("loginSinaWeibo: " + btn.toString());
         System.out.println(page2.asText());

        HtmlPage page3 = wc.getPage("http://weibo.com/friends?leftnav=1&wvr=5&isfriends=1&step=2");
        System.out.println("新浪微博好友圈页面: " + page3.asXml());
        return false;

    }

}
