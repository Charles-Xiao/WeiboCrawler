package com.xy.weibocrawler;

import java.io.IOException;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.http.client.ClientProtocolException;

import com.xy.weibocrawler.html.HtmlClient;
import com.xy.weibocrawler.html.HtmlParser;
import com.xy.weibocrawler.utils.Constants;

public class Crawler {
	public static void main(String args[]) throws InterruptedException {
		
		String str = "果蔬蔬菜白菜青菜白萝卜萝卜梨子食品安全过期变质超标色素健康有害有利有损致癌标准监管掺假防腐剂质量问题";
		List<Term> parseResult = ToAnalysis.parse(str);
		System.out.println(parseResult + "\n");
		System.out.println(parseResult.get(1).getRealName());
		System.out.println(parseResult.get(1).getName());
		System.out.println(parseResult.get(1).getNatureStr());//获得词性
		System.out.println(parseResult.get(1).getNext());
		System.out.println(parseResult.get(1).getOffe());
		System.out.println(parseResult.get(1).getSubTerm());
		
		//JDBC.INSTANCE.getDbConnection();
		
		try {
//			String html = HtmlClient.getHTML(Constants.URl_TEST)[1];
		    String html = HtmlClient.getHTMLByUnit(Constants.URl_TEST);
		    System.out.println(html);
			System.out.println(HtmlParser.parseUserName(html));
			HtmlParser.parseWeibo(html);
			HtmlParser.parseUrls(html);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
