package com.easyshop.test;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.easyshop.pojo.JsonObject;

public class TestJson {

	@Test
	public void test1() {
		//String s = "[{\"id\":23,\"text\":\"美特斯邦威\"},{\"id\":24,\"text\":\"森马\"},{\"id\":25,\"text\":\"李维斯\"},{\"id\":26,\"text\":\"杰克琼斯\"},{\"id\":27,\"text\":\"浪琴牌\"},{\"id\":28,\"text\":\"Apple\"}]";
		String s = "[{\"text\":\"测试1\"},{\"text\":\"测试2\"},{\"text\":\"测试3\"},{\"text\":\"女装2\"}]";
		String str = convertString(s);
		System.out.println(str);
	}

	public static String convertString(String s) {
		// 转化为对象
		List<JsonObject> list = JSON.parseArray(s, JsonObject.class);
		StringBuffer sb = new StringBuffer();
		for (JsonObject object : list) {
			sb.append(object.getText()).append(",");
		}
		String str = sb.toString();
		str = str.substring(0, str.length() - 1);
		return str;
	}
}
