package com.xin.stone;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xin.stone.exception.ParseException;

public class Lexer {
	public static String regex
    = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
      + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
	private Pattern pattern = Pattern.compile(regex);
	
	private List<Token> queue = new ArrayList<Token>();
	private LineNumberReader reader = null;
	private boolean hasMore = true;
	
	
	public Lexer(Reader r) {
		reader = new LineNumberReader(r);
	}
	
	public Token read() throws ParseException {
		if (fillQueue(0)) {
			return queue.remove(0);
		}
		
		return Token.EOF;
	}
	
	public Token peek(int i) throws ParseException {
		if (fillQueue(i))
			return queue.get(i);
		return Token.EOF;
	}
	
	private boolean fillQueue(int i) throws ParseException {
		while (i >= queue.size()) {
			if (hasMore) 
				readLine();
			else 
				return false;
		}
		return true;
	}
	
	private void readLine() throws ParseException {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new ParseException(e);
		}
		if (line == null) {
			hasMore = false;
			return;
		}
		
		int lineNo = reader.getLineNumber();
		Matcher matcher = pattern.matcher(line);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int endPos = line.length();
		
		while (pos < endPos) {
			matcher.region(pos, endPos);
			if (matcher.lookingAt()) {
				addToken(lineNo, matcher);
				pos = matcher.end();
			} else {
				throw new ParseException("bad token at line " + lineNo);
			}
		}
		queue.add(new EOLToken(Token.EOL, lineNo));
	}
	
	private void addToken(int lineNo, Matcher matcher) {
		String m = matcher.group(1);
		if (m != null) // if not a space
            if (matcher.group(2) == null) { // if not a comment
                Token token;
                if (matcher.group(3) != null)
                    token = new NumericToken(lineNo, m, Long.parseLong(m));
                else if (matcher.group(4) != null)
                    token = new StringToken(lineNo, toStringLiteral(m));
                else
                    token = new IdentifierToken(lineNo, m);
                queue.add(token);
            }
	}
	
	/**
	 * explain string such as "\"\\"
	 * @param s
	 * @return
	 */
	protected String toStringLiteral(String s) {
		StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\')
                    c = s.charAt(++i);
                else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
	}
	
}

class NumericToken extends Token {
	private long value;
	
	protected NumericToken(int lineNumber, String text, long value) {
		super(text, lineNumber);
		this.value = value;
		this.type = SymbolType.NUMERIC_CONSTANT;
	}
	
	@Override
	public long getNumeric() {
		return this.value;
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
}

class StringToken extends Token {
	
	public StringToken(int lineNumber, String text) {
		super(text, lineNumber);
		this.type = SymbolType.STRING_CONSTANT;
	}
	
	@Override
	public boolean isString() {
		return true;
	}
}

class IdentifierToken extends Token {

	protected IdentifierToken(int lineNumber, String text) {
		super(text, lineNumber);
		this.type = SymbolType.IDENTIFIER;
	}
	
	@Override
	public boolean isIdentifier() {
		return true;
	}
}

class EOLToken extends Token {

	protected EOLToken(String text, int lineNumber) {
		super(text, lineNumber);
		this.type = SymbolType.EOL;
	}
	
}



