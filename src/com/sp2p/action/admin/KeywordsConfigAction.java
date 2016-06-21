package com.sp2p.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.admin.KeywordsConfigService;

import net.sf.json.JSONObject;

public class KeywordsConfigAction extends BasePageAction {
	public static Log log = LogFactory.getLog(KeywordsConfigAction.class);
	
	private KeywordsConfigService keywordsConfigService;

	public KeywordsConfigService getKeywordsConfigService() {
		return keywordsConfigService;
	}

	public void setKeywordsConfigService(KeywordsConfigService keywordsConfigService) {
		this.keywordsConfigService = keywordsConfigService;
	}
	
	/**
	 * 查询列表初始化
	 * @return
	 */
	public String queryKeywordsinit(){
		return SUCCESS;
	}
	
	/**
	 * 添加关键字初始化
	 * @return
	 */
	public String addKeywordsinit(){
		return SUCCESS;
	}

	/**
	 * 添加一个关键字
	 * @return
	 * @throws Exception
	 */
	public String addKeywords() throws Exception {
		String words = Convert.strToStr(paramMap.get("words"), "");
		int isuse = Convert.strToInt(paramMap.get("isuse"), -1);
		try {
			keywordsConfigService.addKeywords(words, isuse);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改关键字使用状态
	 * @return
	 * @throws Exception
	 */
	public String updateKeywordsState() throws Exception {
		int id = Convert.strToInt(request("id"), -1);
		int isuse = Convert.strToInt(request("isuse"), -1);
		try {
			keywordsConfigService.updateKeywordsState(id, isuse);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询关键字列表
	 * @return
	 * @throws Exception
	 */
	public String queryKeywords() throws Exception {
		String words = Convert.strToStr(paramMap.get("words"), "");
		int isuse = Convert.strToInt(paramMap.get("isuse"), -1);
		try {
			keywordsConfigService.queryKeywords(pageBean, words, isuse);
			this.setRequestToParamMap();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}

}
