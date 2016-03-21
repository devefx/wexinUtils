package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import org.devefx.wx.msg.Article;

/**
 * 图文消息（点击跳转到外链） 
 * @author： youqian.yue
 * @date： 2016-3-9 下午4:38:27
 */
public class NewsAsynMsg extends AsynMsg {
	
	private List<Article> articles = new LinkedList<Article>();
	
	public List<Article> getArticles() {
		return articles;
	}
	@Override
	public String getMsgType() {
		return "news";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		writer.append(QUOT);
		writer.append("articles");
		writer.append(QUOT);
		writer.append(":[");
		for (int i = 0, n = articles.size(); i < n; i++) {
			outputArticle(writer, articles.get(i));
			if (i + 1 < n) {
				writer.append(COMMA);
			}
		}
		writer.append("]");
	}
	private void outputArticle(Writer writer, Article article) throws IOException {
		writer.append(BRACE_L);
		line(writer, "title", article.getTitle(), COMMA);
		line(writer, "description", article.getDescription(), COMMA);
		line(writer, "url", article.getUrl(), COMMA);
		line(writer, "picurl", article.getPicUrl(), null);
		writer.append(BRACE_R);
	}
}
