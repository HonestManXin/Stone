package com.xin.stone.ast;

import java.util.List;

import com.xin.stone.Token;

public class BinaryExpress extends ASTList {

	public BinaryExpress(List<ASTree> child) {
		super(child);
	}
	
	public ASTree left() {
		return child(0);
	}
	
	public ASTree right() {
		return child(1);
	}
	
	public String operator() {
		Token t = ((ASTLeaf)child(1)).token;
		return t.getText();
	}

}
