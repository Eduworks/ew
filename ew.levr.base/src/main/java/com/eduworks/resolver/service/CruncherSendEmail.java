package com.eduworks.resolver.service;

import java.io.InputStream;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.net.mail.EwMail;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherSendEmail extends Cruncher
{

	/**
	 * Sends an email using the parameters passed in. Expected parameters:
	 * <list> <li><b>_to</b>: destination email</li> <li><b>_from</b>: source
	 * email</li> <li><b>_subject</b>: email subject</li> <li><b>_template</b>:
	 * email body</li> </list> <br/>
	 * <br/>
	 * The template value searches for "${from}" Email sending has no return
	 * value, so this method just returns the body of the email sent.
	 *
	 * @return the body of the email sent
	 */
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		final String fromEmail = getAsString("_from", c, parameters, dataStreams);
		final String toEmail = getAsString("_to", c, parameters, dataStreams);
		final String subject = format(getAsString("_subject", c, parameters, dataStreams), c, parameters, dataStreams);
		final String template = format(getAsString("_template", c, parameters, dataStreams), c, parameters, dataStreams);
		final String smtpHost = getAsString("_smtpHost",c, parameters, dataStreams);
		final String smtpPort = getAsString("_smtpPort",c, parameters, dataStreams);
		final String smtpUser = getAsString("_smtpUser",c, parameters, dataStreams);
		final String smtpPass = getAsString("_smtpPass",c, parameters, dataStreams);

		if (optAsBoolean("html", false, c, parameters, dataStreams))
			try
			{
				EwMail.sendHtmlEmail(smtpHost,smtpPort,smtpUser,smtpPass,fromEmail, toEmail, subject, template);
			}
			catch (AddressException e)
			{
				throw new RuntimeException(e);
			}
			catch (MessagingException e)
			{
				throw new RuntimeException(e);
			}
		else
			try
			{
				EwMail.sendEmail(smtpHost,smtpPort,smtpUser,smtpPass,fromEmail, toEmail, subject, template);
			}
			catch (AddressException e)
			{
				throw new RuntimeException(e);
			}
			catch (MessagingException e)
			{
				throw new RuntimeException(e);
			}

		final EwJsonObject obj = new EwJsonObject();

		obj.put("from", fromEmail);
		obj.put("to", toEmail);
		obj.put("subject", subject);
		obj.put("body", template);

		return obj;
	}

	/**
	 * Replaces $(key) in the format string with the corresponding value from this JSONObject,
	 * or the attached parameters. Setting keys and any keys specified in the ignore array may
	 * not be referenced in the format string, or an exception is thrown.
	 */
	public String format(String format, Context c, Map<String,String[]> parameters, Map<String,InputStream> dataStreams, String ... ignore)
	{
		// Approximate the result length: format string + 16 character args
	    final StringBuilder sb = new StringBuilder(format.length() + (parameters.size()*16));

	    final char escChar = '\\';
	    final char derefChar = '$';
	    final char openDelim = '(';
	    final char closeDelim = ')';

	    int cur = 0;
	    int len = format.length();
	    int open;
	    int close;

	    WHILE:
	    while (cur < len)
	    {
	        switch (open = format.indexOf(openDelim, cur))
	        {
	        	case -1:
	        		// No open paren: just append the string as is
	        		sb.append(format.substring(cur, len));
	        		break WHILE;

	        	default:
	        		// Found open paren: append everything leading up to it
		            sb.append(format.substring(cur, open));

		            switch (close = format.indexOf(closeDelim, open))
		            {
		            	case -1:
			        		// No close paren: append the rest of the string
		            		sb.append(format.substring(open));
			        		break WHILE;

	            		default:
	            			// Does a dollar sign precede the open paren?
	            			if (open > 0 && format.charAt(open-1) == derefChar)
	            			{
	            				// Is the dollar escaped?
	            				if (open > 1 && format.charAt(open-2) == escChar)
	            				{
            						// Remove escape and dollar sign
            						sb.setLength(sb.length() - 2);

	            					// Append escaped dollar and open, and continue from there
	            					sb.append(derefChar).append(openDelim);

	            					cur = (open + 1);

	            					continue;
	            				}
	            				else
	            				{
	            					// Parse the paren delimited key: "$(key)"
	            					final String fmtKey = format.substring(open + 1, close);

	            					try
	            					{
	            						if (isSetting(fmtKey)) throw new Exception("Key is Setting.");

	            						for (int i = 0; i < ignore.length; i++)
	            							if (fmtKey.equals(ignore[i]))
	            								throw new Exception("Key is Ignored.");

	            						// Remove preceding dollar sign
	            						sb.setLength(sb.length() - 1);

	            						// Append the corresponding param value
	            						sb.append(getAsString(fmtKey, c,parameters,dataStreams));
	            					}
	            					catch (Exception e)
	            					{
	            						// Append the dollar sign, the parens and the original delimited value
	            						sb.append(derefChar).append(openDelim).append(fmtKey).append(closeDelim);
	            					}
	            				}

	            				cur = close + 1; // Continue after the closing paren
	            			}
	            			else
	            			{
	            				/* No dollar sign before open: find next one and continue from there */

	            				final int nextOpen = format.indexOf(openDelim, open + 1);

	            				if (nextOpen != -1 && nextOpen < close)
									cur = nextOpen;		// Continue at next open paren before close
								else
									cur = (close + 1);	// No open before close: continue after close

	            				sb.append(format.substring(open, cur));
	            			}
		            }
	        }
	    }

	    return sb.toString();
	}

	@Override
	public String getDescription()
	{
		return "Sends an email (TODO: This)";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo();
	}
}
