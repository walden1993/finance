package com.sp2p.action.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.database.Dao.Procedures;

public class BaseAppAction extends BasePageAction {
    public static Log log = LogFactory.getLog(BaseAppAction.class);

    protected Map<String, String> getAppAuthMap() {
        return getRequestMap("auth");

    }

    protected Map<String, String> getAppInfoMap() {
        return getRequestMap("info");
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getRequestMap(String requestAttr) {
        /*HttpServletRequest request = request();
        Map<String, String> jsonMap = new HashMap<String, String>();
        System.out.println("request url==>" + request.getContextPath());
        Map<String, Object> paraMap = request.getParameterMap();
        System.out.println("session==>" + session());
        Set<String> keySet = paraMap.keySet();
        System.out.println("=============request value start===============");
        for (String key : keySet) {
            Object val = paraMap.get(key);
            if (val instanceof String[]) {
                System.out.println(key
                                   + "==>"
                                   + Arrays.toString((String[]) val));
                jsonMap.putAll((Map<String, String>) JSONObject.toBean(JSONObject.fromObject(val),
                        HashMap.class));
            } else {
                System.out.println(key + "==>" + val);
            }
        }
        System.out.println("=============request value end===============");
        String json = request(requestAttr);
        Map<String, String> map = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(json),
                                                                          HashMap.class);
        if (map == null) {
            map = new HashMap<String, String>();
            //json
            Map<String, String> mapJson = getJson();
           if (mapJson!=null) {
        	   map.putAll(mapJson);
           }
        }*/
        Map<String, String> mapJson = getJson();
        return mapJson;

    }

    private Map<String, String> getJson() {/*
    	InputStream inputStream = null;
		try {
			inputStream = request().getInputStream();
			byte [] buffer = new byte[512];
			int len = -1;
			StringBuffer stringBuffer = new StringBuffer();
			while((len=inputStream.read(buffer))!=-1){
				String s = new String(buffer, 0, len,"utf-8");
				stringBuffer.append(s);
				s = null;
			}
			JSONObject obj = JSONObject.fromObject(stringBuffer.toString());
			System.out.println("json==========="+obj);
			@SuppressWarnings("unchecked")
			Map<String, String> map= (Map<String, String>) JSONObject.toBean(obj, HashMap.class);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	*/return (Map<String, String>) request().getAttribute("json");}

	public void getStream(String source) {
        try {
            HttpServletRequest request = request();
            log.debug("request url==>" + request.getContextPath());
            Map<String, Object> paraMap = request.getParameterMap();
            log.debug(paraMap);
            log.debug("session==>" + session());
            Set<String> keySet = paraMap.keySet();
            log.debug("=============request value start===============");
            for (String key : keySet) {
                Object val = paraMap.get(key);
                String conten = "";
                if (val instanceof String[]) {
                    log.debug(key + "==>" + Arrays.toString((String[]) val));
                } else {
                    log.debug(key + "==>" + val);
                }
                if (key.equals("str")) {
                    if (val instanceof String[]) {
                        conten = ((String[]) val)[0];
                    } else {
                        conten = val + "";
                    }
                    byte[] bytes = null;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                        oos.writeObject(Base64.encodeBase64((val + "").getBytes()));
                        oos.flush();
                        bytes = bos.toByteArray();
                        oos.close();
                        bos.close();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    FileOutputStream out = new FileOutputStream(new File(source
                                                                         + "/"
                                                                         + "2.TXT"));
                    Writer writer = new OutputStreamWriter(out, "UTF-8");

                    writer.write(conten);
                    writer.flush();
                    writer.close();
                    out.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
