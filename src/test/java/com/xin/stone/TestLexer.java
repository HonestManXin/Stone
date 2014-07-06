package com.xin.stone;

import java.io.File;
import java.io.FileReader;

public class TestLexer {
	
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader(new File("resources/lexer.test"));
		Lexer lexer = new Lexer(fr);
		Token t;
		while ((t = lexer.read()) != Token.EOF) {
			System.out.println("------> " + t.getText());
		}
	}
}
