package com.twinkle.test;

import java.util.regex.Pattern;

import com.twinkle.plugin.IpSpiderPlugin;

public class Test {

	public static void main(String[] args) {
		
		IpSpiderPlugin ip = new IpSpiderPlugin();
								
		System.out.println(ip.getIpByIp("119.29.98.60"));
		
		System.out.println(Pattern.compile("^/Law.+").matcher("/Law/44//66").matches());
		

	}

}
