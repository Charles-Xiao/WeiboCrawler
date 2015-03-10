
package com.xy.weibocrawler.html;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HtmlClient {
    /**
     * 抓取网页html数据
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String[] getHTML(String url) throws ClientProtocolException, IOException {
        String[] html = new String[2];
        html[1] = "null";
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000)// 设置socket超时时间
                .setConnectTimeout(5000)// 设置connect超时时间
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).setUserAgent("spider").build();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // System.out.println(response.getStatusLine().getStatusCode());
            html[0] = String.valueOf(response.getStatusLine().getStatusCode());
            html[1] = EntityUtils.toString(response.getEntity(), "utf8");
            // System.out.println(html[1]);
        } catch (IOException e) {
            System.out.println("****Connection time out****");
        }

        return html;
    }

    public static String getHTMLByUnit(String url) throws FailingHttpStatusCodeException,
            MalformedURLException, IOException, InterruptedException {
        WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(true); // 禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.addRequestHeader("User-Agent", "spider");
        HtmlPage page = wc.getPage(url);
        //http://bbs.csdn.net/topics/390710871
        //http://blog.csdn.net/zhoujianfeng3/article/details/21395223
        String response = page.getWebResponse().getContentAsString();
        
        return page.asXml();
    }
}
