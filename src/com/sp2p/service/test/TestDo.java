package com.sp2p.service.test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.write.DateTime;

import com.shove.base.BaseTest;
import com.shove.data.DataException;
import com.sp2p.action.app.BaseAppAction;
import com.sp2p.service.UserService;

public class TestDo extends BaseTest{

	private UserService userService;
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void testDoo() throws Exception{
		long userId = 30;
//		String uuid = "d985c5a3-7c99-4fa9-83e1-0d0f0e6077bb";
//		String time = "20140807024246701";
//		long a = userService.addToken(userId, uuid, time);
//		System.out.println("aaaaaaaaaaaaaaaa:"+a);
		
//		Map<String, String> userTokenMap = userService.queryUserTokenById(userId);
//		System.out.println("---------------:"+userTokenMap);
		
//		userService.updateUserToken(userId, "d985c5a3-7c99-4fa9-83e1", "20140808888246701");
		
		
		/**
		 * 分页存储过程
		 * @param pageBean
		 * @param table 表名
		 * @param field 字段名
		 * @param order 排序
		 * @param where 条件
		 * @throws DataException 
		 * @throws SQLException 
		 * @throws IOException 
		 */
		List<Map<String, Object>> userList =userService.getDbData("t_user_check", "*", "", "",10, 1);
		System.out.println("getDbDate:"+userList);
	}

}
