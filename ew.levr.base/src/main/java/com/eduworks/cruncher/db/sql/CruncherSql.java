package com.eduworks.cruncher.db.sql;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;

public class CruncherSql extends SqlTypeCruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Statement stmt = null;
		ResultSet rs = null;
		JSONArray dataSet = new JSONArray();
		try
		{
			Connection conn = openDbConnection(c, parameters, dataStreams);
			String statement = getObj(c, parameters, dataStreams).toString();
			try
			{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(statement);
				while (rs.next())
				{
					dataSet.put(parseCurrentResultSetRow(rs));
				}
			}
			catch (Exception e)
			{
				log.debug("Exception executing sql statement:" + statement);
				e.printStackTrace();
				throw e;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				rs = null;
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				stmt = null;
			}
		}
		return dataSet;
	}

	private JSONObject parseCurrentResultSetRow(ResultSet rs) throws Exception
	{
		JSONObject dataRow = new JSONObject();
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
		{
			String colName = rs.getMetaData().getColumnName(i);
			int colType = rs.getMetaData().getColumnType(i);
			if (colType == java.sql.Types.CHAR)
				dataRow.put(colName, rs.getString(colName).trim());
			else if (colType == java.sql.Types.VARCHAR)
				dataRow.put(colName, rs.getString(colName).trim());
			else if (colType == java.sql.Types.INTEGER)
				dataRow.put(colName, rs.getInt(colName));
			else if (colType == java.sql.Types.DECIMAL)
				dataRow.put(colName, rs.getBigDecimal(colName));
			else if (colType == java.sql.Types.NUMERIC)
				dataRow.put(colName, rs.getDouble(colName));
			else if (colType == java.sql.Types.CLOB)
				dataRow.put(colName, clobToString(rs.getClob(colName)));
		}
		return dataRow;
	}

	private String clobToString(Clob c) throws Exception
	{
		Reader in = c.getCharacterStream();
		StringWriter w = new StringWriter();
		IOUtils.copy(in, w);
		return w.toString();
	}

	@Override
	public String[] getResolverNames()
	{
		return new String[] { getResolverName(), "sqlQuery" };
	}

	@Override
	public String getDescription()
	{
		return "Executes the given sql statment (obj) and returns the result set as a JSON array of JSON objects.";
	}

	@Override
	public String getReturn()
	{
		return "JSONArray";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "String", "sqlConnectionString", "String", "sqlUsername", "String", "sqlPassword", "String", "sqlMysql", "boolean", "sqlSqlServer",
				"boolean", "sqlJtds", "boolean");
	}

}
