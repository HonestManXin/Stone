package com.xin.stone.exception;

import java.io.IOException;

import com.xin.stone.SymbolType;
import com.xin.stone.Token;

public class ParseException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3201428935582643873L;

	public ParseException(Token t) {
		this("", t);
	}
	
	public ParseException(String msg, Token t) {
		super("syntax around " + location(t) + "." + msg );
	}
	
	private static String location(Token t) {
		if (t.getType() == SymbolType.EOF)
			return "the last line";
		
		return "\"" + t.getText() + "\" at line " + t.getLineNumber(); 
	}
	
	public ParseException(String msg) {
		super(msg);
	}
	
	public ParseException(IOException e) {
		super(e);
	}
}
