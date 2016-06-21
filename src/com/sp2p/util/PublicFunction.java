package com.sp2p.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
 

import com.shove.data.dao.MySQL;
import com.sp2p.database.Dao;
import com.shove.Convert;

/**
 * 公共的常用方法
 * 
 * @author liukai
 * @Date 2013-10-07
 */
public class PublicFunction {
	/**
	 * 获得Option表中指定Key的Value
	 * 
	 * @Key Key值
	 * @return Value
	 * @throws SQLException
	 */
	public static String GetOption(String Key) throws SQLException {
		String Value = null;
		Connection conn = MySQL.getConnection();
		try {
			Value = Dao.Functions.f_getoption(conn, Key);
		} catch (Exception e) {

		} finally {
			conn.close();
		}

		return Value.trim();
	}

	/**
	 * 获得Option表中指定Key的Value,，并转型成为String
	 * 
	 * @Key Key值
	 * @return Value
	 * @Default 查询失败时的默认值
	 */
	public static String GetOptionOnString(String Key, String Default)
			throws SQLException {
		String Value = GetOption(Key);
		if (Value == null) {
			return Default;
		}

		Value = Convert.strToStr(Value, Default);

		return Value.trim();
	}

	/**
	 * 获得Option表中指定Key的Value,，并转型成为int
	 * 
	 * @param Key值
	 * @param 查询失败时的默认值
	 * @return Value
	 */
	public static int GetOptionAsInt(String Key, int Default)
			throws SQLException {
		String Value = GetOption(Key);
		int Result = Default;
		if (Value == null) {
			return Default;
		}
		Result = Convert.strToInt(Value, Default);
		return Result;
	}

	/**
	 * 获得Option表中指定Key的Value,，并转型成为long
	 * 
	 * @param Key值
	 * @param 查询失败时的默认值
	 * @return Value
	 */
	public static long GetOptionsAsLong(String Key, long Default)
			throws SQLException {
		String Value = GetOption(Key);

		long Result = Default;

		try {
			Result = Long.parseLong(Value);
		} catch (Exception e) {
			Result = Default;
		}

		return Result;
	}

	/**
	 * 获得Option表中指定Key的Value,，并转型成为double
	 * 
	 * @param Key值
	 * @param 查询失败时的默认值
	 * @return Value
	 * @throws SQLException 
	 */
	public static double GetOptionsAsDouble(String Key, double Default) throws SQLException  {
		String Value = GetOption(Key);

		double Result = Default;

		try {
			Result = Double.parseDouble(Value);
		} catch (Exception e) {
			Result = Default;
		}

		return Result;
	}

	/**
	 * 获得Option表中指定Key的Value,，并转型成为Boolean
	 * 
	 * @param Key值
	 * @param 查询失败时的默认值
	 * @return Value
	 * @throws SQLException 
	 */
	public static Boolean GetOptionsAsBoolean(String Key, Boolean Default) throws SQLException {
		String Value = GetOption(Key);

		Boolean Result = Default;

		try {
			Result = Boolean.parseBoolean(Value);
		} catch (Exception e) {
			Result = Default;
		}

		return Result;
	}
 
	/**
	 * 获得Option表中指定Key的Value,，并转型成为Boolean
	 * 
	 * @param Key值
	 * @param 查询失败时的默认值
	 * @format 时间类型格式 
	 * @return Value 
	 */
    public static Date GetOptionsAsDateTime(String Key, Date Default,String format) throws SQLException
    {
        String Value = GetOption(Key);

        Date Result = Default;

        try
        {
            Result = Convert.strToDate(Value, format, Default);
        }
        catch (Exception e) {
			Result = Default;
		}

        return Result;
    }
}
