// Generated from /Users/lijiazhi/git/beetl/src/main/java/org/beetl/core/parser/BeetlLexer.g4 by ANTLR 4.9.2
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
			"Exponent", "FloatTypeSuffix", "StringLiteral", "EscapeSequence", "OctalEscape", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2C\u0256\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\5\r\u00ec\n\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37"+
		"\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3&\3&\3\'\3\'\3"+
		"\'\3(\3(\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3-\3-\3.\3.\3.\3.\3.\3/\3/\3/\3"+
		"/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64"+
		"\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\38\39\39\39\3"+
		":\3:\3:\6:\u017c\n:\r:\16:\u017d\3:\5:\u0181\n:\3;\3;\3;\7;\u0186\n;\f"+
		";\16;\u0189\13;\5;\u018b\n;\3;\5;\u018e\n;\3<\3<\6<\u0192\n<\r<\16<\u0193"+
		"\3<\5<\u0197\n<\3=\3=\3>\3>\3?\6?\u019e\n?\r?\16?\u019f\3?\3?\7?\u01a4"+
		"\n?\f?\16?\u01a7\13?\3?\5?\u01aa\n?\3?\5?\u01ad\n?\3?\3?\6?\u01b1\n?\r"+
		"?\16?\u01b2\3?\5?\u01b6\n?\3?\5?\u01b9\n?\3?\6?\u01bc\n?\r?\16?\u01bd"+
		"\3?\3?\5?\u01c2\n?\3?\6?\u01c5\n?\r?\16?\u01c6\3?\3?\3?\3?\3?\5?\u01ce"+
		"\n?\3?\7?\u01d1\n?\f?\16?\u01d4\13?\3?\3?\7?\u01d8\n?\f?\16?\u01db\13"+
		"?\5?\u01dd\n?\3?\3?\5?\u01e1\n?\3?\6?\u01e4\n?\r?\16?\u01e5\3?\5?\u01e9"+
		"\n?\5?\u01eb\n?\3@\3@\5@\u01ef\n@\3@\6@\u01f2\n@\r@\16@\u01f3\3A\3A\3"+
		"B\3B\3B\7B\u01fb\nB\fB\16B\u01fe\13B\3B\3B\3B\3B\7B\u0204\nB\fB\16B\u0207"+
		"\13B\3B\5B\u020a\nB\3C\3C\3C\3C\5C\u0210\nC\3D\3D\3D\3D\3D\3D\3D\3D\3"+
		"D\5D\u021b\nD\3E\3E\3E\3E\3E\3E\3E\3F\3F\3F\7F\u0227\nF\fF\16F\u022a\13"+
		"F\3G\3G\3H\3H\3I\6I\u0231\nI\rI\16I\u0232\3I\3I\3J\3J\3J\3J\7J\u023b\n"+
		"J\fJ\16J\u023e\13J\3J\5J\u0241\nJ\3J\3J\5J\u0245\nJ\3J\3J\3K\3K\3K\3K"+
		"\7K\u024d\nK\fK\16K\u0250\13K\3K\3K\3K\3K\3K\3\u024e\2L\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G"+
		"%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y\2"+
		"{\2}>\177\2\u0081\2\u0083?\u0085\2\u0087\2\u0089\2\u008b@\u008d\2\u008f"+
		"\2\u0091A\u0093B\u0095C\3\2\17\4\2ZZzz\5\2\62;CHch\4\2JJjj\4\2RRrr\4\2"+
		"--//\4\2GGgg\6\2\f\f\17\17$$^^\6\2\f\f\17\17))^^\n\2$$))^^ddhhppttvv\16"+
		"\2&&C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa\u2001\u3042\u3191\u3302\u3381"+
		"\u3402\u3d2f\u4e02\ua001\uf902\ufb01\21\2\62;\u0662\u066b\u06f2\u06fb"+
		"\u0968\u0971\u09e8\u09f1\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71\u0be9\u0bf1"+
		"\u0c68\u0c71\u0ce8\u0cf1\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb\u1042\u104b"+
		"\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u027b\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2"+
		"\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M"+
		"\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2"+
		"\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2"+
		"\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s"+
		"\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2}\3\2\2\2\2\u0083\3\2\2\2\2\u008b\3\2"+
		"\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\3\u0097\3\2\2\2\5"+
		"\u009a\3\2\2\2\7\u009e\3\2\2\2\t\u00a6\3\2\2\2\13\u00ab\3\2\2\2\r\u00b1"+
		"\3\2\2\2\17\u00b8\3\2\2\2\21\u00bf\3\2\2\2\23\u00c6\3\2\2\2\25\u00cc\3"+
		"\2\2\2\27\u00d5\3\2\2\2\31\u00eb\3\2\2\2\33\u00ed\3\2\2\2\35\u00f2\3\2"+
		"\2\2\37\u00fa\3\2\2\2!\u00fe\3\2\2\2#\u0104\3\2\2\2%\u010a\3\2\2\2\'\u0114"+
		"\3\2\2\2)\u0116\3\2\2\2+\u0118\3\2\2\2-\u011a\3\2\2\2/\u011c\3\2\2\2\61"+
		"\u011e\3\2\2\2\63\u0120\3\2\2\2\65\u0123\3\2\2\2\67\u0126\3\2\2\29\u0129"+
		"\3\2\2\2;\u012b\3\2\2\2=\u012d\3\2\2\2?\u012f\3\2\2\2A\u0131\3\2\2\2C"+
		"\u0133\3\2\2\2E\u0136\3\2\2\2G\u0139\3\2\2\2I\u013b\3\2\2\2K\u013e\3\2"+
		"\2\2M\u0140\3\2\2\2O\u0143\3\2\2\2Q\u0145\3\2\2\2S\u0147\3\2\2\2U\u014a"+
		"\3\2\2\2W\u014d\3\2\2\2Y\u014f\3\2\2\2[\u0151\3\2\2\2]\u0156\3\2\2\2_"+
		"\u015b\3\2\2\2a\u0161\3\2\2\2c\u0163\3\2\2\2e\u0165\3\2\2\2g\u0167\3\2"+
		"\2\2i\u0169\3\2\2\2k\u016c\3\2\2\2m\u016f\3\2\2\2o\u0172\3\2\2\2q\u0175"+
		"\3\2\2\2s\u0178\3\2\2\2u\u018a\3\2\2\2w\u018f\3\2\2\2y\u0198\3\2\2\2{"+
		"\u019a\3\2\2\2}\u01ea\3\2\2\2\177\u01ec\3\2\2\2\u0081\u01f5\3\2\2\2\u0083"+
		"\u0209\3\2\2\2\u0085\u020f\3\2\2\2\u0087\u021a\3\2\2\2\u0089\u021c\3\2"+
		"\2\2\u008b\u0223\3\2\2\2\u008d\u022b\3\2\2\2\u008f\u022d\3\2\2\2\u0091"+
		"\u0230\3\2\2\2\u0093\u0236\3\2\2\2\u0095\u0248\3\2\2\2\u0097\u0098\7k"+
		"\2\2\u0098\u0099\7h\2\2\u0099\4\3\2\2\2\u009a\u009b\7h\2\2\u009b\u009c"+
		"\7q\2\2\u009c\u009d\7t\2\2\u009d\6\3\2\2\2\u009e\u009f\7g\2\2\u009f\u00a0"+
		"\7n\2\2\u00a0\u00a1\7u\2\2\u00a1\u00a2\7g\2\2\u00a2\u00a3\7h\2\2\u00a3"+
		"\u00a4\7q\2\2\u00a4\u00a5\7t\2\2\u00a5\b\3\2\2\2\u00a6\u00a7\7g\2\2\u00a7"+
		"\u00a8\7n\2\2\u00a8\u00a9\7u\2\2\u00a9\u00aa\7g\2\2\u00aa\n\3\2\2\2\u00ab"+
		"\u00ac\7y\2\2\u00ac\u00ad\7j\2\2\u00ad\u00ae\7k\2\2\u00ae\u00af\7n\2\2"+
		"\u00af\u00b0\7g\2\2\u00b0\f\3\2\2\2\u00b1\u00b2\7u\2\2\u00b2\u00b3\7y"+
		"\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5\7v\2\2\u00b5\u00b6\7e\2\2\u00b6\u00b7"+
		"\7j\2\2\u00b7\16\3\2\2\2\u00b8\u00b9\7u\2\2\u00b9\u00ba\7g\2\2\u00ba\u00bb"+
		"\7n\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd\7e\2\2\u00bd\u00be\7v\2\2\u00be"+
		"\20\3\2\2\2\u00bf\u00c0\7t\2\2\u00c0\u00c1\7g\2\2\u00c1\u00c2\7v\2\2\u00c2"+
		"\u00c3\7w\2\2\u00c3\u00c4\7t\2\2\u00c4\u00c5\7p\2\2\u00c5\22\3\2\2\2\u00c6"+
		"\u00c7\7d\2\2\u00c7\u00c8\7t\2\2\u00c8\u00c9\7g\2\2\u00c9\u00ca\7c\2\2"+
		"\u00ca\u00cb\7m\2\2\u00cb\24\3\2\2\2\u00cc\u00cd\7e\2\2\u00cd\u00ce\7"+
		"q\2\2\u00ce\u00cf\7p\2\2\u00cf\u00d0\7v\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2"+
		"\7p\2\2\u00d2\u00d3\7w\2\2\u00d3\u00d4\7g\2\2\u00d4\26\3\2\2\2\u00d5\u00d6"+
		"\7x\2\2\u00d6\u00d7\7c\2\2\u00d7\u00d8\7t\2\2\u00d8\30\3\2\2\2\u00d9\u00da"+
		"\7F\2\2\u00da\u00db\7K\2\2\u00db\u00dc\7T\2\2\u00dc\u00dd\7G\2\2\u00dd"+
		"\u00de\7E\2\2\u00de\u00df\7V\2\2\u00df\u00e0\7K\2\2\u00e0\u00e1\7X\2\2"+
		"\u00e1\u00ec\7G\2\2\u00e2\u00e3\7f\2\2\u00e3\u00e4\7k\2\2\u00e4\u00e5"+
		"\7t\2\2\u00e5\u00e6\7g\2\2\u00e6\u00e7\7e\2\2\u00e7\u00e8\7v\2\2\u00e8"+
		"\u00e9\7k\2\2\u00e9\u00ea\7x\2\2\u00ea\u00ec\7g\2\2\u00eb\u00d9\3\2\2"+
		"\2\u00eb\u00e2\3\2\2\2\u00ec\32\3\2\2\2\u00ed\u00ee\7e\2\2\u00ee\u00ef"+
		"\7c\2\2\u00ef\u00f0\7u\2\2\u00f0\u00f1\7g\2\2\u00f1\34\3\2\2\2\u00f2\u00f3"+
		"\7f\2\2\u00f3\u00f4\7g\2\2\u00f4\u00f5\7h\2\2\u00f5\u00f6\7c\2\2\u00f6"+
		"\u00f7\7w\2\2\u00f7\u00f8\7n\2\2\u00f8\u00f9\7v\2\2\u00f9\36\3\2\2\2\u00fa"+
		"\u00fb\7v\2\2\u00fb\u00fc\7t\2\2\u00fc\u00fd\7{\2\2\u00fd \3\2\2\2\u00fe"+
		"\u00ff\7e\2\2\u00ff\u0100\7c\2\2\u0100\u0101\7v\2\2\u0101\u0102\7e\2\2"+
		"\u0102\u0103\7j\2\2\u0103\"\3\2\2\2\u0104\u0105\7%\2\2\u0105\u0106\7c"+
		"\2\2\u0106\u0107\7l\2\2\u0107\u0108\7c\2\2\u0108\u0109\7z\2\2\u0109$\3"+
		"\2\2\2\u010a\u010b\7%\2\2\u010b\u010c\7h\2\2\u010c\u010d\7t\2\2\u010d"+
		"\u010e\7c\2\2\u010e\u010f\7i\2\2\u010f\u0110\7o\2\2\u0110\u0111\7g\2\2"+
		"\u0111\u0112\7p\2\2\u0112\u0113\7v\2\2\u0113&\3\2\2\2\u0114\u0115\7}\2"+
		"\2\u0115(\3\2\2\2\u0116\u0117\7\177\2\2\u0117*\3\2\2\2\u0118\u0119\7*"+
		"\2\2\u0119,\3\2\2\2\u011a\u011b\7+\2\2\u011b.\3\2\2\2\u011c\u011d\7]\2"+
		"\2\u011d\60\3\2\2\2\u011e\u011f\7_\2\2\u011f\62\3\2\2\2\u0120\u0121\7"+
		"-\2\2\u0121\u0122\7-\2\2\u0122\64\3\2\2\2\u0123\u0124\7/\2\2\u0124\u0125"+
		"\7/\2\2\u0125\66\3\2\2\2\u0126\u0127\7\60\2\2\u0127\u0128\7\u0080\2\2"+
		"\u01288\3\2\2\2\u0129\u012a\7-\2\2\u012a:\3\2\2\2\u012b\u012c\7/\2\2\u012c"+
		"<\3\2\2\2\u012d\u012e\7,\2\2\u012e>\3\2\2\2\u012f\u0130\7\61\2\2\u0130"+
		"@\3\2\2\2\u0131\u0132\7\'\2\2\u0132B\3\2\2\2\u0133\u0134\7?\2\2\u0134"+
		"\u0135\7?\2\2\u0135D\3\2\2\2\u0136\u0137\7#\2\2\u0137\u0138\7?\2\2\u0138"+
		"F\3\2\2\2\u0139\u013a\7?\2\2\u013aH\3\2\2\2\u013b\u013c\7@\2\2\u013c\u013d"+
		"\7?\2\2\u013dJ\3\2\2\2\u013e\u013f\7@\2\2\u013fL\3\2\2\2\u0140\u0141\7"+
		">\2\2\u0141\u0142\7?\2\2\u0142N\3\2\2\2\u0143\u0144\7>\2\2\u0144P\3\2"+
		"\2\2\u0145\u0146\7#\2\2\u0146R\3\2\2\2\u0147\u0148\7(\2\2\u0148\u0149"+
		"\7(\2\2\u0149T\3\2\2\2\u014a\u014b\7~\2\2\u014b\u014c\7~\2\2\u014cV\3"+
		"\2\2\2\u014d\u014e\7A\2\2\u014eX\3\2\2\2\u014f\u0150\7B\2\2\u0150Z\3\2"+
		"\2\2\u0151\u0152\7p\2\2\u0152\u0153\7w\2\2\u0153\u0154\7n\2\2\u0154\u0155"+
		"\7n\2\2\u0155\\\3\2\2\2\u0156\u0157\7v\2\2\u0157\u0158\7t\2\2\u0158\u0159"+
		"\7w\2\2\u0159\u015a\7g\2\2\u015a^\3\2\2\2\u015b\u015c\7h\2\2\u015c\u015d"+
		"\7c\2\2\u015d\u015e\7n\2\2\u015e\u015f\7u\2\2\u015f\u0160\7g\2\2\u0160"+
		"`\3\2\2\2\u0161\u0162\7=\2\2\u0162b\3\2\2\2\u0163\u0164\7.\2\2\u0164d"+
		"\3\2\2\2\u0165\u0166\7<\2\2\u0166f\3\2\2\2\u0167\u0168\7\60\2\2\u0168"+
		"h\3\2\2\2\u0169\u016a\7k\2\2\u016a\u016b\7p\2\2\u016bj\3\2\2\2\u016c\u016d"+
		"\7>\2\2\u016d\u016e\7\u0080\2\2\u016el\3\2\2\2\u016f\u0170\7>\2\2\u0170"+
		"\u0171\7%\2\2\u0171n\3\2\2\2\u0172\u0173\7\u0080\2\2\u0173\u0174\7@\2"+
		"\2\u0174p\3\2\2\2\u0175\u0176\7>\2\2\u0176\u0177\7`\2\2\u0177r\3\2\2\2"+
		"\u0178\u0179\7\62\2\2\u0179\u017b\t\2\2\2\u017a\u017c\5y=\2\u017b\u017a"+
		"\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017e"+
		"\u0180\3\2\2\2\u017f\u0181\5{>\2\u0180\u017f\3\2\2\2\u0180\u0181\3\2\2"+
		"\2\u0181t\3\2\2\2\u0182\u018b\7\62\2\2\u0183\u0187\4\63;\2\u0184\u0186"+
		"\4\62;\2\u0185\u0184\3\2\2\2\u0186\u0189\3\2\2\2\u0187\u0185\3\2\2\2\u0187"+
		"\u0188\3\2\2\2\u0188\u018b\3\2\2\2\u0189\u0187\3\2\2\2\u018a\u0182\3\2"+
		"\2\2\u018a\u0183\3\2\2\2\u018b\u018d\3\2\2\2\u018c\u018e\5{>\2\u018d\u018c"+
		"\3\2\2\2\u018d\u018e\3\2\2\2\u018ev\3\2\2\2\u018f\u0191\7\62\2\2\u0190"+
		"\u0192\4\629\2\u0191\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193\u0191\3\2"+
		"\2\2\u0193\u0194\3\2\2\2\u0194\u0196\3\2\2\2\u0195\u0197\5{>\2\u0196\u0195"+
		"\3\2\2\2\u0196\u0197\3\2\2\2\u0197x\3\2\2\2\u0198\u0199\t\3\2\2\u0199"+
		"z\3\2\2\2\u019a\u019b\t\4\2\2\u019b|\3\2\2\2\u019c\u019e\4\62;\2\u019d"+
		"\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u019d\3\2\2\2\u019f\u01a0\3\2"+
		"\2\2\u01a0\u01a1\3\2\2\2\u01a1\u01a5\7\60\2\2\u01a2\u01a4\4\62;\2\u01a3"+
		"\u01a2\3\2\2\2\u01a4\u01a7\3\2\2\2\u01a5\u01a3\3\2\2\2\u01a5\u01a6\3\2"+
		"\2\2\u01a6\u01a9\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a8\u01aa\5\177@\2\u01a9"+
		"\u01a8\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01ac\3\2\2\2\u01ab\u01ad\5\u0081"+
		"A\2\u01ac\u01ab\3\2\2\2\u01ac\u01ad\3\2\2\2\u01ad\u01eb\3\2\2\2\u01ae"+
		"\u01b0\7\60\2\2\u01af\u01b1\4\62;\2\u01b0\u01af\3\2\2\2\u01b1\u01b2\3"+
		"\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3\u01b5\3\2\2\2\u01b4"+
		"\u01b6\5\177@\2\u01b5\u01b4\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6\u01b8\3"+
		"\2\2\2\u01b7\u01b9\5\u0081A\2\u01b8\u01b7\3\2\2\2\u01b8\u01b9\3\2\2\2"+
		"\u01b9\u01eb\3\2\2\2\u01ba\u01bc\4\62;\2\u01bb\u01ba\3\2\2\2\u01bc\u01bd"+
		"\3\2\2\2\u01bd\u01bb\3\2\2\2\u01bd\u01be\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf"+
		"\u01c1\5\177@\2\u01c0\u01c2\5\u0081A\2\u01c1\u01c0\3\2\2\2\u01c1\u01c2"+
		"\3\2\2\2\u01c2\u01eb\3\2\2\2\u01c3\u01c5\4\62;\2\u01c4\u01c3\3\2\2\2\u01c5"+
		"\u01c6\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c8\3\2"+
		"\2\2\u01c8\u01eb\5\u0081A\2\u01c9\u01ca\7\62\2\2\u01ca\u01ce\7z\2\2\u01cb"+
		"\u01cc\7\62\2\2\u01cc\u01ce\7Z\2\2\u01cd\u01c9\3\2\2\2\u01cd\u01cb\3\2"+
		"\2\2\u01ce\u01d2\3\2\2\2\u01cf\u01d1\5y=\2\u01d0\u01cf\3\2\2\2\u01d1\u01d4"+
		"\3\2\2\2\u01d2\u01d0\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01dc\3\2\2\2\u01d4"+
		"\u01d2\3\2\2\2\u01d5\u01d9\7\60\2\2\u01d6\u01d8\5y=\2\u01d7\u01d6\3\2"+
		"\2\2\u01d8\u01db\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da\3\2\2\2\u01da"+
		"\u01dd\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc\u01d5\3\2\2\2\u01dc\u01dd\3\2"+
		"\2\2\u01dd\u01de\3\2\2\2\u01de\u01e0\t\5\2\2\u01df\u01e1\t\6\2\2\u01e0"+
		"\u01df\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e3\3\2\2\2\u01e2\u01e4\4\62"+
		";\2\u01e3\u01e2\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e5"+
		"\u01e6\3\2\2\2\u01e6\u01e8\3\2\2\2\u01e7\u01e9\5\u0081A\2\u01e8\u01e7"+
		"\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01eb\3\2\2\2\u01ea\u019d\3\2\2\2\u01ea"+
		"\u01ae\3\2\2\2\u01ea\u01bb\3\2\2\2\u01ea\u01c4\3\2\2\2\u01ea\u01cd\3\2"+
		"\2\2\u01eb~\3\2\2\2\u01ec\u01ee\t\7\2\2\u01ed\u01ef\t\6\2\2\u01ee\u01ed"+
		"\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01f1\3\2\2\2\u01f0\u01f2\4\62;\2\u01f1"+
		"\u01f0\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f3\u01f4\3\2"+
		"\2\2\u01f4\u0080\3\2\2\2\u01f5\u01f6\t\4\2\2\u01f6\u0082\3\2\2\2\u01f7"+
		"\u01fc\7$\2\2\u01f8\u01fb\5\u0085C\2\u01f9\u01fb\n\b\2\2\u01fa\u01f8\3"+
		"\2\2\2\u01fa\u01f9\3\2\2\2\u01fb\u01fe\3\2\2\2\u01fc\u01fa\3\2\2\2\u01fc"+
		"\u01fd\3\2\2\2\u01fd\u01ff\3\2\2\2\u01fe\u01fc\3\2\2\2\u01ff\u020a\7$"+
		"\2\2\u0200\u0205\7)\2\2\u0201\u0204\5\u0085C\2\u0202\u0204\n\t\2\2\u0203"+
		"\u0201\3\2\2\2\u0203\u0202\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0203\3\2"+
		"\2\2\u0205\u0206\3\2\2\2\u0206\u0208\3\2\2\2\u0207\u0205\3\2\2\2\u0208"+
		"\u020a\7)\2\2\u0209\u01f7\3\2\2\2\u0209\u0200\3\2\2\2\u020a\u0084\3\2"+
		"\2\2\u020b\u020c\7^\2\2\u020c\u0210\t\n\2\2\u020d\u0210\5\u0089E\2\u020e"+
		"\u0210\5\u0087D\2\u020f\u020b\3\2\2\2\u020f\u020d\3\2\2\2\u020f\u020e"+
		"\3\2\2\2\u0210\u0086\3\2\2\2\u0211\u0212\7^\2\2\u0212\u0213\4\62\65\2"+
		"\u0213\u0214\4\629\2\u0214\u021b\4\629\2\u0215\u0216\7^\2\2\u0216\u0217"+
		"\4\629\2\u0217\u021b\4\629\2\u0218\u0219\7^\2\2\u0219\u021b\4\629\2\u021a"+
		"\u0211\3\2\2\2\u021a\u0215\3\2\2\2\u021a\u0218\3\2\2\2\u021b\u0088\3\2"+
		"\2\2\u021c\u021d\7^\2\2\u021d\u021e\7w\2\2\u021e\u021f\5y=\2\u021f\u0220"+
		"\5y=\2\u0220\u0221\5y=\2\u0221\u0222\5y=\2\u0222\u008a\3\2\2\2\u0223\u0228"+
		"\5\u008dG\2\u0224\u0227\5\u008dG\2\u0225\u0227\5\u008fH\2\u0226\u0224"+
		"\3\2\2\2\u0226\u0225\3\2\2\2\u0227\u022a\3\2\2\2\u0228\u0226\3\2\2\2\u0228"+
		"\u0229\3\2\2\2\u0229\u008c\3\2\2\2\u022a\u0228\3\2\2\2\u022b\u022c\t\13"+
		"\2\2\u022c\u008e\3\2\2\2\u022d\u022e\t\f\2\2\u022e\u0090\3\2\2\2\u022f"+
		"\u0231\t\r\2\2\u0230\u022f\3\2\2\2\u0231\u0232\3\2\2\2\u0232\u0230\3\2"+
		"\2\2\u0232\u0233\3\2\2\2\u0233\u0234\3\2\2\2\u0234\u0235\bI\2\2\u0235"+
		"\u0092\3\2\2\2\u0236\u0237\7\61\2\2\u0237\u0238\7\61\2\2\u0238\u023c\3"+
		"\2\2\2\u0239\u023b\n\16\2\2\u023a\u0239\3\2\2\2\u023b\u023e\3\2\2\2\u023c"+
		"\u023a\3\2\2\2\u023c\u023d\3\2\2\2\u023d\u0244\3\2\2\2\u023e\u023c\3\2"+
		"\2\2\u023f\u0241\7\17\2\2\u0240\u023f\3\2\2\2\u0240\u0241\3\2\2\2\u0241"+
		"\u0242\3\2\2\2\u0242\u0245\7\f\2\2\u0243\u0245\7\2\2\3\u0244\u0240\3\2"+
		"\2\2\u0244\u0243\3\2\2\2\u0245\u0246\3\2\2\2\u0246\u0247\bJ\2\2\u0247"+
		"\u0094\3\2\2\2\u0248\u0249\7\61\2\2\u0249\u024a\7,\2\2\u024a\u024e\3\2"+
		"\2\2\u024b\u024d\13\2\2\2\u024c\u024b\3\2\2\2\u024d\u0250\3\2\2\2\u024e"+
		"\u024f\3\2\2\2\u024e\u024c\3\2\2\2\u024f\u0251\3\2\2\2\u0250\u024e\3\2"+
		"\2\2\u0251\u0252\7,\2\2\u0252\u0253\7\61\2\2\u0253\u0254\3\2\2\2\u0254"+
		"\u0255\bK\2\2\u0255\u0096\3\2\2\2-\2\u00eb\u017d\u0180\u0187\u018a\u018d"+
		"\u0193\u0196\u019f\u01a5\u01a9\u01ac\u01b2\u01b5\u01b8\u01bd\u01c1\u01c6"+
		"\u01cd\u01d2\u01d9\u01dc\u01e0\u01e5\u01e8\u01ea\u01ee\u01f3\u01fa\u01fc"+
		"\u0203\u0205\u0209\u020f\u021a\u0226\u0228\u0232\u023c\u0240\u0244\u024e"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}