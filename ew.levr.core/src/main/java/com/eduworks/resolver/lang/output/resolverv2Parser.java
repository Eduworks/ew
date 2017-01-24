// $ANTLR 3.5 /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g 2017-01-24 10:17:08

package com.eduworks.resolver.lang.output;

import org.json.JSONObject;
import org.json.JSONException;
import com.eduworks.lang.EwMap;
import com.eduworks.lang.json.impl.EwJsonObject;
import java.util.Iterator;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class resolverv2Parser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ATID", "COMMENT", "DOTFUNCTIONID", 
		"ESC_SEQ", "FUNCTIONID", "HEX_DIGIT", "ID", "INT", "OCTAL_ESC", "SERVLETID", 
		"STRING", "UNICODE_ESC", "WS", "' ->'", "'('", "')'", "','", "';'", "'='", 
		"'{'", "'}'"
	};
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public resolverv2Parser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public resolverv2Parser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return resolverv2Parser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g"; }


		public boolean debug = false;
		public EwMap<String,JSONObject> ids = new EwMap<String,JSONObject>();
		public EwMap<String,JSONObject> servlets = new EwMap<String,JSONObject>();
		public EwMap<String,JSONObject> functions = new EwMap<String,JSONObject>();
		public JSONObject obj = new EwJsonObject();
		public Stack<JSONObject> stk = new Stack<JSONObject>();



	// $ANTLR start "parse"
	// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:79:1: parse : ( ( decl ';' ( WS )? ) ( parse )? | COMMENT ( WS )? ( parse )? | WS ( parse )? | EOF );
	public final void parse() throws Exception {
		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:3: ( ( decl ';' ( WS )? ) ( parse )? | COMMENT ( WS )? ( parse )? | WS ( parse )? | EOF )
			int alt6=4;
			switch ( input.LA(1) ) {
			case FUNCTIONID:
			case ID:
			case SERVLETID:
				{
				alt6=1;
				}
				break;
			case COMMENT:
				{
				alt6=2;
				}
				break;
			case WS:
				{
				alt6=3;
				}
				break;
			case EOF:
				{
				alt6=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:5: ( decl ';' ( WS )? ) ( parse )?
					{
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:5: ( decl ';' ( WS )? )
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:6: decl ';' ( WS )?
					{
					pushFollow(FOLLOW_decl_in_parse572);
					decl();
					state._fsp--;

					match(input,21,FOLLOW_21_in_parse573); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:13: ( WS )?
					int alt1=2;
					int LA1_0 = input.LA(1);
					if ( (LA1_0==WS) ) {
						alt1=1;
					}
					switch (alt1) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:13: WS
							{
							match(input,WS,FOLLOW_WS_in_parse574); 
							}
							break;

					}

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:18: ( parse )?
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0==EOF||LA2_0==COMMENT||LA2_0==FUNCTIONID||LA2_0==ID||LA2_0==SERVLETID||LA2_0==WS) ) {
						alt2=1;
					}
					switch (alt2) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:80:18: parse
							{
							pushFollow(FOLLOW_parse_in_parse578);
							parse();
							state._fsp--;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:81:5: COMMENT ( WS )? ( parse )?
					{
					match(input,COMMENT,FOLLOW_COMMENT_in_parse585); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:81:13: ( WS )?
					int alt3=2;
					int LA3_0 = input.LA(1);
					if ( (LA3_0==WS) ) {
						alt3=1;
					}
					switch (alt3) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:81:13: WS
							{
							match(input,WS,FOLLOW_WS_in_parse587); 
							}
							break;

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:81:17: ( parse )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==EOF||LA4_0==COMMENT||LA4_0==FUNCTIONID||LA4_0==ID||LA4_0==SERVLETID||LA4_0==WS) ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:81:17: parse
							{
							pushFollow(FOLLOW_parse_in_parse590);
							parse();
							state._fsp--;

							}
							break;

					}

					}
					break;
				case 3 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:82:5: WS ( parse )?
					{
					match(input,WS,FOLLOW_WS_in_parse597); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:82:8: ( parse )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0==EOF||LA5_0==COMMENT||LA5_0==FUNCTIONID||LA5_0==ID||LA5_0==SERVLETID||LA5_0==WS) ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:82:8: parse
							{
							pushFollow(FOLLOW_parse_in_parse599);
							parse();
							state._fsp--;

							}
							break;

					}

					}
					break;
				case 4 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:83:5: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_parse606); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "parse"



	// $ANTLR start "decl"
	// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:86:1: decl : (i= ID ( WS )? '=' ( WS )? ( functioncall | '{' param '}' ) |x= ID ( WS )? '=' ( WS )? y= ID ( dotfunctioncall )? |s= SERVLETID ( WS )? ' ->' ( WS )? i= ID |s= SERVLETID ( WS )? '=' ( WS )? i= ID |s= FUNCTIONID ( WS )? ' ->' ( WS )? i= ID |s= FUNCTIONID ( WS )? '=' ( WS )? i= ID );
	public final void decl() throws  Exception {
		Token i=null;
		Token x=null;
		Token y=null;
		Token s=null;

		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:87:2: (i= ID ( WS )? '=' ( WS )? ( functioncall | '{' param '}' ) |x= ID ( WS )? '=' ( WS )? y= ID ( dotfunctioncall )? |s= SERVLETID ( WS )? ' ->' ( WS )? i= ID |s= SERVLETID ( WS )? '=' ( WS )? i= ID |s= FUNCTIONID ( WS )? ' ->' ( WS )? i= ID |s= FUNCTIONID ( WS )? '=' ( WS )? i= ID )
			int alt21=6;
			switch ( input.LA(1) ) {
			case ID:
				{
				int LA21_1 = input.LA(2);
				if ( (LA21_1==WS) ) {
					int LA21_4 = input.LA(3);
					if ( (LA21_4==22) ) {
						switch ( input.LA(4) ) {
						case WS:
							{
							int LA21_12 = input.LA(5);
							if ( (LA21_12==FUNCTIONID||LA21_12==23) ) {
								alt21=1;
							}
							else if ( (LA21_12==ID) ) {
								alt21=2;
							}

							else {
								int nvaeMark = input.mark();
								try {
									for (int nvaeConsume = 0; nvaeConsume < 5 - 1; nvaeConsume++) {
										input.consume();
									}
									NoViableAltException nvae =
										new NoViableAltException("", 21, 12, input);
									throw nvae;
								} finally {
									input.rewind(nvaeMark);
								}
							}

							}
							break;
						case FUNCTIONID:
						case 23:
							{
							alt21=1;
							}
							break;
						case ID:
							{
							alt21=2;
							}
							break;
						default:
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 21, 5, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 21, 4, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA21_1==22) ) {
					switch ( input.LA(3) ) {
					case WS:
						{
						int LA21_12 = input.LA(4);
						if ( (LA21_12==FUNCTIONID||LA21_12==23) ) {
							alt21=1;
						}
						else if ( (LA21_12==ID) ) {
							alt21=2;
						}

						else {
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 21, 12, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

						}
						break;
					case FUNCTIONID:
					case 23:
						{
						alt21=1;
						}
						break;
					case ID:
						{
						alt21=2;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 21, 5, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 21, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case SERVLETID:
				{
				switch ( input.LA(2) ) {
				case WS:
					{
					int LA21_6 = input.LA(3);
					if ( (LA21_6==17) ) {
						alt21=3;
					}
					else if ( (LA21_6==22) ) {
						alt21=4;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 21, 6, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 17:
					{
					alt21=3;
					}
					break;
				case 22:
					{
					alt21=4;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 21, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case FUNCTIONID:
				{
				switch ( input.LA(2) ) {
				case WS:
					{
					int LA21_9 = input.LA(3);
					if ( (LA21_9==17) ) {
						alt21=5;
					}
					else if ( (LA21_9==22) ) {
						alt21=6;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 21, 9, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 17:
					{
					alt21=5;
					}
					break;
				case 22:
					{
					alt21=6;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 21, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 21, 0, input);
				throw nvae;
			}
			switch (alt21) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:87:4: i= ID ( WS )? '=' ( WS )? ( functioncall | '{' param '}' )
					{
					i=(Token)match(input,ID,FOLLOW_ID_in_decl619); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:87:9: ( WS )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( (LA7_0==WS) ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:87:9: WS
							{
							match(input,WS,FOLLOW_WS_in_decl621); 
							}
							break;

					}

					match(input,22,FOLLOW_22_in_decl623); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:87:15: ( WS )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==WS) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:87:15: WS
							{
							match(input,WS,FOLLOW_WS_in_decl624); 
							}
							break;

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:88:3: ( functioncall | '{' param '}' )
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==FUNCTIONID) ) {
						alt9=1;
					}
					else if ( (LA9_0==23) ) {
						alt9=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 9, 0, input);
						throw nvae;
					}

					switch (alt9) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:88:5: functioncall
							{
							pushFollow(FOLLOW_functioncall_in_decl631);
							functioncall();
							state._fsp--;

							ids.put(i.getText(),obj);obj = new EwJsonObject();
							}
							break;
						case 2 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:89:5: '{' param '}'
							{
							match(input,23,FOLLOW_23_in_decl642); 
							pushFollow(FOLLOW_param_in_decl643);
							param();
							state._fsp--;

							match(input,24,FOLLOW_24_in_decl644); 
							ids.put(i.getText(),obj);obj = new EwJsonObject();
							}
							break;

					}

					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:91:4: x= ID ( WS )? '=' ( WS )? y= ID ( dotfunctioncall )?
					{
					x=(Token)match(input,ID,FOLLOW_ID_in_decl660); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:91:9: ( WS )?
					int alt10=2;
					int LA10_0 = input.LA(1);
					if ( (LA10_0==WS) ) {
						alt10=1;
					}
					switch (alt10) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:91:9: WS
							{
							match(input,WS,FOLLOW_WS_in_decl662); 
							}
							break;

					}

					match(input,22,FOLLOW_22_in_decl664); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:91:15: ( WS )?
					int alt11=2;
					int LA11_0 = input.LA(1);
					if ( (LA11_0==WS) ) {
						alt11=1;
					}
					switch (alt11) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:91:15: WS
							{
							match(input,WS,FOLLOW_WS_in_decl665); 
							}
							break;

					}

					y=(Token)match(input,ID,FOLLOW_ID_in_decl670); 
					obj=ids.get(y.getText());
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:92:3: ( dotfunctioncall )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==DOTFUNCTIONID) ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:92:3: dotfunctioncall
							{
							pushFollow(FOLLOW_dotfunctioncall_in_decl680);
							dotfunctioncall();
							state._fsp--;

							}
							break;

					}

					ids.put(x.getText(),obj);obj = new EwJsonObject();
					}
					break;
				case 3 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:93:4: s= SERVLETID ( WS )? ' ->' ( WS )? i= ID
					{
					s=(Token)match(input,SERVLETID,FOLLOW_SERVLETID_in_decl693); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:93:16: ( WS )?
					int alt13=2;
					int LA13_0 = input.LA(1);
					if ( (LA13_0==WS) ) {
						alt13=1;
					}
					switch (alt13) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:93:16: WS
							{
							match(input,WS,FOLLOW_WS_in_decl695); 
							}
							break;

					}

					match(input,17,FOLLOW_17_in_decl698); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:93:26: ( WS )?
					int alt14=2;
					int LA14_0 = input.LA(1);
					if ( (LA14_0==WS) ) {
						alt14=1;
					}
					switch (alt14) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:93:26: WS
							{
							match(input,WS,FOLLOW_WS_in_decl700); 
							}
							break;

					}

					i=(Token)match(input,ID,FOLLOW_ID_in_decl705); 
					servlets.put(s.getText(),ids.get(i.getText()));
					}
					break;
				case 4 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:94:4: s= SERVLETID ( WS )? '=' ( WS )? i= ID
					{
					s=(Token)match(input,SERVLETID,FOLLOW_SERVLETID_in_decl716); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:94:16: ( WS )?
					int alt15=2;
					int LA15_0 = input.LA(1);
					if ( (LA15_0==WS) ) {
						alt15=1;
					}
					switch (alt15) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:94:16: WS
							{
							match(input,WS,FOLLOW_WS_in_decl718); 
							}
							break;

					}

					match(input,22,FOLLOW_22_in_decl721); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:94:24: ( WS )?
					int alt16=2;
					int LA16_0 = input.LA(1);
					if ( (LA16_0==WS) ) {
						alt16=1;
					}
					switch (alt16) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:94:24: WS
							{
							match(input,WS,FOLLOW_WS_in_decl723); 
							}
							break;

					}

					i=(Token)match(input,ID,FOLLOW_ID_in_decl728); 
					servlets.put(s.getText(),ids.get(i.getText()));
					}
					break;
				case 5 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:95:4: s= FUNCTIONID ( WS )? ' ->' ( WS )? i= ID
					{
					s=(Token)match(input,FUNCTIONID,FOLLOW_FUNCTIONID_in_decl739); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:95:17: ( WS )?
					int alt17=2;
					int LA17_0 = input.LA(1);
					if ( (LA17_0==WS) ) {
						alt17=1;
					}
					switch (alt17) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:95:17: WS
							{
							match(input,WS,FOLLOW_WS_in_decl741); 
							}
							break;

					}

					match(input,17,FOLLOW_17_in_decl744); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:95:27: ( WS )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==WS) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:95:27: WS
							{
							match(input,WS,FOLLOW_WS_in_decl746); 
							}
							break;

					}

					i=(Token)match(input,ID,FOLLOW_ID_in_decl751); 
					functions.put(s.getText(),ids.get(i.getText()));
					}
					break;
				case 6 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:96:4: s= FUNCTIONID ( WS )? '=' ( WS )? i= ID
					{
					s=(Token)match(input,FUNCTIONID,FOLLOW_FUNCTIONID_in_decl762); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:96:17: ( WS )?
					int alt19=2;
					int LA19_0 = input.LA(1);
					if ( (LA19_0==WS) ) {
						alt19=1;
					}
					switch (alt19) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:96:17: WS
							{
							match(input,WS,FOLLOW_WS_in_decl764); 
							}
							break;

					}

					match(input,22,FOLLOW_22_in_decl767); 
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:96:25: ( WS )?
					int alt20=2;
					int LA20_0 = input.LA(1);
					if ( (LA20_0==WS) ) {
						alt20=1;
					}
					switch (alt20) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:96:25: WS
							{
							match(input,WS,FOLLOW_WS_in_decl769); 
							}
							break;

					}

					i=(Token)match(input,ID,FOLLOW_ID_in_decl774); 
					functions.put(s.getText(),ids.get(i.getText()));
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "decl"



	// $ANTLR start "functioncall"
	// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:100:1: functioncall : x= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? ;
	public final void functioncall() throws  Exception {
		Token x=null;

		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:101:2: (x= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:101:4: x= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )?
			{
			x=(Token)match(input,FUNCTIONID,FOLLOW_FUNCTIONID_in_functioncall792); 
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:101:17: ( WS )?
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==WS) ) {
				alt22=1;
			}
			switch (alt22) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:101:17: WS
					{
					match(input,WS,FOLLOW_WS_in_functioncall794); 
					}
					break;

			}

			match(input,18,FOLLOW_18_in_functioncall796); 
			try{obj.put("function",x.getText().substring(1));obj.put("_lineNumber", x.getLine());obj.put("_colNumber", x.getCharPositionInLine());}catch(JSONException e){}
			pushFollow(FOLLOW_param_in_functioncall805);
			param();
			state._fsp--;

			match(input,19,FOLLOW_19_in_functioncall806); 
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:102:12: ( dotfunctioncall )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==DOTFUNCTIONID) ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:102:12: dotfunctioncall
					{
					pushFollow(FOLLOW_dotfunctioncall_in_functioncall808);
					dotfunctioncall();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "functioncall"



	// $ANTLR start "dotfunctioncall"
	// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:105:1: dotfunctioncall : y= DOTFUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? ;
	public final void dotfunctioncall() throws  Exception {
		Token y=null;

		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:106:2: (y= DOTFUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? )
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:106:4: y= DOTFUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )?
			{
			y=(Token)match(input,DOTFUNCTIONID,FOLLOW_DOTFUNCTIONID_in_dotfunctioncall823); 
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:106:20: ( WS )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( (LA24_0==WS) ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:106:20: WS
					{
					match(input,WS,FOLLOW_WS_in_dotfunctioncall825); 
					}
					break;

			}

			match(input,18,FOLLOW_18_in_dotfunctioncall827); 
			try{stk.push(obj);obj = new EwJsonObject();obj.put("function",y.getText().substring(1));obj.put("_lineNumber", y.getLine());obj.put("_colNumber", y.getCharPositionInLine());}catch(JSONException e){}
			pushFollow(FOLLOW_param_in_dotfunctioncall836);
			param();
			state._fsp--;

			try{obj.put("obj",stk.pop());}catch(JSONException e){}
			match(input,19,FOLLOW_19_in_dotfunctioncall847); 
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:108:7: ( dotfunctioncall )?
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==DOTFUNCTIONID) ) {
				alt25=1;
			}
			switch (alt25) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:108:7: dotfunctioncall
					{
					pushFollow(FOLLOW_dotfunctioncall_in_dotfunctioncall849);
					dotfunctioncall();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "dotfunctioncall"



	// $ANTLR start "param"
	// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:111:1: param : ( ( WS )? (x= ID ( WS )? '=' ( WS )? (y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? |y= STRING |y= INT |y= ID ( dotfunctioncall )? ) |x= ID | ( WS )? ) ( ( WS )? ',' param )? ( WS )? | ( WS )? x= ATID ( ( WS )? ',' param )? ( WS )? );
	public final void param() throws  Exception {
		Token x=null;
		Token y=null;

		try {
			// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:2: ( ( WS )? (x= ID ( WS )? '=' ( WS )? (y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? |y= STRING |y= INT |y= ID ( dotfunctioncall )? ) |x= ID | ( WS )? ) ( ( WS )? ',' param )? ( WS )? | ( WS )? x= ATID ( ( WS )? ',' param )? ( WS )? )
			int alt42=2;
			switch ( input.LA(1) ) {
			case WS:
				{
				int LA42_1 = input.LA(2);
				if ( (LA42_1==ID||LA42_1==WS||(LA42_1 >= 19 && LA42_1 <= 20)||LA42_1==24) ) {
					alt42=1;
				}
				else if ( (LA42_1==ATID) ) {
					alt42=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 42, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case ID:
			case 19:
			case 20:
			case 24:
				{
				alt42=1;
				}
				break;
			case ATID:
				{
				alt42=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 42, 0, input);
				throw nvae;
			}
			switch (alt42) {
				case 1 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:4: ( WS )? (x= ID ( WS )? '=' ( WS )? (y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? |y= STRING |y= INT |y= ID ( dotfunctioncall )? ) |x= ID | ( WS )? ) ( ( WS )? ',' param )? ( WS )?
					{
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:4: ( WS )?
					int alt26=2;
					int LA26_0 = input.LA(1);
					if ( (LA26_0==WS) ) {
						alt26=1;
					}
					switch (alt26) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:4: WS
							{
							match(input,WS,FOLLOW_WS_in_param866); 
							}
							break;

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:8: (x= ID ( WS )? '=' ( WS )? (y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? |y= STRING |y= INT |y= ID ( dotfunctioncall )? ) |x= ID | ( WS )? )
					int alt34=3;
					int LA34_0 = input.LA(1);
					if ( (LA34_0==ID) ) {
						switch ( input.LA(2) ) {
						case WS:
							{
							int LA34_3 = input.LA(3);
							if ( (LA34_3==22) ) {
								alt34=1;
							}
							else if ( (LA34_3==WS||(LA34_3 >= 19 && LA34_3 <= 20)||LA34_3==24) ) {
								alt34=2;
							}

							else {
								int nvaeMark = input.mark();
								try {
									for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
										input.consume();
									}
									NoViableAltException nvae =
										new NoViableAltException("", 34, 3, input);
									throw nvae;
								} finally {
									input.rewind(nvaeMark);
								}
							}

							}
							break;
						case 22:
							{
							alt34=1;
							}
							break;
						case 19:
						case 20:
						case 24:
							{
							alt34=2;
							}
							break;
						default:
							int nvaeMark = input.mark();
							try {
								input.consume();
								NoViableAltException nvae =
									new NoViableAltException("", 34, 1, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}
					}
					else if ( (LA34_0==WS||(LA34_0 >= 19 && LA34_0 <= 20)||LA34_0==24) ) {
						alt34=3;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 34, 0, input);
						throw nvae;
					}

					switch (alt34) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:9: x= ID ( WS )? '=' ( WS )? (y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? |y= STRING |y= INT |y= ID ( dotfunctioncall )? )
							{
							x=(Token)match(input,ID,FOLLOW_ID_in_param872); 
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:14: ( WS )?
							int alt27=2;
							int LA27_0 = input.LA(1);
							if ( (LA27_0==WS) ) {
								alt27=1;
							}
							switch (alt27) {
								case 1 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:14: WS
									{
									match(input,WS,FOLLOW_WS_in_param874); 
									}
									break;

							}

							match(input,22,FOLLOW_22_in_param877); 
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:22: ( WS )?
							int alt28=2;
							int LA28_0 = input.LA(1);
							if ( (LA28_0==WS) ) {
								alt28=1;
							}
							switch (alt28) {
								case 1 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:112:22: WS
									{
									match(input,WS,FOLLOW_WS_in_param879); 
									}
									break;

							}

							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:113:5: (y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )? |y= STRING |y= INT |y= ID ( dotfunctioncall )? )
							int alt32=4;
							switch ( input.LA(1) ) {
							case FUNCTIONID:
								{
								alt32=1;
								}
								break;
							case STRING:
								{
								alt32=2;
								}
								break;
							case INT:
								{
								alt32=3;
								}
								break;
							case ID:
								{
								alt32=4;
								}
								break;
							default:
								NoViableAltException nvae =
									new NoViableAltException("", 32, 0, input);
								throw nvae;
							}
							switch (alt32) {
								case 1 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:114:6: y= FUNCTIONID ( WS )? '(' param ')' ( dotfunctioncall )?
									{
									y=(Token)match(input,FUNCTIONID,FOLLOW_FUNCTIONID_in_param895); 
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:114:19: ( WS )?
									int alt29=2;
									int LA29_0 = input.LA(1);
									if ( (LA29_0==WS) ) {
										alt29=1;
									}
									switch (alt29) {
										case 1 :
											// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:114:19: WS
											{
											match(input,WS,FOLLOW_WS_in_param897); 
											}
											break;

									}

									match(input,18,FOLLOW_18_in_param899); 
									try{stk.push(obj);obj = new EwJsonObject();obj.put("function",y.getText().substring(1));obj.put("_lineNumber", y.getLine());obj.put("_colNumber", y.getCharPositionInLine());}catch(JSONException e){}
									pushFollow(FOLLOW_param_in_param910);
									param();
									state._fsp--;

									match(input,19,FOLLOW_19_in_param922); 
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:116:10: ( dotfunctioncall )?
									int alt30=2;
									int LA30_0 = input.LA(1);
									if ( (LA30_0==DOTFUNCTIONID) ) {
										alt30=1;
									}
									switch (alt30) {
										case 1 :
											// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:116:10: dotfunctioncall
											{
											pushFollow(FOLLOW_dotfunctioncall_in_param924);
											dotfunctioncall();
											state._fsp--;

											}
											break;

									}

									try{JSONObject jo = obj;obj=stk.pop();obj.put(x.getText(),jo);}catch(JSONException e){}
									}
									break;
								case 2 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:117:6: y= STRING
									{
									y=(Token)match(input,STRING,FOLLOW_STRING_in_param938); 
									try{obj.put(x.getText(),y.getText().substring(1,y.getText().length()-1));}catch(JSONException e){}
									}
									break;
								case 3 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:118:6: y= INT
									{
									y=(Token)match(input,INT,FOLLOW_INT_in_param953); 
									try{obj.put(x.getText(),y.getText().substring(1,y.getText().length()-1));}catch(JSONException e){}
									}
									break;
								case 4 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:120:6: y= ID ( dotfunctioncall )?
									{
									y=(Token)match(input,ID,FOLLOW_ID_in_param977); 
									stk.push(obj);obj = ids.get(y.getText());
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:121:6: ( dotfunctioncall )?
									int alt31=2;
									int LA31_0 = input.LA(1);
									if ( (LA31_0==DOTFUNCTIONID) ) {
										alt31=1;
									}
									switch (alt31) {
										case 1 :
											// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:121:6: dotfunctioncall
											{
											pushFollow(FOLLOW_dotfunctioncall_in_param990);
											dotfunctioncall();
											state._fsp--;

											}
											break;

									}

									try{JSONObject jo = obj;obj=stk.pop();obj.put(x.getText(),jo);}catch(JSONException e){}
									}
									break;

							}

							}
							break;
						case 2 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:123:4: x= ID
							{
							x=(Token)match(input,ID,FOLLOW_ID_in_param1007); 
											try{JSONObject jo = ids.get(x.getText());
													Iterator<String> it = jo.keys();
													while(it.hasNext())
													{
														String s = it.next();
														obj.put(s,jo.get(s));
													}}catch(JSONException e){}
										
							}
							break;
						case 3 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:131:5: ( WS )?
							{
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:131:5: ( WS )?
							int alt33=2;
							int LA33_0 = input.LA(1);
							if ( (LA33_0==WS) ) {
								alt33=1;
							}
							switch (alt33) {
								case 1 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:131:5: WS
									{
									match(input,WS,FOLLOW_WS_in_param1014); 
									}
									break;

							}

							}
							break;

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:133:2: ( ( WS )? ',' param )?
					int alt36=2;
					int LA36_0 = input.LA(1);
					if ( (LA36_0==WS) ) {
						int LA36_1 = input.LA(2);
						if ( (LA36_1==20) ) {
							alt36=1;
						}
					}
					else if ( (LA36_0==20) ) {
						alt36=1;
					}
					switch (alt36) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:133:4: ( WS )? ',' param
							{
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:133:4: ( WS )?
							int alt35=2;
							int LA35_0 = input.LA(1);
							if ( (LA35_0==WS) ) {
								alt35=1;
							}
							switch (alt35) {
								case 1 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:133:4: WS
									{
									match(input,WS,FOLLOW_WS_in_param1024); 
									}
									break;

							}

							match(input,20,FOLLOW_20_in_param1027); 
							pushFollow(FOLLOW_param_in_param1029);
							param();
							state._fsp--;

							}
							break;

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:133:20: ( WS )?
					int alt37=2;
					int LA37_0 = input.LA(1);
					if ( (LA37_0==WS) ) {
						alt37=1;
					}
					switch (alt37) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:133:20: WS
							{
							match(input,WS,FOLLOW_WS_in_param1033); 
							}
							break;

					}

					}
					break;
				case 2 :
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:134:4: ( WS )? x= ATID ( ( WS )? ',' param )? ( WS )?
					{
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:134:4: ( WS )?
					int alt38=2;
					int LA38_0 = input.LA(1);
					if ( (LA38_0==WS) ) {
						alt38=1;
					}
					switch (alt38) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:134:4: WS
							{
							match(input,WS,FOLLOW_WS_in_param1039); 
							}
							break;

					}

					x=(Token)match(input,ATID,FOLLOW_ATID_in_param1044); 
					try{obj.put(x.getText().substring(1),x.getText());}catch(JSONException e){}
					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:135:2: ( ( WS )? ',' param )?
					int alt40=2;
					int LA40_0 = input.LA(1);
					if ( (LA40_0==WS) ) {
						int LA40_1 = input.LA(2);
						if ( (LA40_1==20) ) {
							alt40=1;
						}
					}
					else if ( (LA40_0==20) ) {
						alt40=1;
					}
					switch (alt40) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:135:4: ( WS )? ',' param
							{
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:135:4: ( WS )?
							int alt39=2;
							int LA39_0 = input.LA(1);
							if ( (LA39_0==WS) ) {
								alt39=1;
							}
							switch (alt39) {
								case 1 :
									// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:135:4: WS
									{
									match(input,WS,FOLLOW_WS_in_param1054); 
									}
									break;

							}

							match(input,20,FOLLOW_20_in_param1057); 
							pushFollow(FOLLOW_param_in_param1059);
							param();
							state._fsp--;

							}
							break;

					}

					// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:135:20: ( WS )?
					int alt41=2;
					int LA41_0 = input.LA(1);
					if ( (LA41_0==WS) ) {
						alt41=1;
					}
					switch (alt41) {
						case 1 :
							// /home/fray/workspace/ew/ew.levr.core/src/main/java/com/eduworks/resolver/lang/resolverv2.g:135:20: WS
							{
							match(input,WS,FOLLOW_WS_in_param1063); 
							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "param"

	// Delegated rules



	public static final BitSet FOLLOW_decl_in_parse572 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_parse573 = new BitSet(new long[]{0x0000000000012522L});
	public static final BitSet FOLLOW_WS_in_parse574 = new BitSet(new long[]{0x0000000000012522L});
	public static final BitSet FOLLOW_parse_in_parse578 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COMMENT_in_parse585 = new BitSet(new long[]{0x0000000000012522L});
	public static final BitSet FOLLOW_WS_in_parse587 = new BitSet(new long[]{0x0000000000012522L});
	public static final BitSet FOLLOW_parse_in_parse590 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WS_in_parse597 = new BitSet(new long[]{0x0000000000012522L});
	public static final BitSet FOLLOW_parse_in_parse599 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_parse606 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_decl619 = new BitSet(new long[]{0x0000000000410000L});
	public static final BitSet FOLLOW_WS_in_decl621 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_decl623 = new BitSet(new long[]{0x0000000000810100L});
	public static final BitSet FOLLOW_WS_in_decl624 = new BitSet(new long[]{0x0000000000800100L});
	public static final BitSet FOLLOW_functioncall_in_decl631 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_decl642 = new BitSet(new long[]{0x0000000001110410L});
	public static final BitSet FOLLOW_param_in_decl643 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_decl644 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_decl660 = new BitSet(new long[]{0x0000000000410000L});
	public static final BitSet FOLLOW_WS_in_decl662 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_decl664 = new BitSet(new long[]{0x0000000000010400L});
	public static final BitSet FOLLOW_WS_in_decl665 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_decl670 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_dotfunctioncall_in_decl680 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SERVLETID_in_decl693 = new BitSet(new long[]{0x0000000000030000L});
	public static final BitSet FOLLOW_WS_in_decl695 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_decl698 = new BitSet(new long[]{0x0000000000010400L});
	public static final BitSet FOLLOW_WS_in_decl700 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_decl705 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SERVLETID_in_decl716 = new BitSet(new long[]{0x0000000000410000L});
	public static final BitSet FOLLOW_WS_in_decl718 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_decl721 = new BitSet(new long[]{0x0000000000010400L});
	public static final BitSet FOLLOW_WS_in_decl723 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_decl728 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FUNCTIONID_in_decl739 = new BitSet(new long[]{0x0000000000030000L});
	public static final BitSet FOLLOW_WS_in_decl741 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_decl744 = new BitSet(new long[]{0x0000000000010400L});
	public static final BitSet FOLLOW_WS_in_decl746 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_decl751 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FUNCTIONID_in_decl762 = new BitSet(new long[]{0x0000000000410000L});
	public static final BitSet FOLLOW_WS_in_decl764 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_decl767 = new BitSet(new long[]{0x0000000000010400L});
	public static final BitSet FOLLOW_WS_in_decl769 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_decl774 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FUNCTIONID_in_functioncall792 = new BitSet(new long[]{0x0000000000050000L});
	public static final BitSet FOLLOW_WS_in_functioncall794 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_functioncall796 = new BitSet(new long[]{0x0000000000190410L});
	public static final BitSet FOLLOW_param_in_functioncall805 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_functioncall806 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_dotfunctioncall_in_functioncall808 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOTFUNCTIONID_in_dotfunctioncall823 = new BitSet(new long[]{0x0000000000050000L});
	public static final BitSet FOLLOW_WS_in_dotfunctioncall825 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_dotfunctioncall827 = new BitSet(new long[]{0x0000000000190410L});
	public static final BitSet FOLLOW_param_in_dotfunctioncall836 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_dotfunctioncall847 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_dotfunctioncall_in_dotfunctioncall849 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WS_in_param866 = new BitSet(new long[]{0x0000000000110402L});
	public static final BitSet FOLLOW_ID_in_param872 = new BitSet(new long[]{0x0000000000410000L});
	public static final BitSet FOLLOW_WS_in_param874 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_param877 = new BitSet(new long[]{0x0000000000014D00L});
	public static final BitSet FOLLOW_WS_in_param879 = new BitSet(new long[]{0x0000000000004D00L});
	public static final BitSet FOLLOW_FUNCTIONID_in_param895 = new BitSet(new long[]{0x0000000000050000L});
	public static final BitSet FOLLOW_WS_in_param897 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_param899 = new BitSet(new long[]{0x0000000000190410L});
	public static final BitSet FOLLOW_param_in_param910 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_param922 = new BitSet(new long[]{0x0000000000110042L});
	public static final BitSet FOLLOW_dotfunctioncall_in_param924 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_STRING_in_param938 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_INT_in_param953 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_ID_in_param977 = new BitSet(new long[]{0x0000000000110042L});
	public static final BitSet FOLLOW_dotfunctioncall_in_param990 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_ID_in_param1007 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_WS_in_param1014 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_WS_in_param1024 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_param1027 = new BitSet(new long[]{0x0000000000110410L});
	public static final BitSet FOLLOW_param_in_param1029 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_WS_in_param1033 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WS_in_param1039 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_ATID_in_param1044 = new BitSet(new long[]{0x0000000000110002L});
	public static final BitSet FOLLOW_WS_in_param1054 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_param1057 = new BitSet(new long[]{0x0000000000110410L});
	public static final BitSet FOLLOW_param_in_param1059 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_WS_in_param1063 = new BitSet(new long[]{0x0000000000000002L});
}
