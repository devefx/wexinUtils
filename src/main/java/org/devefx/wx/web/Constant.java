package org.devefx.wx.web;

/**
 * 常量
 * @author： youqian.yue
 * @date： 2016-3-9 上午10:41:50
 */
public class Constant {
	/** 文本消息 */
	public static final String MSG_TEXT = "text";
	/** 图片消息 */
	public static final String MSG_IMAGE = "image";
	/** 语音消息 */
	public static final String MSG_VOICE = "voice";
	/** 视频消息 */
	public static final String MSG_VIDEO = "video";
	/** 小视频消息 */
	public static final String MSG_SHORTVIDEO = "shortvideo";
	/** 地理位置消息 */
	public static final String MSG_LOCATION = "location";
	/** 链接消息 */
	public static final String MSG_LINK = "link";
	/** 事件推送消息 */
	public static final String MSG_EVENT = "event";
	
	/** 发送音乐消息 */
	public static final String MSG_MUSIC = "music";
	/** 发送图文消息（点击跳转到外链） */
	public static final String MSG_NEWS = "news";
	/** 发送图文消息（点击跳转到图文消息页面） */
	public static final String MSG_MPNEWS = "mpnews";
	/** 发送卡券 */
	public static final String MSG_WXCARD = "wxcard";
	
	/** 关注事件 */
	public static final String EVENT_SUBSCRIBE = "subscribe";
	/** 取消关注事件 */
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	/** 用户已关注时的事件推送 */
	public static final String EVENT_SCAN = "SCAN";
	/** 上报地理位置事件 */
	public static final String EVENT_LOCATION = "LOCATION";
	/** 自定义菜单事件 */
	public static final String EVENT_CLICK = "CLICK";
	/** 点击菜单跳转链接时的事件推送 */
	public static final String EVENT_VIEW = "VIEW";
	
	
	
	/** 支付成功 */
	public static final String SUCCESS = "SUCCESS";
	/** 商户无此接口权限 */
	public static final String NOAUTH = "NOAUTH";
	/** 余额不足 */
	public static final String NOTENOUGH = "NOTENOUGH";
	/** 商户订单已支付 */
	public static final String ORDERPAID = "ORDERPAID";
	/** 订单已关闭 */
	public static final String ORDERCLOSED = "ORDERCLOSED";
	/** 系统错误 */
	public static final String SYSTEMERROR = "SYSTEMERROR";
	/** APPID不存在 */
	public static final String APPID_NOT_EXIST = "APPID_NOT_EXIST";
	/** MCHID不存在 */
	public static final String MCHID_NOT_EXIST = "MCHID_NOT_EXIST";
	/** appid和mch_id不匹配 */
	public static final String APPID_MCHID_NOT_MATCH = "APPID_MCHID_NOT_MATCH";
	/** 缺少参数 */
	public static final String LACK_PARAMS = "LACK_PARAMS";
	/** 商户订单号重复 */
	public static final String OUT_TRADE_NO_USED = "OUT_TRADE_NO_USED";
	/** 签名错误 */
	public static final String SIGNERROR = "SIGNERROR";
	/** XML格式错误 */
	public static final String XML_FORMAT_ERROR = "XML_FORMAT_ERROR";
	/** 请使用post方法 */
	public static final String REQUIRE_POST_METHOD = "REQUIRE_POST_METHOD";
	/** post数据为空 */
	public static final String POST_DATA_EMPTY = "POST_DATA_EMPTY";
	/** 编码格式错误 */
	public static final String NOT_UTF8 = "NOT_UTF8";
}
