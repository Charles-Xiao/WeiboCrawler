
package com.xy.weibocrawler.html;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
     * 爬取一个html页面中的所有微博信息
     * 
     * @param html
     */
    public static void parseWeibo(String html) {
        Elements weiboDetails = null;
        try {
            Document doc = Jsoup.parse(html);
            weiboDetails = doc.getElementsByClass("WB_detail");
            // 判断页面对应用户是否是认证用户
            Element vip = doc.select("a.icon_bed > em").first();
            if (vip != null && vip.attr("class").equals("W_icon icon_pf_approve")) {
                System.out.println("vip用户");
            } else {
                System.out.println("非vip用户");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Element element : weiboDetails) {
            String weiboDetailHtml = element.html();
            // System.out.println(weiboDetailHtml);
            Document docDetail = Jsoup.parse(weiboDetailHtml);
            Elements divs = docDetail.getElementsByTag("div");
            String nickName = divs.first().attr("nick-name");
            if (nickName.equals("")) {
                System.out.println("转发的微博");
            } else {
                System.out.println(nickName);
            }
            // 提取微博原创内容
            String content = divs.first().text();
            System.out.println(content);
            // 提取微博发布时间
            Elements timeElements = docDetail.select("a[node-type=feed_list_item_date]");
            String time = timeElements.last().attr("title");
            System.out.println(time + "\r\n");
            // TODO 爬取用户转发的微博的内容div.WB_feed_expand
        }
    }

    public static List<String> parseUrls(String html) {
        List<String> urlList = new ArrayList<>();
        final String baseUrl = "http://weibo.com";
        Elements urls = null;
        // TODO 新浪微博异步加载会导致无法加载到下一页按钮以及一页只能读取10条左右的微博 模拟下拉滚动条操作
        try {
            Document doc = Jsoup.parse(html);
            urls = doc.getElementsByClass("page next S_txt1 S_line1");
            if (urls.size() > 0 && urls.last().text().equals("下一页")) {
                String nextUrl = baseUrl + urls.last().attr("href");
                urlList.add(nextUrl);
                System.out.println(nextUrl);
            }
            System.out.println("nextUrl: " + urls.size());

            // 获取转发的微博的原创用户链接
            urls = doc.select("a[node-type=feed_list_originNick]");
            for (Element element : urls) {
                    urlList.add(baseUrl + element.attr("href"));
                    System.out.println(baseUrl + element.attr("href"));
            }
            //获取新浪微博提供的与该用户相似的用户微博链接 
            Element div = doc.select("div.PCD_ut_a").first();
            Document divDoc = Jsoup.parse(div.html());
            urls = divDoc.select("a[class=S_txt1]");
            for (Element element : urls) {
            	urlList.add(element.attr("href"));
                System.out.println(element.attr("href"));
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("urlList size： " + urlList.size());
        return urlList;
    }
}
