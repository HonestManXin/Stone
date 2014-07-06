package com.xin.stone.ast;

import java.util.ArrayList;
import java.util.Iterator;

import com.xin.stone.Token;

public class ASTLeaf extends ASTree {
	protected Token token;
	private static ArrayList<ASTree> empty = new ArrayList<ASTree>();
	
	public ASTLeaf(Token token) {
		this.token = token;
	}
	
	@Override
	public String getLocation() {
		return "at line " + token.getLineNumber();
	}

	@Override
	public ASTree child(int i) {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public int numChildren() {
		return 0;
	}

	@Override
	public Iterator<ASTree> children() {
		return empty.iterator();
	}

}
