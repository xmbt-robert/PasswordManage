package com.fx.passform.model;

import com.fx.passform.util.StringUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Answer's Model
 * <p/>
 * Created by fan.xu on 2014/10/21.
 */
public class Answer extends Model<Answer> {
    public static final Answer dao = new Answer();

    /**
     * 增加密保问题
     *
     * @param user
     * @param staffid
     * @param q1
     * @param a1
     * @param q2
     * @param a2
     * @param q3
     * @param a3
     */
    public boolean addAnswer(String user, String staffid, String q1, String a1,
                             String q2, String a2, String q3, String a3) {
        Answer answer = new Answer();
        answer.set("staffId", staffid).set("userName", user).set("question1", q1);
        answer.set("answer1", a1).set("question2", q2).set("answer2", a2);
        return answer.set("question3", q3).set("answer3", a3).save();
    }

    /**
     * 根据用户名和工号，得到密保
     *
     * @param user
     * @param staffid
     * @return
     */
    public Answer getAnswer(String user, String staffid) {
        List<Answer> answers = find("select * from data_registered where userName ="
                + user + " and staffId=" + staffid);
        if (answers.size() == 0) {
            return null;
        }

        return answers.get(0);
    }

    /**
     * 根据用户名、工号更新密保
     *
     * @param user
     * @param staffid
     * @param q1
     * @param a1
     * @param q2
     * @param a2
     * @param q3
     * @param a3
     * @return
     */
    public boolean updAnswer(String user, String staffid, String q1, String a1,
                             String q2, String a2, String q3, String a3) {
        Answer answer = dao.findById(staffid);
        answer.set("question1", q1).set("answer1", a1);
        answer.set("question2", q2).set("answer2", a2);
        answer.set("question3", q3).set("answer3", a3);
        return answer.update();
    }

    /**
     * 分页查询所有密保
     *
     * @param pageNum   　页码
     * @param pageCount 　每页总条数
     * @return
     */
    public Page<Answer> findAll(int pageNum, int pageCount) {
        return dao.paginate(pageNum, pageCount, "select * ", "from data_registered");
    }

    /**
     * 根据条件进行查询分析
     *
     * @param pageNum
     * @param pageCount
     * @param user
     * @param staff
     * @return
     */
    public Page<Answer> findSms(int pageNum, int pageCount, String user, String staff) {
        //组装条件
        StringBuilder builder = new StringBuilder(512);
        builder.append("from data_registered where ");
        builder.append(StringUtil.isValid(user) ? " userName like ?" : "");
        builder.append(StringUtil.isValid(staff) ? " and staffId like ?" : "");

        //组装值
        ArrayList vals = new ArrayList();
        if (StringUtil.isValid(user)) vals.add("%" + user + "%");
        if (StringUtil.isValid(staff)) vals.add("%" + staff + "%");

        return dao.paginate(pageNum, pageCount, "select * ", builder.toString(), vals.toArray()[vals.size()]);
    }
}
