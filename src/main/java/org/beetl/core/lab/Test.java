package org.beetl.core.lab;


import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * http://sports.qq.com/a/20151126/029300.htm
 * 
 * @author xiandafu
 *
 */
public class Test {
	/**

	 dfd
	 df
	 df
	 fdf
	 dsfdsf

	 @param args
	 @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("lab/");
		Configuration cfg = Configuration.defaultConfiguration();

		cfg.setDirectByteOutput(true);
		cfg.getResourceMap().put("tagRoot", "");
		cfg.getPkgList().add("org.beetl.core.lab.");
		cfg.setStatementStart("<%");
		cfg.setStatementEnd("%>");
		// js 里专用
//		cfg.setStatementStart2("//#");
//		cfg.setStatementEnd2(null);
//		cfg.setPlaceholderStart2("#");
//		cfg.setPlaceholderEnd2("#");
		cfg.initOther();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		gt.registerTag("table",TableTag.class);
		gt.registerTag("col",ColTag.class);
		TestUser user = new TestUser("a");
		TestUser older = new TestUser("b");


		List list = new ArrayList();
		list.add(1);
		list.add(2);
		Iterator it = list.iterator();



//		user.set("older",older);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {

			Template t = gt.getTemplate("/hello.txt");
			t.binding("json",json);
//			t.binding("user",new TestUser("abc"));
//			t.binding("title","hello");
			String str = t.render();
			System.out.println(str);


		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}


	static String json = "[{\"classifyid\":\"0A70\",\"classifyname\":\"马克思、恩格斯、列宁、斯大林、毛泽东、邓小平生平和传记\",\"clevel\":2,\"id\":8236," +
			"\"inserttime\":1559280038443,\"pid\":8228,\"updatetime\":1559280038443},{\"classifyid\":\"0A80\"," +
			"\"classifyname\":\"马克思主义、列宁主义、毛泽东思想、邓小平理论的学习和研究\",\"clevel\":2,\"id\":8244,\"inserttime\":1559280038443,\"pid\":8228," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0B84\",\"classifyname\":\"心理学\",\"clevel\":2,\"id\":8348," +
			"\"inserttime\":1559280038443,\"pid\":8263,\"updatetime\":1559280038443},{\"classifyid\":\"0C50\"," +
			"\"classifyname\":\"社会科学丛书、文集、连续性出版物\",\"clevel\":2,\"id\":8398,\"inserttime\":1559280038443,\"pid\":8391," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0C91\",\"classifyname\":\"社会学\",\"clevel\":2,\"id\":8410," +
			"\"inserttime\":1559280038443,\"pid\":8391,\"updatetime\":1559280038443},{\"classifyid\":\"0C93\",\"classifyname\":\"管理学\"," +
			"\"clevel\":2,\"id\":8443,\"inserttime\":1559280038443,\"pid\":8391,\"updatetime\":1559280038443},{\"classifyid\":\"0D00\"," +
			"\"classifyname\":\"总论\",\"clevel\":2,\"id\":8473,\"inserttime\":1559280038443,\"pid\":8472,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0D01\",\"classifyname\":\"政治理论\",\"clevel\":2,\"id\":8474,\"inserttime\":1559280038443,\"pid\":8472," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0D10\",\"classifyname\":\"国际共产主义运动\",\"clevel\":2,\"id\":8493," +
			"\"inserttime\":1559280038443,\"pid\":8472,\"updatetime\":1559280038443},{\"classifyid\":\"0D20\",\"classifyname\":\"中国共产党\"," +
			"\"clevel\":2,\"id\":8501,\"inserttime\":1559280038443,\"pid\":8472,\"updatetime\":1559280038443},{\"classifyid\":\"0D60\"," +
			"\"classifyname\":\"中国政治\",\"clevel\":2,\"id\":8548,\"inserttime\":1559280038443,\"pid\":8472,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0D80\",\"classifyname\":\"外交、国际关系\",\"clevel\":2,\"id\":8614,\"inserttime\":1559280038443,\"pid\":8472," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0D90\",\"classifyname\":\"法律\",\"clevel\":2,\"id\":8639," +
			"\"inserttime\":1559280038443,\"pid\":8472,\"updatetime\":1559280038443},{\"classifyid\":\"0E10\",\"classifyname\":\"世界军事\"," +
			"\"clevel\":2,\"id\":8772,\"inserttime\":1559280038443,\"pid\":8769,\"updatetime\":1559280038443},{\"classifyid\":\"0E20\"," +
			"\"classifyname\":\"中国军事\",\"clevel\":2,\"id\":8782,\"inserttime\":1559280038443,\"pid\":8769,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0F01\",\"classifyname\":\"经济学\",\"clevel\":2,\"id\":8833,\"inserttime\":1559280038443,\"pid\":8824," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0F10\",\"classifyname\":\"世界各国经济概况、经济史、经济地理\",\"clevel\":2,\"id\":8905," +
			"\"inserttime\":1559280038443,\"pid\":8824,\"updatetime\":1559280038443},{\"classifyid\":\"0F20\"," +
			"\"classifyname\":\"经济计划与管理\",\"clevel\":2,\"id\":8981,\"inserttime\":1559280038443,\"pid\":8824," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0F40\",\"classifyname\":\"工业经济\",\"clevel\":2,\"id\":9152," +
			"\"inserttime\":1559280038443,\"pid\":8824,\"updatetime\":1559280038443},{\"classifyid\":\"0F50\"," +
			"\"classifyname\":\"交通运输经济\",\"clevel\":2,\"id\":9207,\"inserttime\":1559280038443,\"pid\":8824," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0F70\",\"classifyname\":\"贸易经济\",\"clevel\":2,\"id\":9228," +
			"\"inserttime\":1559280038443,\"pid\":8824,\"updatetime\":1559280038443},{\"classifyid\":\"0F80\",\"classifyname\":\"财政、金融\"," +
			"\"clevel\":2,\"id\":9317,\"inserttime\":1559280038443,\"pid\":8824,\"updatetime\":1559280038443},{\"classifyid\":\"0G10\"," +
			"\"classifyname\":\"世界各国文化与文化事业\",\"clevel\":2,\"id\":9411,\"inserttime\":1559280038443,\"pid\":9408," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0G20\",\"classifyname\":\"信息与知识传播\",\"clevel\":2,\"id\":9416," +
			"\"inserttime\":1559280038443,\"pid\":9408,\"updatetime\":1559280038443},{\"classifyid\":\"0G30\"," +
			"\"classifyname\":\"科学、科学研究\",\"clevel\":2,\"id\":9454,\"inserttime\":1559280038443,\"pid\":9408," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0G40\",\"classifyname\":\"教育\",\"clevel\":2,\"id\":9468," +
			"\"inserttime\":1559280038443,\"pid\":9408,\"updatetime\":1559280038443},{\"classifyid\":\"0H30\",\"classifyname\":\"常用外国语\"," +
			"\"clevel\":2,\"id\":9705,\"inserttime\":1559280038443,\"pid\":9645,\"updatetime\":1559280038443},{\"classifyid\":\"0I10\"," +
			"\"classifyname\":\"世界文学\",\"clevel\":2,\"id\":9782,\"inserttime\":1559280038443,\"pid\":9760,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0I20\",\"classifyname\":\"中国文学\",\"clevel\":2,\"id\":9802,\"inserttime\":1559280038443,\"pid\":9760," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0I30\",\"classifyname\":\"各国文学\",\"clevel\":2,\"id\":9982," +
			"\"inserttime\":1559280038443,\"pid\":9760,\"updatetime\":1559280038443},{\"classifyid\":\"0J20\",\"classifyname\":\"绘画\"," +
			"\"clevel\":2,\"id\":10066,\"inserttime\":1559280038443,\"pid\":10053,\"updatetime\":1559280038443},{\"classifyid\":\"0J50\"," +
			"\"classifyname\":\"工艺美术\",\"clevel\":2,\"id\":10138,\"inserttime\":1559280038443,\"pid\":10053," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0J60\",\"classifyname\":\"音乐\",\"clevel\":2,\"id\":10153," +
			"\"inserttime\":1559280038443,\"pid\":10053,\"updatetime\":1559280038443},{\"classifyid\":\"0J80\",\"classifyname\":\"戏剧艺术\"," +
			"\"clevel\":2,\"id\":10190,\"inserttime\":1559280038443,\"pid\":10053,\"updatetime\":1559280038443},{\"classifyid\":\"0K10\"," +
			"\"classifyname\":\"世界史\",\"clevel\":2,\"id\":10215,\"inserttime\":1559280038443,\"pid\":10205,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0K20\",\"classifyname\":\"中国史\",\"clevel\":2,\"id\":10235,\"inserttime\":1559280038443,\"pid\":10205," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0K30\",\"classifyname\":\"亚洲史\",\"clevel\":2,\"id\":10374," +
			"\"inserttime\":1559280038443,\"pid\":10205,\"updatetime\":1559280038443},{\"classifyid\":\"0K40\",\"classifyname\":\"非洲史\"," +
			"\"clevel\":2,\"id\":10381,\"inserttime\":1559280038443,\"pid\":10205,\"updatetime\":1559280038443},{\"classifyid\":\"0K50\"," +
			"\"classifyname\":\"欧洲史\",\"clevel\":2,\"id\":10382,\"inserttime\":1559280038443,\"pid\":10205,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0K81\",\"classifyname\":\"传记\",\"clevel\":2,\"id\":10395,\"inserttime\":1559280038443,\"pid\":10205," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0K85\",\"classifyname\":\"文物考古\",\"clevel\":2,\"id\":10458," +
			"\"inserttime\":1559280038443,\"pid\":10205,\"updatetime\":1559280038443},{\"classifyid\":\"0K90\",\"classifyname\":\"地理\"," +
			"\"clevel\":2,\"id\":10486,\"inserttime\":1559280038443,\"pid\":10205,\"updatetime\":1559280038443},{\"classifyid\":\"0N01\"," +
			"\"classifyname\":\"自然科学理论与方法论\",\"clevel\":2,\"id\":10529,\"inserttime\":1559280038443,\"pid\":10527," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0N10\",\"classifyname\":\"自然科学现状及发展\",\"clevel\":2,\"id\":10539," +
			"\"inserttime\":1559280038443,\"pid\":10527,\"updatetime\":1559280038443},{\"classifyid\":\"0N20\"," +
			"\"classifyname\":\"自然科学机构、团体、会议\",\"clevel\":2,\"id\":10543,\"inserttime\":1559280038443,\"pid\":10527," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0N50\",\"classifyname\":\"自然科学丛书、文集、连续性出版物\",\"clevel\":2,\"id\":10546," +
			"\"inserttime\":1559280038443,\"pid\":10527,\"updatetime\":1559280038443},{\"classifyid\":\"0O10\",\"classifyname\":\"数学\"," +
			"\"clevel\":2,\"id\":10553,\"inserttime\":1559280038443,\"pid\":10551,\"updatetime\":1559280038443},{\"classifyid\":\"0O30\"," +
			"\"classifyname\":\"力学\",\"clevel\":2,\"id\":10634,\"inserttime\":1559280038443,\"pid\":10551,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0O40\",\"classifyname\":\"物理学\",\"clevel\":2,\"id\":10659,\"inserttime\":1559280038443,\"pid\":10551," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0P10\",\"classifyname\":\"天文学\",\"clevel\":2,\"id\":10752," +
			"\"inserttime\":1559280038443,\"pid\":10750,\"updatetime\":1559280038443},{\"classifyid\":\"0P20\",\"classifyname\":\"测绘学\"," +
			"\"clevel\":2,\"id\":10762,\"inserttime\":1559280038443,\"pid\":10750,\"updatetime\":1559280038443},{\"classifyid\":\"0P30\"," +
			"\"classifyname\":\"地球物理学\",\"clevel\":2,\"id\":10771,\"inserttime\":1559280038443,\"pid\":10750," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0P50\",\"classifyname\":\"地质学\",\"clevel\":2,\"id\":10795," +
			"\"inserttime\":1559280038443,\"pid\":10750,\"updatetime\":1559280038443},{\"classifyid\":\"0P90\"," +
			"\"classifyname\":\"自然地理学\",\"clevel\":2,\"id\":10835,\"inserttime\":1559280038443,\"pid\":10750," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0R00\",\"classifyname\":\"总论复分\",\"clevel\":2,\"id\":10900," +
			"\"inserttime\":1559280038443,\"pid\":10899,\"updatetime\":1559280038443},{\"classifyid\":\"0R20\",\"classifyname\":\"中国医学\"," +
			"\"clevel\":2,\"id\":10958,\"inserttime\":1559280038443,\"pid\":10899,\"updatetime\":1559280038443},{\"classifyid\":\"0S10\"," +
			"\"classifyname\":\"农业基础科学\",\"clevel\":2,\"id\":11283,\"inserttime\":1559280038443,\"pid\":11281," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0S70\",\"classifyname\":\"林业\",\"clevel\":2,\"id\":11336," +
			"\"inserttime\":1559280038443,\"pid\":11281,\"updatetime\":1559280038443},{\"classifyid\":\"0T00\",\"classifyname\":\"总论复分\"," +
			"\"clevel\":2,\"id\":11363,\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0T0B\"," +
			"\"classifyname\":\"一般工业技术\",\"clevel\":2,\"id\":11369,\"inserttime\":1559280038443,\"pid\":11362," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0T0E\",\"classifyname\":\"石油、天然气工业\",\"clevel\":2,\"id\":11409," +
			"\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0T0H\"," +
			"\"classifyname\":\"机械、仪表工业\",\"clevel\":2,\"id\":11528,\"inserttime\":1559280038443,\"pid\":11362," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0T0K\",\"classifyname\":\"能源与动力工程\",\"clevel\":2,\"id\":11585," +
			"\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0T0M\",\"classifyname\":\"电工技术\"," +
			"\"clevel\":2,\"id\":11624,\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0T0N\"," +
			"\"classifyname\":\"无线电电子学、电信技术\",\"clevel\":2,\"id\":11686,\"inserttime\":1559280038443,\"pid\":11362," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0T0P\",\"classifyname\":\"自动化技术、计算机技术\",\"clevel\":2,\"id\":11780," +
			"\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0T0S\"," +
			"\"classifyname\":\"轻工业、手工业\",\"clevel\":2,\"id\":11980,\"inserttime\":1559280038443,\"pid\":11362," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0T0U\",\"classifyname\":\"建筑科学\",\"clevel\":2,\"id\":12056," +
			"\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0T0V\",\"classifyname\":\"水利工程\"," +
			"\"clevel\":2,\"id\":12146,\"inserttime\":1559280038443,\"pid\":11362,\"updatetime\":1559280038443},{\"classifyid\":\"0U00\"," +
			"\"classifyname\":\"总论\",\"clevel\":2,\"id\":12158,\"inserttime\":1559280038443,\"pid\":12157,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0U10\",\"classifyname\":\"综合运输\",\"clevel\":2,\"id\":12159,\"inserttime\":1559280038443,\"pid\":12157," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0U20\",\"classifyname\":\"铁路运输\",\"clevel\":2,\"id\":12160," +
			"\"inserttime\":1559280038443,\"pid\":12157,\"updatetime\":1559280038443},{\"classifyid\":\"0U40\",\"classifyname\":\"公路运输\"," +
			"\"clevel\":2,\"id\":12169,\"inserttime\":1559280038443,\"pid\":12157,\"updatetime\":1559280038443},{\"classifyid\":\"0U60\"," +
			"\"classifyname\":\"水路运输\",\"clevel\":2,\"id\":12192,\"inserttime\":1559280038443,\"pid\":12157," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0V00\",\"classifyname\":\"总论\",\"clevel\":2,\"id\":12215," +
			"\"inserttime\":1559280038443,\"pid\":12214,\"updatetime\":1559280038443},{\"classifyid\":\"0V20\",\"classifyname\":\"航空\"," +
			"\"clevel\":2,\"id\":12217,\"inserttime\":1559280038443,\"pid\":12214,\"updatetime\":1559280038443},{\"classifyid\":\"0V40\"," +
			"\"classifyname\":\"航天(宇宙航行)\",\"clevel\":2,\"id\":12228,\"inserttime\":1559280038443,\"pid\":12214," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0X00\",\"classifyname\":\"总论\",\"clevel\":2,\"id\":12238," +
			"\"inserttime\":1559280038443,\"pid\":12237,\"updatetime\":1559280038443},{\"classifyid\":\"0X10\"," +
			"\"classifyname\":\"环境科学基础理论\",\"clevel\":2,\"id\":12239,\"inserttime\":1559280038443,\"pid\":12237," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0X20\",\"classifyname\":\"社会与环境\",\"clevel\":2,\"id\":12249," +
			"\"inserttime\":1559280038443,\"pid\":12237,\"updatetime\":1559280038443},{\"classifyid\":\"0X30\"," +
			"\"classifyname\":\"环境保护管理\",\"clevel\":2,\"id\":12250,\"inserttime\":1559280038443,\"pid\":12237," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0X50\",\"classifyname\":\"环境污染及其防治\",\"clevel\":2,\"id\":12252," +
			"\"inserttime\":1559280038443,\"pid\":12237,\"updatetime\":1559280038443},{\"classifyid\":\"0X70\"," +
			"\"classifyname\":\"废物处理与综合利用\",\"clevel\":2,\"id\":12261,\"inserttime\":1559280038443,\"pid\":12237," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0X80\",\"classifyname\":\"环境质量评价与环境监测\",\"clevel\":2,\"id\":12262," +
			"\"inserttime\":1559280038443,\"pid\":12237,\"updatetime\":1559280038443},{\"classifyid\":\"0X90\",\"classifyname\":\"安全科学\"," +
			"\"clevel\":2,\"id\":12263,\"inserttime\":1559280038443,\"pid\":12237,\"updatetime\":1559280038443},{\"classifyid\":\"0Z01\"," +
			"\"classifyname\":\"丛书\",\"clevel\":2,\"id\":12266,\"inserttime\":1559280038443,\"pid\":12264,\"updatetime\":1559280038443}," +
			"{\"classifyid\":\"0Z02\",\"classifyname\":\"百科全书、类书\",\"clevel\":2,\"id\":12278,\"inserttime\":1559280038443,\"pid\":12264," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0Z03\",\"classifyname\":\"辞典\",\"clevel\":2,\"id\":12285," +
			"\"inserttime\":1559280038443,\"pid\":12264,\"updatetime\":1559280038443},{\"classifyid\":\"0Z04\"," +
			"\"classifyname\":\"论文集、全集、选集、杂著\",\"clevel\":2,\"id\":12286,\"inserttime\":1559280038443,\"pid\":12264," +
			"\"updatetime\":1559280038443},{\"classifyid\":\"0Z08\",\"classifyname\":\"图书书目、文摘、索引\",\"clevel\":2,\"id\":12300," +
			"\"inserttime\":1559280038443,\"pid\":12264,\"updatetime\":1559280038443}]";



}
