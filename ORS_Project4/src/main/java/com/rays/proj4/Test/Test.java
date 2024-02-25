package com.rays.proj4.Test;

public class Test {

	public static void main(String[] args) {

		String s1 = "welcome home";
		String[] s2 = s1.split("");
		int count = 0;

		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) == 'e') {
				count++;
			}
		}
		System.out.println("count" + count);
		System.out.println("s2" + s1.length());
	}

}
