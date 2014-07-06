package com.xin.stone.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
	protected List<ASTree> childrens = null;
	public ASTList(List<ASTree> child) {
		this.childrens = child;
	}
	
	@Override
	public String getLocation() {
		for (ASTree child : childrens) {
			String s = child.getLocation();
			if (s != null) {
				return s;
			}
		}
		return null;
	}

	@Override
	public ASTree child(int i) {
		return childrens.get(i);
	}

	@Override
	public int numChildren() {
		return childrens.size();
	}

	@Override
	public Iterator<ASTree> children() {
		return childrens.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		String sep = "";
		for (ASTree tree : childrens) {
			sb.append(sep);
			sep = " ";
			sb.append(tree.toString());
		}
		sb.append(")");
		return sb.toString();
	}

}
