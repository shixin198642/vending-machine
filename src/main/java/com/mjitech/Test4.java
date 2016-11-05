package com.mjitech;

import java.util.HashMap;
import java.util.Map;

import com.mjitech.wxpay.util.MD5;

public class Test4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test = "appId=wx4da5ecd6305e620a&nonceStr=nqmzfrarrjpkqknh8m6pnlcpinnb2s&package=prepay_id&signType=MD5&timeStamp=1477903476&key=SFkeFpqecaeguwldI38sdMDeqpmzekgh";
		String ret = MD5.MD5Encode(test).toUpperCase();
		System.out.println(ret);
	}

}
