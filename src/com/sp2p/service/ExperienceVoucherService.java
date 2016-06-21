package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.web.Utility;
import com.sp2p.dao.ExperienceVoucherDao;
import com.sp2p.database.Dao.Procedures;

/**
 * 体验券service
 * 
 * @author hejiahua
 * 
 */
public class ExperienceVoucherService extends BaseService {
    private ExperienceVoucherDao experienceVoucherDao;
    private Log log = LogFactory.getLog(ExperienceVoucherService.class);

    public ExperienceVoucherDao getExperienceVoucherDao() {
        return experienceVoucherDao;
    }

    public void setExperienceVoucherDao(ExperienceVoucherDao experienceVoucherDao) {
        this.experienceVoucherDao = experienceVoucherDao;
    }

    /**
     * 根据批次号获取一张体验券 getExperienceVoucher
     * 
     * @auth hejiahua
     * @param batchId
     *            批次号
     * @return Map<String,String> 返回一张体验券
     * @throws SQLException
     * @exception
     * @date:2014-9-27 下午10:22:29
     * @since 1.0.0
     */
    public Map<String, String> getTicket(int batchId) throws SQLException {
        Connection conn = MySQL.getConnection();
        Map<String, String> map = null;
        try {
            map = experienceVoucherDao.getTicket(conn, batchId);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.info(e);
        }
        finally {
            conn.close();
        }
        return map;
    }

    /**
     * 
     * activeTicket  激活我的体验券信息 
     * @auth hejiahua
     * @param userId  用户id
     * @param ticketNo  体验券号
     * @return
     * @throws Exception 
     * Map<String,String>
     * @exception 
     * @date:2014-9-27 下午10:34:24
     * @since  1.0.0
     */
    public Map<String, String> activeTicket(int userId, String ticketNo)
            throws Exception {
        log.info("className:"+ this.getClass().getName()+ ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        ticketNo = Utility.filteSqlInfusion(ticketNo);
        Connection conn = MySQL.getConnection();
        long ret = -1;
        DataSet ds = new DataSet();
        List<Object> outParameterValues = new ArrayList<Object>();
        Map<String, String> map = new HashMap<String, String>();
        try {
            Procedures.p_trial_ticket_binding(conn, ds,outParameterValues,userId,ticketNo, 0, "");
            ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
            map.put("ret", ret + "");
            map.put("ret_desc", outParameterValues.get(1) + "");
            if (ret <= 0) {
                conn.rollback();
            }
            conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
        }
        finally {
            conn.close();
        }
        return map;
    }

}
