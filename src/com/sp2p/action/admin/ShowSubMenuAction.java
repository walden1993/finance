package com.sp2p.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RoleRightsService;

public class ShowSubMenuAction extends BasePageAction{
	public static Log log = LogFactory.getLog(ShowSubMenuAction.class);
	private RoleRightsService roleRightsService;
	private AdminService adminService;
	
	public void setRoleRightsService(RoleRightsService roleRightsService) {
		this.roleRightsService = roleRightsService;
	}
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 查询一级菜单下二级菜单的权限
	 * @return
	 * @throws Exception 
	 */
	public String showsubmenu() throws Exception{
		String index = request.getString("index");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long roleId = admin.getRoleId();
		List<Map<String, Object>> list = null;
		try {
			list = roleRightsService.queryAdminRoleRightMenu(roleId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		//--审核管理
		Map<String,String> map = adminService.queryCheckCount(admin.getId());
		session().setAttribute("map", map);
		
		session().setAttribute("adminRoleMenuList", list);
		
		session().setAttribute(IConstants.SESSION_ADMIN, admin);
		session().setAttribute("index", index);
		return SUCCESS;
	}
	
	/**
	 * 加载菜单
	 * menu
	 * @auth hejiahua
	 * @return 
	 * String
	 * @throws Exception 
	 * @exception 
	 * @date:2014-11-1 上午11:41:53
	 * @since  1.0.0
	 */
	public String  menu() throws Exception{
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        long roleId = admin.getRoleId();
        List<Map<String, Object>> list = null;
        try {
            //查询出该用户的所有权限功能
            list = roleRightsService.queryAdminRoleRightMenu(roleId);
            //开始筛选
            menus.clear();
            groupRole(list);
            JSONUtils.printArray(menus);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } 
	    return null;
	}
	
	List<Map<String, Object>> menus = new ArrayList<Map<String,Object>>();
	/**
	 * 顶级权限
	 */
	public void groupRole(List<Map<String, Object>> list){
	    //得到顶级功能
	    for (Map<String, Object> map : list) {
            if ((map.get("parentId")== null || "".equals(map.get("parentId"))) && map.get("rightsId")!=null &&map.get("summary")!=null) {
                Map<String, Object> menu = new HashMap<String, Object>();
                menu.put("id", map.get("rightsId").toString());
                menu.put("rightsId", map.get("rightsId"));
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("text", map.get("summary"));
                menu.put("menu",item );//下级
                menus.add(menu);
            };
        }
	    groupTwo(menus,list);
	}
	/**
	 * 得到二以下的菜单
	 */
	@SuppressWarnings("unchecked")
    public void groupTwo(List<Map<String, Object>> menus,List<Map<String, Object>> list){//二级菜单
	   for (Map<String, Object> one : menus) {
	       if (one.get("rightsId")!=null) {
	           long rightsId = Convert.strToLong(one.get("rightsId").toString(), -1L);
	           List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
	           for (Map<String, Object> two : list) {
	               if (two.get("parentId")!= null && two.get("rightsId")!=null &&two.get("summary")!=null) {
    	                   long parentId =Convert.strToLong(two.get("parentId").toString(), -1L); 
    	                   if (rightsId==parentId) {
    	                       Map<String, Object> menu = new HashMap<String, Object>();
    	                       menu.put("id", two.get("rightsId").toString());
    	                       menu.put("rightsId", two.get("rightsId"));
    	                       menu.put("href", two.get("action"));
    	                       menu.put("text",two.get("summary"));
    	                       HashMap<String, Object> item = new HashMap<String, Object>();
    	                       item.put("text", two.get("summary"));
    	                       menu.put("menu",item );//下级
    	                       items.add(menu);
    	                   }
	               }
	           }
	           HashMap<String, Object> menu = (HashMap<String, Object>) one.get("menu");
	           if (items.size()>0) {
	              menu.put("items", items);
	               one.put("menu", menu);
	           }else {
	               one.remove("menu");
	           }
	           one.remove("rightsId");
	           groupTwo(items, list);
	       }
	   }
	}
}
