package com.xin.stone.ast;

import com.xin.stone.Token;

public class NumericLiteral extends ASTLeaf {

	public NumericLiteral(Token token) {
		super(token);
	}
	
	public long value() {
		return token.getNumeric();
	}

}
