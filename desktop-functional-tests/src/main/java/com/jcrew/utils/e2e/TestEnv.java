package com.jcrew.utils.e2e;

public class TestEnv {

	public static void main(String[] args) {
		System.out.println(System.getenv("M2"));
		System.out.println(System.getenv("M2_HOME"));
		System.out.println(System.getenv("Path"));
		System.out.println(System.getenv("path"));
	}
}
