// $ANTLR 3.5 /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g 2017-01-24 10:17:08

package com.eduworks.resolver.lang.output;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class resolverv2Lexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int ATID=4;
	public static final int COMMENT=5;
	public static final int DOTFUNCTIONID=6;
	public static final int ESC_SEQ=7;
	public static final int FUNCTIONID=8;
	public static final int HEX_DIGIT=9;
	public static final int ID=10;
	public static final int INT=11;
	public static final int OCTAL_ESC=12;
	public static final int SERVLETID=13;
	public static final int STRING=14;
	public static final int UNICODE_ESC=15;
	public static final int WS=16;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public resolverv2Lexer() {} 
	public resolverv2Lexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public resolverv2Lexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g"; }

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:7:7: ( ' ->' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:7:9: ' ->'
			{
			match(" ->"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:8:7: ( '(' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:8:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:9:7: ( ')' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:9:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:10:7: ( ',' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:10:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:11:7: ( ';' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:11:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:12:7: ( '=' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:12:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:13:7: ( '{' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:13:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:14:7: ( '}' )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:14:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:27:5: ( ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ':' )* )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:27:7: ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ':' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:27:30: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ':' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0=='-'||(LA1_0 >= '0' && LA1_0 <= ':')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop1;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "SERVLETID"
	public final void mSERVLETID() throws RecognitionException {
		try {
			int _type = SERVLETID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:30:12: ( '/' ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ':' | '/' )* )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:30:14: '/' ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ':' | '/' )*
			{
			match('/'); 
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:30:40: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ':' | '/' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0=='-'||(LA2_0 >= '/' && LA2_0 <= ':')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( input.LA(1)=='-'||(input.LA(1) >= '/' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SERVLETID"

	// $ANTLR start "ATID"
	public final void mATID() throws RecognitionException {
		try {
			int _type = ATID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:33:6: ( '@' ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:33:8: '@' ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			match('@'); 
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:33:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||LA3_0=='_'||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop3;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ATID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:36:5: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:36:7: ( ' ' | '\\t' | '\\r' | '\\n' )+
			{
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:36:7: ( ' ' | '\\t' | '\\r' | '\\n' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "FUNCTIONID"
	public final void mFUNCTIONID() throws RecognitionException {
		try {
			int _type = FUNCTIONID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:39:13: ( '#' ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:39:15: '#' ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			match('#'); 
			if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:39:28: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= '0' && LA5_0 <= '9')||(LA5_0 >= 'A' && LA5_0 <= 'Z')||LA5_0=='_'||(LA5_0 >= 'a' && LA5_0 <= 'z')) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop5;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FUNCTIONID"

	// $ANTLR start "DOTFUNCTIONID"
	public final void mDOTFUNCTIONID() throws RecognitionException {
		try {
			int _type = DOTFUNCTIONID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:42:16: ( '.' ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:42:18: '.' ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			match('.'); 
			if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:42:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= '0' && LA6_0 <= '9')||(LA6_0 >= 'A' && LA6_0 <= 'Z')||LA6_0=='_'||(LA6_0 >= 'a' && LA6_0 <= 'z')) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop6;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOTFUNCTIONID"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:45:9: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0=='/') ) {
				int LA10_1 = input.LA(2);
				if ( (LA10_1=='/') ) {
					alt10=1;
				}
				else if ( (LA10_1=='*') ) {
					alt10=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 10, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}

			switch (alt10) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:45:11: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
					{
					match("//"); 

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:45:16: (~ ( '\\n' | '\\r' ) )*
					loop7:
					while (true) {
						int alt7=2;
						int LA7_0 = input.LA(1);
						if ( ((LA7_0 >= '\u0000' && LA7_0 <= '\t')||(LA7_0 >= '\u000B' && LA7_0 <= '\f')||(LA7_0 >= '\u000E' && LA7_0 <= '\uFFFF')) ) {
							alt7=1;
						}

						switch (alt7) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop7;
						}
					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:45:30: ( '\\r' )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0=='\r') ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:45:30: '\\r'
							{
							match('\r'); 
							}
							break;

					}

					match('\n'); 
					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:46:7: '/*' ( options {greedy=false; } : . )* '*/'
					{
					match("/*"); 

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:46:12: ( options {greedy=false; } : . )*
					loop9:
					while (true) {
						int alt9=2;
						int LA9_0 = input.LA(1);
						if ( (LA9_0=='*') ) {
							int LA9_1 = input.LA(2);
							if ( (LA9_1=='/') ) {
								alt9=2;
							}
							else if ( ((LA9_1 >= '\u0000' && LA9_1 <= '.')||(LA9_1 >= '0' && LA9_1 <= '\uFFFF')) ) {
								alt9=1;
							}

						}
						else if ( ((LA9_0 >= '\u0000' && LA9_0 <= ')')||(LA9_0 >= '+' && LA9_0 <= '\uFFFF')) ) {
							alt9=1;
						}

						switch (alt9) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:46:40: .
							{
							matchAny(); 
							}
							break;

						default :
							break loop9;
						}
					}

					match("*/"); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:49:5: ( ( '0' .. '9' )+ )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:49:7: ( '0' .. '9' )+
			{
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:49:7: ( '0' .. '9' )+
			int cnt11=0;
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( ((LA11_0 >= '0' && LA11_0 <= '9')) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt11 >= 1 ) break loop11;
					EarlyExitException eee = new EarlyExitException(11, input);
					throw eee;
				}
				cnt11++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:53:5: ( '\"' (~ ( '\"' ) )* '\"' | '\\'' (~ ( '\\'' ) )* '\\'' )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0=='\"') ) {
				alt14=1;
			}
			else if ( (LA14_0=='\'') ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:53:8: '\"' (~ ( '\"' ) )* '\"'
					{
					match('\"'); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:53:12: (~ ( '\"' ) )*
					loop12:
					while (true) {
						int alt12=2;
						int LA12_0 = input.LA(1);
						if ( ((LA12_0 >= '\u0000' && LA12_0 <= '!')||(LA12_0 >= '#' && LA12_0 <= '\uFFFF')) ) {
							alt12=1;
						}

						switch (alt12) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop12;
						}
					}

					match('\"'); 
					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:54:8: '\\'' (~ ( '\\'' ) )* '\\''
					{
					match('\''); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:54:13: (~ ( '\\'' ) )*
					loop13:
					while (true) {
						int alt13=2;
						int LA13_0 = input.LA(1);
						if ( ((LA13_0 >= '\u0000' && LA13_0 <= '&')||(LA13_0 >= '(' && LA13_0 <= '\uFFFF')) ) {
							alt13=1;
						}

						switch (alt13) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop13;
						}
					}

					match('\''); 
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "HEX_DIGIT"
	public final void mHEX_DIGIT() throws RecognitionException {
		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:59:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX_DIGIT"

	// $ANTLR start "ESC_SEQ"
	public final void mESC_SEQ() throws RecognitionException {
		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:63:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
			int alt15=3;
			int LA15_0 = input.LA(1);
			if ( (LA15_0=='\\') ) {
				switch ( input.LA(2) ) {
				case '\"':
				case '\'':
				case '\\':
				case 'b':
				case 'f':
				case 'n':
				case 'r':
				case 't':
					{
					alt15=1;
					}
					break;
				case 'u':
					{
					alt15=2;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					{
					alt15=3;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 15, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}

			switch (alt15) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:63:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
					{
					match('\\'); 
					if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:64:9: UNICODE_ESC
					{
					mUNICODE_ESC(); 

					}
					break;
				case 3 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:65:9: OCTAL_ESC
					{
					mOCTAL_ESC(); 

					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ESC_SEQ"

	// $ANTLR start "OCTAL_ESC"
	public final void mOCTAL_ESC() throws RecognitionException {
		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:70:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
			int alt16=3;
			int LA16_0 = input.LA(1);
			if ( (LA16_0=='\\') ) {
				int LA16_1 = input.LA(2);
				if ( ((LA16_1 >= '0' && LA16_1 <= '3')) ) {
					int LA16_2 = input.LA(3);
					if ( ((LA16_2 >= '0' && LA16_2 <= '7')) ) {
						int LA16_4 = input.LA(4);
						if ( ((LA16_4 >= '0' && LA16_4 <= '7')) ) {
							alt16=1;
						}

						else {
							alt16=2;
						}

					}

					else {
						alt16=3;
					}

				}
				else if ( ((LA16_1 >= '4' && LA16_1 <= '7')) ) {
					int LA16_3 = input.LA(3);
					if ( ((LA16_3 >= '0' && LA16_3 <= '7')) ) {
						alt16=2;
					}

					else {
						alt16=3;
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 16, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 16, 0, input);
				throw nvae;
			}

			switch (alt16) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:70:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:71:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 3 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:72:9: '\\\\' ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OCTAL_ESC"

	// $ANTLR start "UNICODE_ESC"
	public final void mUNICODE_ESC() throws RecognitionException {
		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:77:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:77:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
			{
			match('\\'); 
			match('u'); 
			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UNICODE_ESC"

	@Override
	public void mTokens() throws RecognitionException {
		// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:8: ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | ID | SERVLETID | ATID | WS | FUNCTIONID | DOTFUNCTIONID | COMMENT | INT | STRING )
		int alt17=17;
		switch ( input.LA(1) ) {
		case ' ':
			{
			int LA17_1 = input.LA(2);
			if ( (LA17_1=='-') ) {
				alt17=1;
			}

			else {
				alt17=12;
			}

			}
			break;
		case '(':
			{
			alt17=2;
			}
			break;
		case ')':
			{
			alt17=3;
			}
			break;
		case ',':
			{
			alt17=4;
			}
			break;
		case ';':
			{
			alt17=5;
			}
			break;
		case '=':
			{
			alt17=6;
			}
			break;
		case '{':
			{
			alt17=7;
			}
			break;
		case '}':
			{
			alt17=8;
			}
			break;
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case '_':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt17=9;
			}
			break;
		case '/':
			{
			int LA17_10 = input.LA(2);
			if ( ((LA17_10 >= 'A' && LA17_10 <= 'Z')||LA17_10=='_'||(LA17_10 >= 'a' && LA17_10 <= 'z')) ) {
				alt17=10;
			}
			else if ( (LA17_10=='*'||LA17_10=='/') ) {
				alt17=15;
			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 17, 10, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case '@':
			{
			alt17=11;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
			{
			alt17=12;
			}
			break;
		case '#':
			{
			alt17=13;
			}
			break;
		case '.':
			{
			alt17=14;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt17=16;
			}
			break;
		case '\"':
		case '\'':
			{
			alt17=17;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 17, 0, input);
			throw nvae;
		}
		switch (alt17) {
			case 1 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:10: T__17
				{
				mT__17(); 

				}
				break;
			case 2 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:16: T__18
				{
				mT__18(); 

				}
				break;
			case 3 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:22: T__19
				{
				mT__19(); 

				}
				break;
			case 4 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:28: T__20
				{
				mT__20(); 

				}
				break;
			case 5 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:34: T__21
				{
				mT__21(); 

				}
				break;
			case 6 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:40: T__22
				{
				mT__22(); 

				}
				break;
			case 7 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:46: T__23
				{
				mT__23(); 

				}
				break;
			case 8 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:52: T__24
				{
				mT__24(); 

				}
				break;
			case 9 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:58: ID
				{
				mID(); 

				}
				break;
			case 10 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:61: SERVLETID
				{
				mSERVLETID(); 

				}
				break;
			case 11 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:71: ATID
				{
				mATID(); 

				}
				break;
			case 12 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:76: WS
				{
				mWS(); 

				}
				break;
			case 13 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:79: FUNCTIONID
				{
				mFUNCTIONID(); 

				}
				break;
			case 14 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:90: DOTFUNCTIONID
				{
				mDOTFUNCTIONID(); 

				}
				break;
			case 15 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:104: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 16 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:112: INT
				{
				mINT(); 

				}
				break;
			case 17 :
				// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:1:116: STRING
				{
				mSTRING(); 

				}
				break;

		}
	}



}
