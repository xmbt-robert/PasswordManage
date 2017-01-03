package com.fx.passform.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 *  OptLog's Moel
 *
 * Created by fan.xu on 2014/10/21.
 */
public class OptLog extends Model<OptLog> {
    public static final OptLog dao = new OptLog();
    public Page<OptLog> findAll(int pageNum, int pageCount) {
        return dao.paginate(pageNum, pageCount, "select * ", "from sys_opt_logs where 1=1 order by id desc");
    }
}