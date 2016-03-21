package org.devefx.wx.msg;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;


/**
 * 微信消息
 * @author： youqian.yue
 * @date： 2016-3-7 上午9:44:14
 */
public class ReceiveMsg implements Msg {

	private Long msgId;				// 消息id，64位整型
	private String toUserName;		// 接收方微信号
	private String fromUserName;	// 发送方微信号，若为普通用户，则是一个OpenID
	private Date createTime;		// 消息创建时间
	private String msgType;			// 消息类型
	private String content;			// 文本消息内容
	private String picUrl;			// 图片链接
	private String mediaId;			// 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String format;			// 语音格式，如amr，speex等
	private String recognition;		// 语音识别结果，UTF8编码
	private String thumbMediaId;	// 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	private String location_X;		// 地理位置维度
	private String location_Y;		// 地理位置经度
	private String scale;			// 地图缩放大小
	private String label;			// 地理位置信息
	private String title;			// 消息标题
	private String description;		// 消息描述
	private String url;				// 消息链接
	
	private String event;			// 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	private String eventKey;		// 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	private String ticket;			// 二维码的ticket，可用来换取二维码图片
	private String latitude;		// 地理位置纬度
	private String longitude;		// 地理位置经度
	private String precision;		// 地理位置精度
	
	private String musicURL;		// 音乐链接
	private String hQMusicUrl;		// 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String articleCount;	// 图文消息个数，限制为10条以内
	private List<Article> articles;	// 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	
	private String encrypt;			// 密文，仅在兼容或安全模式下会出现
	/**
	 * 消息id，64位整型
	 * @return Long
	 */
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	/**
	 * 接收方微信号
	 * @return String
	 */
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	/**
	 * 发送方微信号，若为普通用户，则是一个OpenID
	 * @return String
	 */
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	/**
	 * 消息创建时间
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 消息类型
	 * @return String
	 */
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * 文本消息内容
	 * @return String
	 */
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 图片链接
	 * @return String
	 */
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 * @return String
	 */
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	/**
	 * 语音格式，如amr，speex等
	 * @return String
	 */
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * 语音识别结果，UTF8编码
	 * @return String
	 */
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	 * @return String
	 */
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	/**
	 * 地理位置维度
	 * @return String
	 */
	public String getLocation_X() {
		return location_X;
	}
	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}
	/**
	 * 地理位置经度
	 * @return String
	 */
	public String getLocation_Y() {
		return location_Y;
	}
	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}
	/**
	 * 地图缩放大小
	 * @return String
	 */
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	/**
	 * 地理位置信息
	 * @return String
	 */
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * 消息标题
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 消息描述
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 消息链接
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	 * @return String
	 */
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	/**
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 * @return String
	 */
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 * @return String
	 */
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * 地理位置纬度
	 * @return String
	 */
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 地理位置经度
	 * @return String
	 */
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 地理位置精度
	 * @return String
	 */
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	/**
	 * 音乐链接
	 * @return String
	 */
	public String getMusicURL() {
		return musicURL;
	}
	public void setMusicURL(String musicURL) {
		this.musicURL = musicURL;
	}
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 * @return String
	 */
	public String gethQMusicUrl() {
		return hQMusicUrl;
	}
	public void sethQMusicUrl(String hQMusicUrl) {
		this.hQMusicUrl = hQMusicUrl;
	}
	/**
	 * 图文消息个数，限制为10条以内
	 * @return String
	 */
	public String getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}
	/**
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 * @return List<Article>
	 */
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	/**
	 * 密文，仅在兼容或安全模式下会出现
	 * @return String
	 */
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	@Override
	public void output(Writer writer) throws IOException {
		writer.append("<xml>");
		
		line(writer, "MsgId", msgId);
		line(writer, "ToUserName", toUserName);
		line(writer, "FromUserName", fromUserName);
		if (createTime != null) {
			writer.append("<CreateTime>");
			writer.append(String.valueOf(createTime.getTime()));
			writer.append("</CreateTime>");
		}
		line(writer, "MsgType", msgType);
		line(writer, "Content", content);
		line(writer, "PicUrl", picUrl);
		line(writer, "MediaId", mediaId);
		line(writer, "Format", format);
		line(writer, "ThumbMediaId", thumbMediaId);
		line(writer, "Location_X", location_X);
		line(writer, "Location_Y", location_Y);
		line(writer, "Scale", scale);
		line(writer, "Label", label);
		line(writer, "Title", title);
		line(writer, "Description", description);
		line(writer, "Url", url);
		
		line(writer, "Event", event);
		line(writer, "EventKey", eventKey);
		line(writer, "Ticket", ticket);
		line(writer, "Latitude", latitude);
		line(writer, "Longitude", longitude);
		line(writer, "Precision", precision);
		
		line(writer, "MusicURL", musicURL);
		line(writer, "HQMusicUrl", hQMusicUrl);
		line(writer, "ArticleCount", articleCount);
		if (articles != null) {
			writer.append("<Articles>");
			for (Article article : articles) {
				outputArticle(writer, article);
				writer.append(article.toString());
			}
			writer.append("</Articles>");
		}
		
		line(writer, "Encrypt", encrypt);
		
		writer.append("</xml>");
	}
	private void outputArticle(Writer writer, Article article) throws IOException {
		writer.append("<item>");

		line(writer, "Title", article.getTitle());
		line(writer, "Description", article.getDescription());
		line(writer, "PicUrl", article.getPicUrl());
		line(writer, "Url", article.getUrl());
		
		writer.append("</item>");
	}
	private void line(Writer writer, String name, Object value) throws IOException {
		if (value != null) {
			writer.append("<" + name + "><![CDATA[");
			writer.append(String.valueOf(value));
			writer.append("]]></" + name + ">");
		}
	}
}
