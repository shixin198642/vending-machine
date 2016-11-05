package com.mjitech.utils;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Component("xmlUtils")
@Singleton
public class XmlUtils {

	private static XStream mapxstream = new XStream(new DomDriver("UTF-8",
			new XmlFriendlyNameCoder("-_", "_")));
	static {
		mapxstream.alias("xml", Map.class);
		
		mapxstream.registerConverter(new MapEntryConverter());
	}

	public String convertMapToXMLString(Map<String, Object> input) {
		
		String retXML = mapxstream.toXML(input);
		return retXML;
	}
	
	public static void main(String[] args) {
		Map<String, Object> retMsg = new HashMap<String, Object>();
		retMsg.put("MsgType", "text");
		retMsg.put("CreateTime", "" + (System.currentTimeMillis() / 1000));
		retMsg.put("FromUserName", "123sdsf2e");
		retMsg.put("ToUserName", "dasdfq3rdsaf");
		retMsg.put("Content", "你好");
		System.out.println(new XmlUtils().convertMapToXMLString(retMsg));
	}

}
