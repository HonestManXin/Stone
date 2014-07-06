package com.xin.stone;

import com.xin.stone.exception.StoneException;

public abstract class Token {
	private String text;
	protected SymbolType type;
	private int lineNumber;
	public static final Token EOF = new Token("", -1) {
		{
			this.type = SymbolType.EOF;
		}
	};
	public static final String EOL = "\\n";
	
	protected Token(String text, int lineNumber) {
		this.text = text;
		this.lineNumber = lineNumber;
	}
	
	public String getText() {
		return text;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public SymbolType getType() {
		return type;
	}
	
	public long getNumeric() {
		throw new StoneException("not a number");
	}
	
	public boolean isIdentifier() {
		return false;
	}
	
	public boolean isNumber() {
		return false;
	}
	
	public boolean isString() {
		return false;
	}
}
