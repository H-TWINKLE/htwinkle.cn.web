package com.twinkle.utils;

public interface Constant {

	public final static String JavaPath = "ProgramFile";
	public final static String JavaProperty = "java.home";
	public final static String basePath = "E:\\TWINKLE\\Desktop\\demo";
	public final static String imgUrl = "http://jwgl.cdnu.edu.cn/CheckCode.aspx";
	public final static String demoImg = "1.png";
	public final static String demoPath = "E:\\TWINKLE\\Desktop\\demo";
	public final static String tomcatPath = "C:\\Users\\Administrator\\Pictures\\demo";
	public final static String jwglHome = "http://jwgl.cdnu.edu.cn/default5.aspx";
	public final static String jwglIndex = "http://jwgl.cdnu.edu.cn/xs_main.aspx?xh=";
	public final static String RadioButtonList1 = "学生";
	public final static String Button1 = "按学期查询";
	public final static String orcPath = "E:\\MyMajor\\myeclipse\\Tess4J\\tessdata";
	public final static String orcTomcatPath = "C:\\Users\\Administrator\\Documents\\ocr\\tessdata";
	
	public final static String eolHost = "http://eol.cdnu.edu.cn";
	public final static String eolReferer = "http://eol.cdnu.edu.cn/eol/homepage/common/";
	public final static String eolLogin = "http://eol.cdnu.edu.cn/eol/loginCheck.do";  //yes
	public final static String eolindex = "http://eol.cdnu.edu.cn/eol/homepage/common/index.jsp";

	/**登录失败，用户或者密码错误，或者验证码错误*/
	public final static int FAILURE = 10001; 
	
	/**登录成功*/
	public final static int SUCCESS = 10000; 
	
	/**网络异常 内部异常*/
	public final static int NETFAILURE = 10002; 
	
	/**host错误 服务器不存在*/
	public final static int SERVERERROR = 10003; 
	
	/**没有进行系统评价*/
	public final static int NEVERTOENAL = 10004; 
	
	/**验证码为空*/
	public final static int CODEIMGERROR = 10005;
	
	/** string 服务器错误 */
	public final static String FAILURESERVER = "服务器错误";
	
	/**作业全部完成*/
	public final static int WORK_COMPLETE = 10006; 

	public final static String eolStudentIndex = "http://eol.cdnu.edu.cn/eol/personal.do";  //yes
	public final static String eolTask = "http://eol.cdnu.edu.cn/eol/welcomepage/student/interaction_reminder_v8.jsp"; //yes

	public final static String NetMusicUrl = "http://music.163.com/discover/toplist?id=3778678";
	public final static String NetMusicCommUrl = "http://music.163.com/api/v1/resource/comments/";

	public final static String g3BiZhiUrl = "http://sj.zol.com.cn/bizhi/";

	public final static String meiRiYiWenUrl = "https://meiriyiwen.com/random";

	public static final String APP_ID = "11692607"; // 百度orc sdk
	public static final String API_KEY = "OoKiNeBN2YBc0l2W2T0NI83P";
	public static final String SECRET_KEY = "OWrrF6mpueItSb16RPvpBH222LDydriu";

	public static final String LOCALIP = "0:0:0:0:0:0:0:1";

	public static final String CDNUEDUCNNOTICES = "http://www.cdnu.edu.cn/channels/1433";

	public static final String HTML = ".html";

	public static final String CDNUWEIBOLIFESHARE = "kxz2BBBY"; // 成都师范学院生活分享

	public static final String CDNUWEIBOYISHI = "eBt1RRRM"; // 成都师范轶事

	public static final String CDNUNEWS = "n2YxBBBk"; // 成都师范学院官方新闻

	public static final String CDNUTIEBASHARE = "VtYf666P"; // 成都师范学院贴吧吐槽

	public static final String CDNU = "http://www.cdnu.edu.cn";

	public static final String NEWS = "[新闻公告]";
	
	public static final String YISHI = "[成师轶事]";
	
	public static final String SHARE = "[生活分享]";
	
	public static final String TIEBASHARE = "[贴吧吐槽]";

	public static final String TEXT = "1";
	public static final String IMG_TEXT = "2";
	public static final String ONE_ING_TEXT = "3";

	public static final String X_Bmob_Application_Id = "efcec7fdecd3aefe199792559b33bf1b";

	public static final String X_Bmob_REST_API_Key = "2f6e22943cb0acd32ca6c5d0dfa07b08";

	public static final String BmobAddPostUrl = "https://api2.bmob.cn/1/classes/Post";
	
	public static final String WEIBOJSON = "https://m.weibo.cn/api/container/getIndex?containerid=";

	public static final String WEIBOJSONBYYISHI = "1076033700687004&page=";

	public static final String WEIBOJSONBYCDNU = "1076033963799894&page=";
	
	public static final String CDNUTIEBA = "成都师范学院";
	
	public static final String TieBaUrl = "https://tieba.baidu.com/f?kw=";
	
	public static final String TieBa = "https://tieba.baidu.com";
	
	
}
