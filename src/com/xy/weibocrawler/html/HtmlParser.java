
package com.xy.weibocrawler.html;

import com.xy.weibocrawler.db.Weibo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HtmlParser {

    /**
     * 从一个html页面数据中parse用户昵称
     * 
     * @param html
     * @return
     */
    public static String parseUserName(String html) {
        Elements username = null;
        try {
            Document doc = Jsoup.parse(html);
            username = doc.getElementsByTag("title");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 字符串处理，截去"的微博_微博"
        String result = username.get(0).text();
        result = result.substring(0, result.length() - 6);
        return result;

    }

    /**
     * 爬取一个html页面中的所有原创微博内容
     * 
     * @param html
     */
    public synchronized static CopyOnWriteArrayList<Weibo> parseWeibo(String html) {
        CopyOnWriteArrayList<Weibo> weibos = new CopyOnWriteArrayList<>();
        boolean isVip = false;
        Elements weiboDetails = null;
        Document doc = Jsoup.parse(html);
        weiboDetails = doc.getElementsByClass("WB_detail");
        // 判断页面对应用户是否是认证用户
        Element pfPhoto = doc.getElementsByClass("pf_photo").first();
        if (pfPhoto != null) {
            Document pfDoc = Jsoup.parse(pfPhoto.outerHtml());
            Element vip = pfDoc.select("a.icon_bed > em").first();
            if (vip != null) {
                if (vip.attr("class").equals("W_icon icon_pf_approve")) {
                    isVip = true;
                }
                if (vip.attr("class").equals("W_icon icon_pf_approve_co")) {
                    isVip = true;
                }
            }
        }

        for (Element element : weiboDetails) {
            String weiboDetailHtml = element.html();
            Document docDetail = Jsoup.parse(weiboDetailHtml);
            Elements divs = docDetail.getElementsByTag("div");
            if (divs.first() != null) {
                String nickName = divs.first().attr("nick-name");
                if (!nickName.equals("")) {
                    // 提取微博原创内容
                    // System.out.println(nickName + "的原创微博");
                    String content = divs.first().text();
                    // System.out.println(content);
                    // 提取微博发布时间
                    Elements timeElements = docDetail.select("a[node-type=feed_list_item_date]");
                    String time = null;
                    if (timeElements != null && timeElements.size() > 0) {
                        time = timeElements.last().attr("title");
                    }
                    // System.out.println(time + "\r\n");
                    weibos.add(new Weibo(nickName, isVip, content, time, 0, 0, 0, 0, null));
                }
            }

        }
        return weibos;
    }

    public static List<String> parseUrls(String html) {
        List<String> urlList = new ArrayList<>();
        final String baseUrl = "http://weibo.com";
        Elements urls = null;
        try {

            Document doc = Jsoup.parse(html);
            urls = doc.getElementsByClass("page next S_txt1 S_line1");

            // 获取转发的微博的原创用户链接
            urls = doc.select("a[node-type=feed_list_originNick]");
            for (Element element : urls) {
                urlList.add(baseUrl + element.attr("href"));
                // System.out.println(baseUrl + element.attr("href"));
            }
            // 获取新浪微博提供的与该用户相似的用户微博链接
            Element div = doc.select("div.PCD_ut_a").first();
            if (div != null) {
                Document divDoc = Jsoup.parse(div.html());
                urls = divDoc.select("a[class=S_txt1]");
                for (Element element : urls) {
                    String similarUrl = element.attr("href");
                    String indexUrl = similarUrl.split("\\?")[0];
                    urlList.add(indexUrl);
                    // System.out.println("相似用户url: " + indexUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("爬取到的新urlList size： " + urlList.size());
        return urlList;
    }
}
