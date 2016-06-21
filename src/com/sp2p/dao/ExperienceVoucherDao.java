package com.sp2p.dao;

import java.sql.Connection;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 体验券Dao
 * 
 * @author hejiahua
 * 
 */
public class ExperienceVoucherDao {
    /**
     * 
     * getExperienceVoucher  获取一张体验券
     * 
     * @auth hejiahua
     * @param conn
     * @param batchId
     * @return
     * @throws DataException
     *             Map<String,String>
     * @exception
     * @date:2014-9-27 下午10:28:49
     * @since 1.0.0
     */
    public Map<String, String> getTicket(Connection conn, int batchId)
            throws DataException {
        Dao.Tables.t_trial_ticket ticket = new Dao().new Tables().new t_trial_ticket();
        DataSet ds = ticket.open(conn, "", " batchId="+ batchId+ " and status=0   ", "", 0, 1);
        return BeanMapUtils.dataSetToMap(ds);
    }
}
