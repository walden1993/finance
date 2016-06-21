package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.renren.api.client.utils.JsonUtils;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.EmalAndMessageService;
import com.sp2p.service.admin.RoleRightsService;

public class EmalAndMessageAction extends BaseFrontAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(EmalAndMessageAction.class);
	
	private EmalAndMessageService emalAndMessageService;
	private RoleRightsService roleRightsService;
	public void setEmalAndMessageService(
			EmalAndMessageService emalAndMessageService) {
		this.emalAndMessageService = emalAndMessageService;
	}
	public void setRoleRightsService(RoleRightsService roleRightsService) {
        this.roleRightsService = roleRightsService;
    }
	/**
	 * 邮件设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String emailAndMessageinitMethod() throws Exception {
		Map<String, String> mailMap = null;
		mailMap = emalAndMessageService.queryMailsetMaxId();
		mailMap = emalAndMessageService.queryMailSet(Convert.strToInt(mailMap
				.get("id"), 1));
		request().setAttribute("mailMap", mailMap);
		return SUCCESS;
	}

	/**
	 * 提交邮件设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMailSetM() throws Exception {
		JSONObject obj = new JSONObject();
		String maildress = paramMap.get("maildress");
		String password = paramMap.get("password");
		String sendEmail = paramMap.get("sendEmail");
		String username = paramMap.get("username");
		String port = paramMap.get("port");
		String host = paramMap.get("host");
		Long result = -1L;
		result = emalAndMessageService.addUserWorkData(maildress, password,
				sendEmail, username, port, host);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			IConstants.MAIL_HOST = host;
			IConstants.MAIL_USERNAME = sendEmail;
			IConstants.MAIL_PASSWORD = password;
			IConstants.MAIL_FROM = maildress;

			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_mailset", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"更新邮件设置成功", 2);
			return null;
		} else {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_mailset", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"更新邮件设置失败", 2);
			return null;
		}
	}

	/**
	 * 设置短信参数设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMessageSetMethod() throws Exception {
		Map<String, String> Messagemap = null;
		Map<String, String> Messagemap2 = null;
		Messagemap = emalAndMessageService.queryMessageSet(1);
		Messagemap2 = emalAndMessageService.queryMessageSet(2);
		request().setAttribute("Messagemap", Messagemap);
		request().setAttribute("Messagemap2", Messagemap2);
		return SUCCESS;
	}

	/**
	 * 增加或修改短信参数设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMessageSetMethod() throws Exception {
		String enablestr = paramMap.get("kaiqi1");
		if (StringUtils.isBlank(enablestr)) {
			JSONUtils.printStr("8");
			return null;
		}
		Integer enable = Convert.strToInt(enablestr, 1);// 默认开启

		Long id = Convert.strToLong(paramMap.get("id"), -1);
		String username = paramMap.get("username1");
		if (StringUtils.isBlank(username)) {
			JSONUtils.printStr("3");
			return null;
		}
		if (username.length() < 6) {
			JSONUtils.printStr("4");
			return null;
		}
		String password = paramMap.get("password1");
		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		if (password.length() < 6) {
			JSONUtils.printStr("6");
			return null;
		}
		String url = paramMap.get("url1");
		if (StringUtils.isBlank(url)) {
			JSONUtils.printStr("7");
			return null;
		}
		Long reLong = -1L;
		reLong = emalAndMessageService.addMessageSet(id, username, password,
				url, enable);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (reLong > 0) {
			JSONUtils.printStr("1");// 成功
			operationLogService.addOperationLog("t_messageset", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"更新短信参数设置成功", 2);
			return null;
		} else {
			JSONUtils.printStr("0");
			operationLogService.addOperationLog("t_messageset", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"更新短信参数设置失败", 2);
			return null;
		}
	}

	/**
	 * 增加或修改短信参数设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMessageSetMethod2() throws Exception {
		String enablestr = paramMap.get("kaiqi2");
		if (StringUtils.isBlank(enablestr)) {
			JSONUtils.printStr("8");
			return null;
		}
		Integer enable = Convert.strToInt(enablestr, 1);// 默认开启

		Long id = Convert.strToLong(paramMap.get("id"), -1);
		String username = paramMap.get("username2");
		if (StringUtils.isBlank(username)) {
			JSONUtils.printStr("3");
			return null;
		}
		if (username.length() < 6) {
			JSONUtils.printStr("4");
			return null;
		}
		String password = paramMap.get("password2");
		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		if (password.length() < 6) {
			JSONUtils.printStr("6");
			return null;
		}
		String url = paramMap.get("url2");
		if (StringUtils.isBlank(url)) {
			JSONUtils.printStr("7");
			return null;
		}
		Long reLong = -1L;
		reLong = emalAndMessageService.addMessageSet(id, username, password,
				url, enable);
		if (reLong > 0) {
			JSONUtils.printStr("1");// 成功
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}
	}

	/**
	 * 联动
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String linkageinitIndex() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 加载功能，权限控制(此功能暂时作废)
	 * loadRigthsMenu
	 * @auth hejiahua
	 * @return
	 * @throws Exception 
	 * String
	 * @exception 
	 * @date:2014-9-1 下午5:36:16
	 * @since  1.0.0
	 */
	@SuppressWarnings("unused")
    @Deprecated
    private String loadRigthsMenu() throws Exception {
        String rightsId = request.getString("rightsId");//左侧权限id
	    if (StringUtils.isBlank(rightsId)) {
	        return SUCCESS;
        }
	    
	    Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
	    long roleId = admin.getRoleId();//用户分组
	    
	    List<Map<String,Object>> list = null;
	    list = roleRightsService.queryAdminRoleRightMenu(roleId,rightsId);
	    session().setAttribute("adminRoleMenuListRightl", list);
	    session().setAttribute("rightsId", rightsId);
	    return SUCCESS;
    }

	/**
	 *联动详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String linkageinfoMethod() throws Exception {
		emalAndMessageService.querySelectInfo(pageBean);
		return SUCCESS;
	}

	/**
	 * 添加借款目的
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addTargMethod() throws Exception {
		String titleName = paramMap.get("titleName");
		Long result = -1L;
		if (StringUtils.isNotBlank(titleName)) {
			result = emalAndMessageService.addTarage(titleName);
			if (result > 0) {
				JSONUtils.printStr("1");
				return null;
			} else {
				JSONUtils.printStr("0");
				return null;
			}
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_select", admin.getUserName(),
				IConstants.INSERT, admin.getLastIP(), 0, "添加借款目的", 2);

		return null;
	}

	/**
	 * 修改借款目的
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updatetageMethod() throws SQLException, DataException,
			IOException {
		Long id = request.getLong("id", -1);
		request().setAttribute("id", id);
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatetagerealMethod() throws Exception {
		Long id = Convert.strToLong(paramMap.get("id"), -1L);
		String conte = paramMap.get("tagd");
		Long result = -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (id != null && id != -1 && StringUtils.isNotBlank(conte)) {
			result = emalAndMessageService.updateTage(id, conte);
		}
		if (result > 0) {
			JSONUtils.printStr("1");
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改借款目的成功", 2);
			return null;
		} else {
			JSONUtils.printStr("0");
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改借款目的失败", 2);
			return null;
		}
	}

	/**
	 * 删除借款目的
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deletetageMethod() throws Exception {
		Long id = request.getLong("id", -1);
		if (id != null && id != -1) {
			emalAndMessageService.deleteTage(id);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_select", admin.getUserName(),
				IConstants.INSERT, admin.getLastIP(), 0, "删除借款目的", 2);
		return SUCCESS;
	}

	/**
	 * 担保机构初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryAcountInitMe() {
		return SUCCESS;
	}

	/**
	 * 查询担保机构列表
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public String queryAcountinfoM() throws Exception {
		emalAndMessageService.queryAccountInfo(pageBean);
		return SUCCESS;
	}

	/**
	 * 增加担保方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAssureM() throws Exception {

		String titleName = paramMap.get("titleName");
		if (StringUtils.isBlank(titleName)) {
			JSONUtils.printStr("2");
			return null;
		}
		Long result = -1L;
		if (StringUtils.isNotBlank(titleName)) {
			result = emalAndMessageService.addDan(titleName);
			if (result > 0) {
				JSONUtils.printStr("1");
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "新增担保方式", 2);
				return null;
			} else {
				JSONUtils.printStr("0");
				return null;
			}
		}
		return null;

	}
	
	/**
	 * 新增系统参数类别
	 * addSysparMethod
	 * @auth hejiahua
	 * @return 
	 * String
	 * @throws Exception 
	 * @exception 
	 * @date:2014-9-2 上午8:26:00
	 * @since  1.0.0
	 */
	public String addSysparMethod() throws Exception{
	        String typeName = paramMap.get("titleName");
	        String introduce = paramMap.get("introduce");
	        if (StringUtils.isNotBlank(typeName)) {
                Long result = this.emalAndMessageService.addSyspar(typeName,introduce);
                if (result >0) {
                    JSONUtils.printStr("1");
                    Admin admin = (Admin) session().getAttribute(
                            IConstants.SESSION_ADMIN);
                    operationLogService.addOperationLog("t_selecttype", admin
                            .getUserName(), IConstants.INSERT, admin.getLastIP(),
                            0, "新增系统参数类型", 2);
                }else {
                    JSONUtils.printStr("2");
                }
            }else {
                JSONUtils.printStr("0");
            }
	        
	        return null;
	}
	
	/**
	 * 删除系统参数类型
	 * @throws Exception 
	 */
	public String deleteSysparMethod() throws Exception{
	      String id = paramMap.get("id");
	      if (StringUtils.isBlank(id)) {
            JSONUtils.printStr("1");
            return null;
        }
	     emalAndMessageService.querySyspar(id, pageBean);
	     if (pageBean.getTotalNum()>0) {
            JSONUtils.printStr("2");
            return null;
        }
	     Long result = emalAndMessageService.deleteSyspar(id);
	     if (result>0) {
            JSONUtils.printStr("3");
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("t_selecttype", admin .getUserName(), IConstants.DELETE, admin.getLastIP(),
                                                         0, "删除系统参数类型", 2);
        }else {
            JSONUtils.printStr("4");
        }
	      
	      return null;
	}
	
	/**
	 * 删除某类型的系统参数
	 * @throws Exception 
	 */
	public String deleteSysparChildMethod() throws Exception{
	    String id = request.getString("id");
	    Long result = emalAndMessageService.deleteSysparChild(id);
        if (result>0) {
           JSONUtils.printStr("1");
           Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
           operationLogService.addOperationLog("t_selecttype", admin .getUserName(), IConstants.DELETE, admin.getLastIP(),
                                                        0, "删除某类型系统参数", 2);
       }else {
           JSONUtils.printStr("2");
       }
        return null;
	}
	
	/**
	 * 更新某类型系统参数
	 * @throws Exception 
	 */
	public String updateSysparChildMethod() throws Exception{
	    String id = paramMap.get("id");
	    String selectName = paramMap.get("selectName");
	    String selectValue = paramMap.get("selectValue");
	    String deleted = paramMap.get("deleted");
	    String introduce = paramMap.get("introduce");
	    Long result = emalAndMessageService.updateSysparChild(id, selectName, selectValue, deleted,introduce);
	    if (result >0) {
            JSONUtils.printStr("1");
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("t_selecttype", admin .getUserName(), IConstants.UPDATE, admin.getLastIP(),
                                                         0, "修改某类型系统参数", 2);
        }else {
            JSONUtils.printStr("2");
        }
	    return null;
	}
	
	/**
	 * 初始化新增系统参数
	 * @throws SQLException 
	 */
	public String addSysparChildInitMethod() throws SQLException{
	    String id = request.getString("id");//类型的id
	    String typename = request.getString("typename");//类型名称
	    int type = request.getInt("type",1);//操作类型  (1新增 3查看)(系统参数)   2修改 (参数类别)
	    request().setAttribute("id", id);
	    request().setAttribute("typename", typename);
	    request().setAttribute("type", type);
	    
	    if (type==3) {
	        pageBean.setPageNum(request.getInt("curPage", 1));
	        pageBean.setPageSize(IConstants.PAGE_SIZE_6);
            emalAndMessageService.querySyspar(id,pageBean);
        }else if(type==2){//修改系统参数类型
            emalAndMessageService.querySysparInfo(pageBean, id);
            request().setAttribute("map", pageBean.getPage()!=null?pageBean.getPage().get(0):null);
        }
	    
	    return SUCCESS;
	}
	
	/**
	 * 验证参数码
	 */
	public String validataSyspar() throws SQLException, IOException{
	    String selectKey = paramMap.get("selectKey");
	    int result = emalAndMessageService.querySysparByKey(selectKey);
	    if (result>0) {
            JSONUtils.printStr("2");
        }else {
            JSONUtils.printStr("1");
        }
	    return null;
	}
	
	/**
	 * 新增系统参数
	 * @throws Exception 
	 */
	public String addSysparChildMethod() throws Exception{
        String typeId = paramMap.get("typeId");
        String selectValue = paramMap.get("selectValue");
        String selectName = paramMap.get("selectName");
        String selectKey = paramMap.get("selectKey");
        String deleted = paramMap.get("deleted");
        String introduce = paramMap.get("introduce");
        int hasKey = emalAndMessageService.querySysparByKey(selectKey);
        if (hasKey>0) {//数据库已存在此key
            JSONUtils.printStr("3");
            return null;
        }
        
        Long result = emalAndMessageService.addSysparChild(typeId, selectValue, selectName,selectKey, deleted,introduce);
        if (result>0) {
            JSONUtils.printStr("1");
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("t_selecttype", admin .getUserName(), IConstants.INSERT, admin.getLastIP(),
                                                         0, "新增系统参数", 2);
        }else {
            JSONUtils.printStr("2");
        }
        return null;
	}
    
	/**
	 * 更新系统参数类型
	 * @throws Exception 
	 */
	public String updateSysparMethod() throws Exception{
	    String typeName = paramMap.get("selectName");
	    String introduce = paramMap.get("introduce");
	    String typeId = paramMap.get("typeId");
	    Long result = emalAndMessageService.updateSyspar(typeId, typeName,introduce);
	    if (result>0) {
            JSONUtils.printStr("1");
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("t_selecttype", admin .getUserName(), IConstants.UPDATE, admin.getLastIP(),
                                                         0, "更新系统参数类别", 2);
        }else {
            JSONUtils.printStr("2");
        }
	    return null;
	}
	
	/**
	 * 修改金额范围 传值到弹出窗口的id值
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updatam() throws SQLException, DataException, IOException {
		Long id = request.getLong("id", -1);
		request().setAttribute("id", id);
		return SUCCESS;
	}

	/**
	 * 修改担保机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatamreal() throws Exception {
		Long id = Convert.strToLong(paramMap.get("id"), -1L);
		String conte = paramMap.get("tagd");
		if (StringUtils.isBlank(conte)) {
			JSONUtils.printStr("3");
			return null;
		}
		Long result = -1L;
		if (id != null && id != -1 && StringUtils.isNotBlank(conte)) {
			result = emalAndMessageService.updateAccount(id, conte);
		}
		if (result > 0) {
			JSONUtils.printStr("1");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改担保机构", 2);
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}
	}

	/**
	 * 删除金额范围
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteaccM() throws Exception {
		Long id = request.getLong("id", -1);
		if (id != null && id != -1) {
			emalAndMessageService.deleteacc(id);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_select", admin.getUserName(),
				IConstants.DELETE, admin.getLastIP(), 0, "删除金额范围", 2);
		return SUCCESS;
	}

	/**
	 * 反担保方式初始化
	 * 
	 * @return
	 */
	public String queryInversIndexM() {
		return SUCCESS;
	}

	/**
	 * 反担保方式详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryInversInfoM() throws Exception {
		emalAndMessageService.queryIversInof(pageBean);
		return SUCCESS;
	}

	/**
	 * 添加投资金额参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addInversM() throws Exception {
		String titleName = paramMap.get("titleName");
//		if (!StringUtils.isNumeric(titleName)) {
//			JSONUtils.printStr("2");
//			return null;
//		}
		Long result = -1L;
		if (StringUtils.isNotBlank(titleName)) {
			result = emalAndMessageService.addInver(titleName);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			if (result > 0) {
				JSONUtils.printStr("1");
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "添加投资金额参数成功", 2);
				return null;
			} else {
				JSONUtils.printStr("0");
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "添加投资金额参数失败", 2);
				return null;
			}
		}
		return null;
	}

	/**
	 * 将id传到弹出框
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updateinversM() throws SQLException, DataException,
			IOException {
		Long id = request.getLong("id", -1);
		request().setAttribute("id", id);
		return SUCCESS;
	}

	public String updateinversRealM() throws Exception {
		Long id = Convert.strToLong(paramMap.get("id"), -1L);
		String conte = paramMap.get("tagd");
		if (StringUtils.isBlank(conte)) {
			JSONUtils.printStr("3");
			return null;
		}
		Long result = -1L;
		if (id != null && id != -1 && StringUtils.isNotBlank(conte)) {
			result = emalAndMessageService.updateInvers(id, conte);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			JSONUtils.printStr("1");
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改担保机构和反担保方式成功", 2);
			return null;
		} else {
			JSONUtils.printStr("0");
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改担保机构和反担保方式失败", 2);
			return null;
		}
	}

	/**
	 * 删除投标金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteinversM() throws Exception {
		Long id = request.getLong("id", -1);
		if (id != null && id != -1) {
			emalAndMessageService.deleteacc(id);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_select", admin.getUserName(),
				IConstants.DELETE, admin.getLastIP(), 0, "删除反担保方式", 2);
		return SUCCESS;
	}

	/**
	 * 初始化图片
	 * 
	 * @return
	 */
	public String queryImageIndexM() {
		return SUCCESS;
	}

	/**
	 * 查看图片详细情况
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryImageInfoM() throws Exception {
		emalAndMessageService.querySysImageInfo(pageBean);
		return SUCCESS;
	}

	/**
	 * 增加图片
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addSysImageM() throws Exception {

		String titleName = paramMap.get("titleName");
		if (StringUtils.isBlank(titleName)) {
			JSONUtils.printStr("2");
			return null;
		}
		Long result = -1L;
		if (StringUtils.isNotBlank(titleName)) {
			result = emalAndMessageService.addSysImage(titleName);
			if (result > 0) {
				JSONUtils.printStr("1");
				return null;
			} else {
				JSONUtils.printStr("0");
				return null;
			}
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_sysimages", admin.getUserName(),
				IConstants.INSERT, admin.getLastIP(), 0, "新增系统头像图片", 2);
		return null;

	}

	/**
	 * 更新系统头像
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateSysImageM() throws Exception {
		Long id = Convert.strToLong(paramMap.get("id"), -1L);
		String conte = paramMap.get("titleName");
		if (StringUtils.isBlank(conte)) {
			JSONUtils.printStr("3");
			return null;
		}
		Long result = -1L;
		if (id != null && id != -1 && StringUtils.isNotBlank(conte)) {
			result = emalAndMessageService.updateSysImage(id, conte);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			JSONUtils.printStr("1");
			operationLogService.addOperationLog("t_sysimages", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"修改系统头像图片成功", 2);
			return null;
		} else {
			JSONUtils.printStr("0");
			operationLogService.addOperationLog("t_sysimages", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"修改系统头像图片失败", 2);
			return null;
		}
	}

	/**
	 * 上传系统头像
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deletSysImageM() throws Exception {
		Long id = request.getLong("id", -1);
		if (id != null && id != -1) {
			emalAndMessageService.deletSImage(id);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_sysimages", admin.getUserName(),
				IConstants.DELETE, admin.getLastIP(), 0, "删除系统头像", 2);
		return SUCCESS;
	}

	public String updateSelectType() throws Exception {
		int typeId = request.getInt("typeId", -1);
		long id = request.getLong("id", -1);
		long result = -1L;
		try {
			result = emalAndMessageService.updateSelectType(typeId, id);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			if (result > 0) {
				JSONUtils.printStr("1");
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.UPDATE, admin.getLastIP(),
						0, "修改选择类型成功", 2);
				return null;
			} else {
				JSONUtils.printStr("0");
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.UPDATE, admin.getLastIP(),
						0, "修改选择类型成功", 2);
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 借款期限初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryDeadlineIndex() {
		return SUCCESS;
	}

	/**
	 * 查询借款期限列表
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public String queryDeadlineinfoM() throws Exception {
		emalAndMessageService.queryDeadlineInfo(pageBean);
		return SUCCESS;
	}

	/**
	 * 增加借款期限
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addDeadlineM() throws Exception {

		String titleName = paramMap.get("titleName");
		if(StringUtils.isEmpty(titleName)){
			JSONUtils.printStr("3");
			return null;
		}
		if (!StringUtils.isNumeric(titleName)) {
			JSONUtils.printStr("2");
			return null;
		}
		Long result = -1L;
		if (StringUtils.isNotBlank(titleName)) {
			result = emalAndMessageService.addDeadline(titleName);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			if (result > 0) {
				JSONUtils.printStr("1");
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "新增借款期限成功", 2);
				return null;
			} else {
				JSONUtils.printStr("0");
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "新增借款期限失败", 2);
				return null;
			}
		}
		return null;

	}

	/**
	 * 将id传到弹出框
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updateDeadlineM() throws SQLException, DataException,
			IOException {
		Long id = request.getLong("id", -1);
		request().setAttribute("id", id);
		return SUCCESS;
	}

	public String updateDeadlineRealM() throws Exception {
		Long id = Convert.strToLong(paramMap.get("id"), -1L);
		String conte = paramMap.get("tagd");
		if (!StringUtils.isNumeric(conte)) {
			JSONUtils.printStr("2");
			return null;
		}
		Long result = -1L;
		if (id != null && id != -1 && StringUtils.isNotBlank(conte)) {
			result = emalAndMessageService.updateDeadline(id, conte);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改借款期限成功", 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改借款期限失败", 2);
			JSONUtils.printStr("0");
			return null;
		}
	}

	/**
	 * 金额范围初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryMomeyIndex() {
		return SUCCESS;
	}

	/**
	 * 查询金额范围列表
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public String queryMomeyinfoM() throws Exception {
		emalAndMessageService.queryMomeyInfo(pageBean);
		return SUCCESS;
	}

	/**
	 * 增加金额范围
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addMomeyM() throws Exception {

		String titleName = paramMap.get("titleName");
		if (!StringUtils.isNumeric(titleName)) {
			JSONUtils.printStr("2");
			return null;
		}
		Long result = -1L;
		if (StringUtils.isNotBlank(titleName)) {
			result = emalAndMessageService.addMoney(titleName);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			if (result > 0) {
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "增加金额范围成功", 2);
				JSONUtils.printStr("1");
				return null;
			} else {
				operationLogService.addOperationLog("t_select", admin
						.getUserName(), IConstants.INSERT, admin.getLastIP(),
						0, "增加金额范围失败", 2);
				JSONUtils.printStr("0");
				return null;
			}
		}
		return null;

	}

	/**
	 * 将id传到弹出框
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updateMomeyM() throws SQLException, DataException,
			IOException {
		Long id = request.getLong("id", -1);
		request().setAttribute("id", id);
		return SUCCESS;
	}

	/**
	 * 修改金额范围
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMomeyRealM() throws Exception {
		Long id = Convert.strToLong(paramMap.get("id"), -1L);
		String conte = paramMap.get("tagd");
		if (!StringUtils.isNumeric(conte)) {
			JSONUtils.printStr("2");
			return null;
		}
		Long result = -1L;
		if (id != null && id != -1 && StringUtils.isNotBlank(conte)) {
			result = emalAndMessageService.updateMoney(id, conte);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改金额范围成功", 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			operationLogService.addOperationLog("t_select",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "修改金额范围失败", 2);
			JSONUtils.printStr("0");
			return null;
		}
	}
	
	/**
	 *功能：查找邮件列表
	 * @return
	 */
	public String emailAndMessagelist(){
		try {
			emalAndMessageService.emailAndMessagelist(pageBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 *功能：修改邮件
	 * @return
	 */
	public String emailAndMessageModify(){
		log.info("------emailAndMessageModify---");
		try {
			Map<String, String> mailMap = null;
			String id = request.getString("id");
			int iid = Integer.parseInt(id);
			System.out.println("======id:"+id);
			mailMap = emalAndMessageService.queryMailSet(iid);
			request().setAttribute("mailMap", mailMap);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 *功能：删除邮件
	 * @return
	 */
	public String emailAndMessageDelete(){
		log.info("------emailAndMessageDelete---");
		try {
//			Map<String, String> mailMap = null;
			String id = request.getString("id");
			int iid = Integer.parseInt(id);
			System.out.println("======id:"+id);
			emalAndMessageService.deleteMail(iid);
//			request().setAttribute("mailMap", mailMap);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateMailSetMd(){
		String mailaddress = paramMap.get("maildress");
		String mailpassword = paramMap.get("password");
		String sendmail = paramMap.get("sendEmail");
		String sendname = paramMap.get("username");
		String port = paramMap.get("port");
		String host = paramMap.get("host");
		String id = paramMap.get("id");
		int iid = Integer.parseInt(id);
		try {
			emalAndMessageService.updateMessageSet(iid,mailaddress,
					mailpassword, sendmail, sendname,port,host);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**单参数设置*/
	public String querySysparList(){
		try {
			emalAndMessageService.queryParamlist(pageBean);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	
	/**单参数设置*/
	public String updateParamById(){
		try {
			String id = paramMap.get("id");
			emalAndMessageService.updateParamById(paramMap);
			JSONUtils.printStr("1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String querySysparIndexMethod() {
	    return SUCCESS;
	}
	
	public String querySysparInfoMethod() throws SQLException{
	    emalAndMessageService.querySysparInfo(pageBean);
	    return SUCCESS;
	}

}
