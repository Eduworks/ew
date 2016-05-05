package com.eduworks.cruncher.db.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.ContextEvent;
import com.eduworks.resolver.Cruncher;

public abstract class SqlTypeCruncher extends Cruncher
{
	protected static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	protected static final String SQLSERVER_DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	protected static final String JTDS_DRIVER_CLASS = "net.sourceforge.jtds.jdbc.Driver";

	protected Connection openDbConnection(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws Exception
	{
		return openDbConnection(c, parameters, dataStreams, false);
	}

	protected Connection openDbConnection(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams, boolean autoCommit)
			throws Exception
	{
		String connectionString;
		String userName;
		String password;
		boolean isMySql;
		boolean isSqlServer;
		boolean isJtds;

		Connection conn;
		connectionString = getAsString("sqlConnectionString", c, parameters, dataStreams);
		userName = getAsString("sqlUsername", c, parameters, dataStreams);
		password = getAsString("sqlPassword", c, parameters, dataStreams);
		isMySql = optAsBoolean("sqlMysql", false, c, parameters, dataStreams);
		isSqlServer = optAsBoolean("sqlSqlServer", false, c, parameters, dataStreams);
		isJtds = optAsBoolean("sqlJtds", false, c, parameters, dataStreams);

		try
		{
			if (isMySql)
				Class.forName(MYSQL_DRIVER_CLASS);
			else if (isSqlServer)
				Class.forName(SQLSERVER_DRIVER_CLASS);
			else if (isJtds)
				Class.forName(JTDS_DRIVER_CLASS);
			conn = (Connection) c.get(connectionString);
			if (conn == null)
			{
				conn = DriverManager.getConnection(connectionString, userName, password);
				final Connection conFinal = conn;
				c.onFinally(new ContextEvent()
				{
					@Override
					public void go()
					{
						if (conFinal != null)
						{
							try
							{
								conFinal.close();
							}
							catch (SQLException e)
							{
								e.printStackTrace();
							}
						}
					}
				});
				if (conn != null && !conn.isClosed())
					conn.setAutoCommit(autoCommit);
			}
			return conn;
		}
		catch (Exception e)
		{
			log.debug("Exception opening database connection.");
			throw e;
		}
	}
}
