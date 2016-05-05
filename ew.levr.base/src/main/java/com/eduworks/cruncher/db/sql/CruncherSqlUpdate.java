package com.eduworks.cruncher.db.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;

public class CruncherSqlUpdate extends SqlTypeCruncher
{


	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{

		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			Connection conn = openDbConnection(c, parameters, dataStreams);
			String statement = getObj(c, parameters, dataStreams).toString();
			try
			{
				stmt = conn.createStatement();
				int numRowsAffected = stmt.executeUpdate(statement);
				conn.commit();
				return numRowsAffected;
			}
			catch (Exception e)
			{
				conn.rollback();
				e.printStackTrace();
				return -1;
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
	}

	@Override
	public String[] getResolverNames()
	{
		return new String[] { getResolverName(), "sqlInsert", "sqlDelete" };
	}

	@Override
	public String getDescription()
	{
		return "Executes the given sql update, insert, or delete (obj) and returns the number of rows affected.  Returns -1 if an error occurred.";
	}

	@Override
	public String getReturn()
	{
		return "Number";
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
