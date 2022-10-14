// Generated from BeetlLexer.g4 by ANTLR 4.9.2
package org.beetl.core.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BeetlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		If=1, For=2, Elsefor=3, Else=4, While=5, Switch=6, Select=7, Return=8, 
		Break=9, Continue=10, Var=11, Directive=12, Case=13, Default=14, Try=15, 
		Catch=16, Ajax=17, Fragment=18, LEFT_BRACE=19, RIGHT_BRACE=20, LEFT_PAR=21, 
		RIGHT_PAR=22, LEFT_SQBR=23, RIGHT_SQBR=24, INCREASE=25, DECREASE=26, VIRTUAL=27, 
		ADD=28, MIN=29, MUlTIP=30, DIV=31, MOD=32, EQUAL=33, NOT_EQUAL=34, ASSIN=35, 
		LARGE_EQUAL=36, LARGE=37, LESS_EQUAL=38, LESS=39, NOT=40, AND=41, OR=42, 
		QUESTOIN=43, AT=44, NULL=45, TRUE=46, FALSE=47, END=48, COMMA=49, COLON=50, 
		PERIOD=51, FOR_IN=52, LEFT_TOKEN=53, LEFT_TOKEN2=54, RIGHT_TOKEN=55, LEFT_TEXT_TOKEN=56, 
		HexLiteral=57, DecimalLiteral=58, OctalLiteral=59, FloatingPointLiteral=60, 
		StringLiteral=61, Identifier=62, WS=63, LINE_COMMENT=64, COMMENT=65;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"If", "For", "Elsefor", "Else", "While", "Switch", "Select", "Return", 
			"Break", "Continue", "Var", "Directive", "Case", "Default", "Try", "Catch", 
			"Ajax", "Fragment", "LEFT_BRACE", "RIGHT_BRACE", "LEFT_PAR", "RIGHT_PAR", 
			"LEFT_SQBR", "RIGHT_SQBR", "INCREASE", "DECREASE", "VIRTUAL", "ADD", 
			"MIN", "MUlTIP", "DIV", "MOD", "EQUAL", "NOT_EQUAL", "ASSIN", "LARGE_EQUAL", 
			"LARGE", "LESS_EQUAL", "LESS", "NOT", "AND", "OR", "QUESTOIN", "AT", 
			"NULL", "TRUE", "FALSE", "END", "COMMA", "COLON", "PERIOD", "FOR_IN", 
			"LEFT_TOKEN", "LEFT_TOKEN2", "RIGHT_TOKEN", "LEFT_TEXT_TOKEN", "HexLiteral", 
			"DecimalLiteral", "OctalLiteral", "HexDigit", "IntegerTypeSuffix", "FloatingPointLiteral", 
			"Exponent", "FloatTypeSuffix", "StringLiteral", "LONG_STRING", "LONG_STRING_ITEM", 
			"LONG_STRING_CHAR", "STRING_ESCAPE_SEQ", "EscapeSequence", "OctalEscape", 
			"UnicodeEscape", "Identifier", "Letter", "JavaIDDigit", "WS", "LINE_COMMENT", 
			"COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'for'", "'elsefor'", "'else'", "'while'", "'switch'", 
			"'select'", "'return'", "'break'", "'continue'", "'var'", null, "'case'", 
			"'default'", "'try'", "'catch'", "'#ajax'", "'#fragment'", "'{'", "'}'", 
			"'('", "')'", "'['", "']'", "'++'", "'--'", "'.~'", "'+'", "'-'", "'*'", 
			"'/'", "'%'", "'=='", "'!='", "'='", "'>='", "'>'", "'<='", "'<'", "'!'", 
			"'&&'", "'||'", "'?'", "'@'", "'null'", "'true'", "'false'", "';'", "','", 
			"':'", "'.'", "'in'", "'<~'", "'<#'", "'~>'", "'<^'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "If", "For", "Elsefor", "Else", "While", "Switch", "Select", "Return", 
			"Break", "Continue", "Var", "Directive", "Case", "Default", "Try", "Catch", 
			"Ajax", "Fragment", "LEFT_BRACE", "RIGHT_BRACE", "LEFT_PAR", "RIGHT_PAR", 
			"LEFT_SQBR", "RIGHT_SQBR", "INCREASE", "DECREASE", "VIRTUAL", "ADD", 
			"MIN", "MUlTIP", "DIV", "MOD", "EQUAL", "NOT_EQUAL", "ASSIN", "LARGE_EQUAL", 
			"LARGE", "LESS_EQUAL", "LESS", "NOT", "AND", "OR", "QUESTOIN", "AT", 
			"NULL", "TRUE", "FALSE", "END", "COMMA", "COLON", "PERIOD", "FOR_IN", 
			"LEFT_TOKEN", "LEFT_TOKEN2", "RIGHT_TOKEN", "LEFT_TEXT_TOKEN", "HexLiteral", 
			"DecimalLiteral", "OctalLiteral", "FloatingPointLiteral", "StringLiteral", 
			"Identifier", "WS", "LINE_COMMENT", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


		public void notifyListeners(LexerNoViableAltException e) {
			String text = _input.getText(Interval.of(_tokenStartCharIndex, _input.index()));		
			ANTLRErrorListener listener = getErrorListenerDispatch();
			listener.syntaxError(this, null, _tokenStartLine, _tokenStartCharPositionInLine, text, e);
		}


	public BeetlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BeetlLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2C\u0287\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00f4\n\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27"+
		"\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34"+
		"\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$"+
		"\3%\3%\3%\3&\3&\3\'\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3-\3-"+
		"\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61"+
		"\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67"+
		"\3\67\38\38\38\39\39\39\3:\3:\3:\6:\u0184\n:\r:\16:\u0185\3:\5:\u0189"+
		"\n:\3;\3;\3;\7;\u018e\n;\f;\16;\u0191\13;\5;\u0193\n;\3;\5;\u0196\n;\3"+
		"<\3<\6<\u019a\n<\r<\16<\u019b\3<\5<\u019f\n<\3=\3=\3>\3>\3?\6?\u01a6\n"+
		"?\r?\16?\u01a7\3?\3?\7?\u01ac\n?\f?\16?\u01af\13?\3?\5?\u01b2\n?\3?\5"+
		"?\u01b5\n?\3?\3?\6?\u01b9\n?\r?\16?\u01ba\3?\5?\u01be\n?\3?\5?\u01c1\n"+
		"?\3?\6?\u01c4\n?\r?\16?\u01c5\3?\3?\5?\u01ca\n?\3?\6?\u01cd\n?\r?\16?"+
		"\u01ce\3?\3?\3?\3?\3?\5?\u01d6\n?\3?\7?\u01d9\n?\f?\16?\u01dc\13?\3?\3"+
		"?\7?\u01e0\n?\f?\16?\u01e3\13?\5?\u01e5\n?\3?\3?\5?\u01e9\n?\3?\6?\u01ec"+
		"\n?\r?\16?\u01ed\3?\5?\u01f1\n?\5?\u01f3\n?\3@\3@\5@\u01f7\n@\3@\6@\u01fa"+
		"\n@\r@\16@\u01fb\3A\3A\3B\3B\3B\7B\u0203\nB\fB\16B\u0206\13B\3B\3B\3B"+
		"\3B\7B\u020c\nB\fB\16B\u020f\13B\3B\3B\5B\u0213\nB\3C\3C\3C\3C\3C\7C\u021a"+
		"\nC\fC\16C\u021d\13C\3C\3C\3C\3C\3C\3C\3C\3C\7C\u0227\nC\fC\16C\u022a"+
		"\13C\3C\3C\3C\5C\u022f\nC\3D\3D\5D\u0233\nD\3E\3E\3F\3F\3F\3F\5F\u023b"+
		"\nF\3G\3G\3G\3G\5G\u0241\nG\3H\3H\3H\3H\3H\3H\3H\3H\3H\5H\u024c\nH\3I"+
		"\3I\3I\3I\3I\3I\3I\3J\3J\3J\7J\u0258\nJ\fJ\16J\u025b\13J\3K\3K\3L\3L\3"+
		"M\6M\u0262\nM\rM\16M\u0263\3M\3M\3N\3N\3N\3N\7N\u026c\nN\fN\16N\u026f"+
		"\13N\3N\5N\u0272\nN\3N\3N\5N\u0276\nN\3N\3N\3O\3O\3O\3O\7O\u027e\nO\f"+
		"O\16O\u0281\13O\3O\3O\3O\3O\3O\5\u021b\u0228\u027f\2P\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K"+
		"\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y\2{\2}"+
		">\177\2\u0081\2\u0083?\u0085\2\u0087\2\u0089\2\u008b\2\u008d\2\u008f\2"+
		"\u0091\2\u0093@\u0095\2\u0097\2\u0099A\u009bB\u009dC\3\2\20\4\2ZZzz\5"+
		"\2\62;CHch\4\2JJjj\4\2RRrr\4\2--//\4\2GGgg\6\2\f\f\17\17$$^^\6\2\f\f\17"+
		"\17))^^\3\2^^\4\2\f\f\17\17\n\2$$))^^ddhhppttvv\16\2&&C\\aac|\u00c2\u00d8"+
		"\u00da\u00f8\u00fa\u2001\u3042\u3191\u3302\u3381\u3402\u3d2f\u4e02\ua001"+
		"\uf902\ufb01\21\2\62;\u0662\u066b\u06f2\u06fb\u0968\u0971\u09e8\u09f1"+
		"\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71\u0be9\u0bf1\u0c68\u0c71\u0ce8\u0cf1"+
		"\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb\u1042\u104b\5\2\13\f\16\17\"\"\2"+
		"\u02ae\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3"+
		"\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2"+
		"\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2"+
		"m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2}\3"+
		"\2\2\2\2\u0083\3\2\2\2\2\u0093\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2"+
		"\2\u009d\3\2\2\2\3\u009f\3\2\2\2\5\u00a2\3\2\2\2\7\u00a6\3\2\2\2\t\u00ae"+
		"\3\2\2\2\13\u00b3\3\2\2\2\r\u00b9\3\2\2\2\17\u00c0\3\2\2\2\21\u00c7\3"+
		"\2\2\2\23\u00ce\3\2\2\2\25\u00d4\3\2\2\2\27\u00dd\3\2\2\2\31\u00f3\3\2"+
		"\2\2\33\u00f5\3\2\2\2\35\u00fa\3\2\2\2\37\u0102\3\2\2\2!\u0106\3\2\2\2"+
		"#\u010c\3\2\2\2%\u0112\3\2\2\2\'\u011c\3\2\2\2)\u011e\3\2\2\2+\u0120\3"+
		"\2\2\2-\u0122\3\2\2\2/\u0124\3\2\2\2\61\u0126\3\2\2\2\63\u0128\3\2\2\2"+
		"\65\u012b\3\2\2\2\67\u012e\3\2\2\29\u0131\3\2\2\2;\u0133\3\2\2\2=\u0135"+
		"\3\2\2\2?\u0137\3\2\2\2A\u0139\3\2\2\2C\u013b\3\2\2\2E\u013e\3\2\2\2G"+
		"\u0141\3\2\2\2I\u0143\3\2\2\2K\u0146\3\2\2\2M\u0148\3\2\2\2O\u014b\3\2"+
		"\2\2Q\u014d\3\2\2\2S\u014f\3\2\2\2U\u0152\3\2\2\2W\u0155\3\2\2\2Y\u0157"+
		"\3\2\2\2[\u0159\3\2\2\2]\u015e\3\2\2\2_\u0163\3\2\2\2a\u0169\3\2\2\2c"+
		"\u016b\3\2\2\2e\u016d\3\2\2\2g\u016f\3\2\2\2i\u0171\3\2\2\2k\u0174\3\2"+
		"\2\2m\u0177\3\2\2\2o\u017a\3\2\2\2q\u017d\3\2\2\2s\u0180\3\2\2\2u\u0192"+
		"\3\2\2\2w\u0197\3\2\2\2y\u01a0\3\2\2\2{\u01a2\3\2\2\2}\u01f2\3\2\2\2\177"+
		"\u01f4\3\2\2\2\u0081\u01fd\3\2\2\2\u0083\u0212\3\2\2\2\u0085\u022e\3\2"+
		"\2\2\u0087\u0232\3\2\2\2\u0089\u0234\3\2\2\2\u008b\u023a\3\2\2\2\u008d"+
		"\u0240\3\2\2\2\u008f\u024b\3\2\2\2\u0091\u024d\3\2\2\2\u0093\u0254\3\2"+
		"\2\2\u0095\u025c\3\2\2\2\u0097\u025e\3\2\2\2\u0099\u0261\3\2\2\2\u009b"+
		"\u0267\3\2\2\2\u009d\u0279\3\2\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7h\2"+
		"\2\u00a1\4\3\2\2\2\u00a2\u00a3\7h\2\2\u00a3\u00a4\7q\2\2\u00a4\u00a5\7"+
		"t\2\2\u00a5\6\3\2\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7n\2\2\u00a8\u00a9"+
		"\7u\2\2\u00a9\u00aa\7g\2\2\u00aa\u00ab\7h\2\2\u00ab\u00ac\7q\2\2\u00ac"+
		"\u00ad\7t\2\2\u00ad\b\3\2\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7n\2\2\u00b0"+
		"\u00b1\7u\2\2\u00b1\u00b2\7g\2\2\u00b2\n\3\2\2\2\u00b3\u00b4\7y\2\2\u00b4"+
		"\u00b5\7j\2\2\u00b5\u00b6\7k\2\2\u00b6\u00b7\7n\2\2\u00b7\u00b8\7g\2\2"+
		"\u00b8\f\3\2\2\2\u00b9\u00ba\7u\2\2\u00ba\u00bb\7y\2\2\u00bb\u00bc\7k"+
		"\2\2\u00bc\u00bd\7v\2\2\u00bd\u00be\7e\2\2\u00be\u00bf\7j\2\2\u00bf\16"+
		"\3\2\2\2\u00c0\u00c1\7u\2\2\u00c1\u00c2\7g\2\2\u00c2\u00c3\7n\2\2\u00c3"+
		"\u00c4\7g\2\2\u00c4\u00c5\7e\2\2\u00c5\u00c6\7v\2\2\u00c6\20\3\2\2\2\u00c7"+
		"\u00c8\7t\2\2\u00c8\u00c9\7g\2\2\u00c9\u00ca\7v\2\2\u00ca\u00cb\7w\2\2"+
		"\u00cb\u00cc\7t\2\2\u00cc\u00cd\7p\2\2\u00cd\22\3\2\2\2\u00ce\u00cf\7"+
		"d\2\2\u00cf\u00d0\7t\2\2\u00d0\u00d1\7g\2\2\u00d1\u00d2\7c\2\2\u00d2\u00d3"+
		"\7m\2\2\u00d3\24\3\2\2\2\u00d4\u00d5\7e\2\2\u00d5\u00d6\7q\2\2\u00d6\u00d7"+
		"\7p\2\2\u00d7\u00d8\7v\2\2\u00d8\u00d9\7k\2\2\u00d9\u00da\7p\2\2\u00da"+
		"\u00db\7w\2\2\u00db\u00dc\7g\2\2\u00dc\26\3\2\2\2\u00dd\u00de\7x\2\2\u00de"+
		"\u00df\7c\2\2\u00df\u00e0\7t\2\2\u00e0\30\3\2\2\2\u00e1\u00e2\7F\2\2\u00e2"+
		"\u00e3\7K\2\2\u00e3\u00e4\7T\2\2\u00e4\u00e5\7G\2\2\u00e5\u00e6\7E\2\2"+
		"\u00e6\u00e7\7V\2\2\u00e7\u00e8\7K\2\2\u00e8\u00e9\7X\2\2\u00e9\u00f4"+
		"\7G\2\2\u00ea\u00eb\7f\2\2\u00eb\u00ec\7k\2\2\u00ec\u00ed\7t\2\2\u00ed"+
		"\u00ee\7g\2\2\u00ee\u00ef\7e\2\2\u00ef\u00f0\7v\2\2\u00f0\u00f1\7k\2\2"+
		"\u00f1\u00f2\7x\2\2\u00f2\u00f4\7g\2\2\u00f3\u00e1\3\2\2\2\u00f3\u00ea"+
		"\3\2\2\2\u00f4\32\3\2\2\2\u00f5\u00f6\7e\2\2\u00f6\u00f7\7c\2\2\u00f7"+
		"\u00f8\7u\2\2\u00f8\u00f9\7g\2\2\u00f9\34\3\2\2\2\u00fa\u00fb\7f\2\2\u00fb"+
		"\u00fc\7g\2\2\u00fc\u00fd\7h\2\2\u00fd\u00fe\7c\2\2\u00fe\u00ff\7w\2\2"+
		"\u00ff\u0100\7n\2\2\u0100\u0101\7v\2\2\u0101\36\3\2\2\2\u0102\u0103\7"+
		"v\2\2\u0103\u0104\7t\2\2\u0104\u0105\7{\2\2\u0105 \3\2\2\2\u0106\u0107"+
		"\7e\2\2\u0107\u0108\7c\2\2\u0108\u0109\7v\2\2\u0109\u010a\7e\2\2\u010a"+
		"\u010b\7j\2\2\u010b\"\3\2\2\2\u010c\u010d\7%\2\2\u010d\u010e\7c\2\2\u010e"+
		"\u010f\7l\2\2\u010f\u0110\7c\2\2\u0110\u0111\7z\2\2\u0111$\3\2\2\2\u0112"+
		"\u0113\7%\2\2\u0113\u0114\7h\2\2\u0114\u0115\7t\2\2\u0115\u0116\7c\2\2"+
		"\u0116\u0117\7i\2\2\u0117\u0118\7o\2\2\u0118\u0119\7g\2\2\u0119\u011a"+
		"\7p\2\2\u011a\u011b\7v\2\2\u011b&\3\2\2\2\u011c\u011d\7}\2\2\u011d(\3"+
		"\2\2\2\u011e\u011f\7\177\2\2\u011f*\3\2\2\2\u0120\u0121\7*\2\2\u0121,"+
		"\3\2\2\2\u0122\u0123\7+\2\2\u0123.\3\2\2\2\u0124\u0125\7]\2\2\u0125\60"+
		"\3\2\2\2\u0126\u0127\7_\2\2\u0127\62\3\2\2\2\u0128\u0129\7-\2\2\u0129"+
		"\u012a\7-\2\2\u012a\64\3\2\2\2\u012b\u012c\7/\2\2\u012c\u012d\7/\2\2\u012d"+
		"\66\3\2\2\2\u012e\u012f\7\60\2\2\u012f\u0130\7\u0080\2\2\u01308\3\2\2"+
		"\2\u0131\u0132\7-\2\2\u0132:\3\2\2\2\u0133\u0134\7/\2\2\u0134<\3\2\2\2"+
		"\u0135\u0136\7,\2\2\u0136>\3\2\2\2\u0137\u0138\7\61\2\2\u0138@\3\2\2\2"+
		"\u0139\u013a\7\'\2\2\u013aB\3\2\2\2\u013b\u013c\7?\2\2\u013c\u013d\7?"+
		"\2\2\u013dD\3\2\2\2\u013e\u013f\7#\2\2\u013f\u0140\7?\2\2\u0140F\3\2\2"+
		"\2\u0141\u0142\7?\2\2\u0142H\3\2\2\2\u0143\u0144\7@\2\2\u0144\u0145\7"+
		"?\2\2\u0145J\3\2\2\2\u0146\u0147\7@\2\2\u0147L\3\2\2\2\u0148\u0149\7>"+
		"\2\2\u0149\u014a\7?\2\2\u014aN\3\2\2\2\u014b\u014c\7>\2\2\u014cP\3\2\2"+
		"\2\u014d\u014e\7#\2\2\u014eR\3\2\2\2\u014f\u0150\7(\2\2\u0150\u0151\7"+
		"(\2\2\u0151T\3\2\2\2\u0152\u0153\7~\2\2\u0153\u0154\7~\2\2\u0154V\3\2"+
		"\2\2\u0155\u0156\7A\2\2\u0156X\3\2\2\2\u0157\u0158\7B\2\2\u0158Z\3\2\2"+
		"\2\u0159\u015a\7p\2\2\u015a\u015b\7w\2\2\u015b\u015c\7n\2\2\u015c\u015d"+
		"\7n\2\2\u015d\\\3\2\2\2\u015e\u015f\7v\2\2\u015f\u0160\7t\2\2\u0160\u0161"+
		"\7w\2\2\u0161\u0162\7g\2\2\u0162^\3\2\2\2\u0163\u0164\7h\2\2\u0164\u0165"+
		"\7c\2\2\u0165\u0166\7n\2\2\u0166\u0167\7u\2\2\u0167\u0168\7g\2\2\u0168"+
		"`\3\2\2\2\u0169\u016a\7=\2\2\u016ab\3\2\2\2\u016b\u016c\7.\2\2\u016cd"+
		"\3\2\2\2\u016d\u016e\7<\2\2\u016ef\3\2\2\2\u016f\u0170\7\60\2\2\u0170"+
		"h\3\2\2\2\u0171\u0172\7k\2\2\u0172\u0173\7p\2\2\u0173j\3\2\2\2\u0174\u0175"+
		"\7>\2\2\u0175\u0176\7\u0080\2\2\u0176l\3\2\2\2\u0177\u0178\7>\2\2\u0178"+
		"\u0179\7%\2\2\u0179n\3\2\2\2\u017a\u017b\7\u0080\2\2\u017b\u017c\7@\2"+
		"\2\u017cp\3\2\2\2\u017d\u017e\7>\2\2\u017e\u017f\7`\2\2\u017fr\3\2\2\2"+
		"\u0180\u0181\7\62\2\2\u0181\u0183\t\2\2\2\u0182\u0184\5y=\2\u0183\u0182"+
		"\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0183\3\2\2\2\u0185\u0186\3\2\2\2\u0186"+
		"\u0188\3\2\2\2\u0187\u0189\5{>\2\u0188\u0187\3\2\2\2\u0188\u0189\3\2\2"+
		"\2\u0189t\3\2\2\2\u018a\u0193\7\62\2\2\u018b\u018f\4\63;\2\u018c\u018e"+
		"\4\62;\2\u018d\u018c\3\2\2\2\u018e\u0191\3\2\2\2\u018f\u018d\3\2\2\2\u018f"+
		"\u0190\3\2\2\2\u0190\u0193\3\2\2\2\u0191\u018f\3\2\2\2\u0192\u018a\3\2"+
		"\2\2\u0192\u018b\3\2\2\2\u0193\u0195\3\2\2\2\u0194\u0196\5{>\2\u0195\u0194"+
		"\3\2\2\2\u0195\u0196\3\2\2\2\u0196v\3\2\2\2\u0197\u0199\7\62\2\2\u0198"+
		"\u019a\4\629\2\u0199\u0198\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u0199\3\2"+
		"\2\2\u019b\u019c\3\2\2\2\u019c\u019e\3\2\2\2\u019d\u019f\5{>\2\u019e\u019d"+
		"\3\2\2\2\u019e\u019f\3\2\2\2\u019fx\3\2\2\2\u01a0\u01a1\t\3\2\2\u01a1"+
		"z\3\2\2\2\u01a2\u01a3\t\4\2\2\u01a3|\3\2\2\2\u01a4\u01a6\4\62;\2\u01a5"+
		"\u01a4\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a7\u01a8\3\2"+
		"\2\2\u01a8\u01a9\3\2\2\2\u01a9\u01ad\7\60\2\2\u01aa\u01ac\4\62;\2\u01ab"+
		"\u01aa\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad\u01ae\3\2"+
		"\2\2\u01ae\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u01b2\5\177@\2\u01b1"+
		"\u01b0\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2\u01b4\3\2\2\2\u01b3\u01b5\5\u0081"+
		"A\2\u01b4\u01b3\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01f3\3\2\2\2\u01b6"+
		"\u01b8\7\60\2\2\u01b7\u01b9\4\62;\2\u01b8\u01b7\3\2\2\2\u01b9\u01ba\3"+
		"\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bd\3\2\2\2\u01bc"+
		"\u01be\5\177@\2\u01bd\u01bc\3\2\2\2\u01bd\u01be\3\2\2\2\u01be\u01c0\3"+
		"\2\2\2\u01bf\u01c1\5\u0081A\2\u01c0\u01bf\3\2\2\2\u01c0\u01c1\3\2\2\2"+
		"\u01c1\u01f3\3\2\2\2\u01c2\u01c4\4\62;\2\u01c3\u01c2\3\2\2\2\u01c4\u01c5"+
		"\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7"+
		"\u01c9\5\177@\2\u01c8\u01ca\5\u0081A\2\u01c9\u01c8\3\2\2\2\u01c9\u01ca"+
		"\3\2\2\2\u01ca\u01f3\3\2\2\2\u01cb\u01cd\4\62;\2\u01cc\u01cb\3\2\2\2\u01cd"+
		"\u01ce\3\2\2\2\u01ce\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d0\3\2"+
		"\2\2\u01d0\u01f3\5\u0081A\2\u01d1\u01d2\7\62\2\2\u01d2\u01d6\7z\2\2\u01d3"+
		"\u01d4\7\62\2\2\u01d4\u01d6\7Z\2\2\u01d5\u01d1\3\2\2\2\u01d5\u01d3\3\2"+
		"\2\2\u01d6\u01da\3\2\2\2\u01d7\u01d9\5y=\2\u01d8\u01d7\3\2\2\2\u01d9\u01dc"+
		"\3\2\2\2\u01da\u01d8\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01e4\3\2\2\2\u01dc"+
		"\u01da\3\2\2\2\u01dd\u01e1\7\60\2\2\u01de\u01e0\5y=\2\u01df\u01de\3\2"+
		"\2\2\u01e0\u01e3\3\2\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2"+
		"\u01e5\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e4\u01dd\3\2\2\2\u01e4\u01e5\3\2"+
		"\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01e8\t\5\2\2\u01e7\u01e9\t\6\2\2\u01e8"+
		"\u01e7\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01eb\3\2\2\2\u01ea\u01ec\4\62"+
		";\2\u01eb\u01ea\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ed"+
		"\u01ee\3\2\2\2\u01ee\u01f0\3\2\2\2\u01ef\u01f1\5\u0081A\2\u01f0\u01ef"+
		"\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f3\3\2\2\2\u01f2\u01a5\3\2\2\2\u01f2"+
		"\u01b6\3\2\2\2\u01f2\u01c3\3\2\2\2\u01f2\u01cc\3\2\2\2\u01f2\u01d5\3\2"+
		"\2\2\u01f3~\3\2\2\2\u01f4\u01f6\t\7\2\2\u01f5\u01f7\t\6\2\2\u01f6\u01f5"+
		"\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01f9\3\2\2\2\u01f8\u01fa\4\62;\2\u01f9"+
		"\u01f8\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fb\u01fc\3\2"+
		"\2\2\u01fc\u0080\3\2\2\2\u01fd\u01fe\t\4\2\2\u01fe\u0082\3\2\2\2\u01ff"+
		"\u0204\7$\2\2\u0200\u0203\5\u008dG\2\u0201\u0203\n\b\2\2\u0202\u0200\3"+
		"\2\2\2\u0202\u0201\3\2\2\2\u0203\u0206\3\2\2\2\u0204\u0202\3\2\2\2\u0204"+
		"\u0205\3\2\2\2\u0205\u0207\3\2\2\2\u0206\u0204\3\2\2\2\u0207\u0213\7$"+
		"\2\2\u0208\u020d\7)\2\2\u0209\u020c\5\u008dG\2\u020a\u020c\n\t\2\2\u020b"+
		"\u0209\3\2\2\2\u020b\u020a\3\2\2\2\u020c\u020f\3\2\2\2\u020d\u020b\3\2"+
		"\2\2\u020d\u020e\3\2\2\2\u020e\u0210\3\2\2\2\u020f\u020d\3\2\2\2\u0210"+
		"\u0213\7)\2\2\u0211\u0213\5\u0085C\2\u0212\u01ff\3\2\2\2\u0212\u0208\3"+
		"\2\2\2\u0212\u0211\3\2\2\2\u0213\u0084\3\2\2\2\u0214\u0215\7)\2\2\u0215"+
		"\u0216\7)\2\2\u0216\u0217\7)\2\2\u0217\u021b\3\2\2\2\u0218\u021a\5\u0087"+
		"D\2\u0219\u0218\3\2\2\2\u021a\u021d\3\2\2\2\u021b\u021c\3\2\2\2\u021b"+
		"\u0219\3\2\2\2\u021c\u021e\3\2\2\2\u021d\u021b\3\2\2\2\u021e\u021f\7)"+
		"\2\2\u021f\u0220\7)\2\2\u0220\u022f\7)\2\2\u0221\u0222\7$\2\2\u0222\u0223"+
		"\7$\2\2\u0223\u0224\7$\2\2\u0224\u0228\3\2\2\2\u0225\u0227\5\u0087D\2"+
		"\u0226\u0225\3\2\2\2\u0227\u022a\3\2\2\2\u0228\u0229\3\2\2\2\u0228\u0226"+
		"\3\2\2\2\u0229\u022b\3\2\2\2\u022a\u0228\3\2\2\2\u022b\u022c\7$\2\2\u022c"+
		"\u022d\7$\2\2\u022d\u022f\7$\2\2\u022e\u0214\3\2\2\2\u022e\u0221\3\2\2"+
		"\2\u022f\u0086\3\2\2\2\u0230\u0233\5\u0089E\2\u0231\u0233\5\u008bF\2\u0232"+
		"\u0230\3\2\2\2\u0232\u0231\3\2\2\2\u0233\u0088\3\2\2\2\u0234\u0235\n\n"+
		"\2\2\u0235\u008a\3\2\2\2\u0236\u0237\7^\2\2\u0237\u023b\13\2\2\2\u0238"+
		"\u0239\7^\2\2\u0239\u023b\t\13\2\2\u023a\u0236\3\2\2\2\u023a\u0238\3\2"+
		"\2\2\u023b\u008c\3\2\2\2\u023c\u023d\7^\2\2\u023d\u0241\t\f\2\2\u023e"+
		"\u0241\5\u0091I\2\u023f\u0241\5\u008fH\2\u0240\u023c\3\2\2\2\u0240\u023e"+
		"\3\2\2\2\u0240\u023f\3\2\2\2\u0241\u008e\3\2\2\2\u0242\u0243\7^\2\2\u0243"+
		"\u0244\4\62\65\2\u0244\u0245\4\629\2\u0245\u024c\4\629\2\u0246\u0247\7"+
		"^\2\2\u0247\u0248\4\629\2\u0248\u024c\4\629\2\u0249\u024a\7^\2\2\u024a"+
		"\u024c\4\629\2\u024b\u0242\3\2\2\2\u024b\u0246\3\2\2\2\u024b\u0249\3\2"+
		"\2\2\u024c\u0090\3\2\2\2\u024d\u024e\7^\2\2\u024e\u024f\7w\2\2\u024f\u0250"+
		"\5y=\2\u0250\u0251\5y=\2\u0251\u0252\5y=\2\u0252\u0253\5y=\2\u0253\u0092"+
		"\3\2\2\2\u0254\u0259\5\u0095K\2\u0255\u0258\5\u0095K\2\u0256\u0258\5\u0097"+
		"L\2\u0257\u0255\3\2\2\2\u0257\u0256\3\2\2\2\u0258\u025b\3\2\2\2\u0259"+
		"\u0257\3\2\2\2\u0259\u025a\3\2\2\2\u025a\u0094\3\2\2\2\u025b\u0259\3\2"+
		"\2\2\u025c\u025d\t\r\2\2\u025d\u0096\3\2\2\2\u025e\u025f\t\16\2\2\u025f"+
		"\u0098\3\2\2\2\u0260\u0262\t\17\2\2\u0261\u0260\3\2\2\2\u0262\u0263\3"+
		"\2\2\2\u0263\u0261\3\2\2\2\u0263\u0264\3\2\2\2\u0264\u0265\3\2\2\2\u0265"+
		"\u0266\bM\2\2\u0266\u009a\3\2\2\2\u0267\u0268\7\61\2\2\u0268\u0269\7\61"+
		"\2\2\u0269\u026d\3\2\2\2\u026a\u026c\n\13\2\2\u026b\u026a\3\2\2\2\u026c"+
		"\u026f\3\2\2\2\u026d\u026b\3\2\2\2\u026d\u026e\3\2\2\2\u026e\u0275\3\2"+
		"\2\2\u026f\u026d\3\2\2\2\u0270\u0272\7\17\2\2\u0271\u0270\3\2\2\2\u0271"+
		"\u0272\3\2\2\2\u0272\u0273\3\2\2\2\u0273\u0276\7\f\2\2\u0274\u0276\7\2"+
		"\2\3\u0275\u0271\3\2\2\2\u0275\u0274\3\2\2\2\u0276\u0277\3\2\2\2\u0277"+
		"\u0278\bN\2\2\u0278\u009c\3\2\2\2\u0279\u027a\7\61\2\2\u027a\u027b\7,"+
		"\2\2\u027b\u027f\3\2\2\2\u027c\u027e\13\2\2\2\u027d\u027c\3\2\2\2\u027e"+
		"\u0281\3\2\2\2\u027f\u0280\3\2\2\2\u027f\u027d\3\2\2\2\u0280\u0282\3\2"+
		"\2\2\u0281\u027f\3\2\2\2\u0282\u0283\7,\2\2\u0283\u0284\7\61\2\2\u0284"+
		"\u0285\3\2\2\2\u0285\u0286\bO\2\2\u0286\u009e\3\2\2\2\62\2\u00f3\u0185"+
		"\u0188\u018f\u0192\u0195\u019b\u019e\u01a7\u01ad\u01b1\u01b4\u01ba\u01bd"+
		"\u01c0\u01c5\u01c9\u01ce\u01d5\u01da\u01e1\u01e4\u01e8\u01ed\u01f0\u01f2"+
		"\u01f6\u01fb\u0202\u0204\u020b\u020d\u0212\u021b\u0228\u022e\u0232\u023a"+
		"\u0240\u024b\u0257\u0259\u0263\u026d\u0271\u0275\u027f\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}