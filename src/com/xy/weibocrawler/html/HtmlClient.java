package com.xy.weibocrawler.html;

import java.io.IOException;
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
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String[] getHTML(String url) throws ClientProtocolException,
			IOException {
		String[] html = new String[2];
		html[1] = "null";
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(5000)// 设置socket超时时间
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
//			 System.out.println(html[1]);
		} catch (IOException e) {
			System.out.println("****Connection time out****");
		}

		return html;
	}
}
