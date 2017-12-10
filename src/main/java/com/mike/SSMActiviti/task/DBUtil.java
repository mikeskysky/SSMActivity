package com.mike.SSMActiviti.task;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mike.SSMActiviti.task.entity.ScheduleJob;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBUtil {
    /**
     * 查询数据库任务设置,返回列表
     * 
     * @return 数据库任务设置列表
     */
    public static List<ScheduleJob> getScheduleJobs() {
        List<ScheduleJob> result = new ArrayList<ScheduleJob>();
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;

        con = new DBUtil().getDBConnection();
        try {
            if (con != null) {
                smt = (Statement) con.createStatement();
                con.setAutoCommit(false);
                String sql = "SELECT ID,JOB_NAME,JOB_STATUS,CRON_EXPRESSION,CONCURRENT,DESCRIPTION,JOB_GROUP,TARGET_OBJECT,TARGET_METHOD,IS_SPRING_BEAN,CLAZZ,CHILD_JOBS FROM QUARTZ_SCHEDULEJOB";
                rs = smt.executeQuery(sql);
                while (rs.next()) {
                    ScheduleJob sj = new ScheduleJob();
                    sj.setJobId(rs.getString("ID"));
                    sj.setJobName(rs.getString("JOB_NAME"));
                    sj.setJobGroup(rs.getString("JOB_GROUP"));
                    sj.setJobStatus(rs.getString("JOB_STATUS"));
                    sj.setCronExpression(rs.getString("CRON_EXPRESSION"));
                    sj.setDescription(rs.getString("DESCRIPTION"));
                    sj.setTargetMethod(rs.getString("TARGET_METHOD"));
                    sj.setTargetObject(rs.getString("TARGET_OBJECT"));
                    sj.setIsSpringBean(rs.getString("IS_SPRING_BEAN"));
                    sj.setClazz(rs.getString("CLAZZ"));
                    sj.setConcurrent(rs.getString("CONCURRENT"));
                    sj.setChildJobs(rs.getString("CHILD_JOBS"));

                    result.add(sj);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static ScheduleJob getScheduleJobById(String id) {
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        ScheduleJob sj = null;
        con = new DBUtil().getDBConnection();
        try {
            if (con != null) {
                smt = (Statement) con.createStatement();
                con.setAutoCommit(false);
                String sql = "SELECT "
                        + "ID,JOB_NAME,JOB_STATUS,CRON_EXPRESSION,CONCURRENT,DESCRIPTION,JOB_GROUP,"
                        + "TARGET_OBJECT,TARGET_METHOD,IS_SPRING_BEAN,CLAZZ,CHILD_JOBS "
                        + "FROM QUARTZ_SCHEDULEJOB WHERE ID = '"+id+"'";
                rs = smt.executeQuery(sql);
                while (rs.next()) {
                    sj = new ScheduleJob();
                    sj.setJobId(rs.getString("ID"));
                    sj.setJobName(rs.getString("JOB_NAME"));
                    sj.setJobGroup(rs.getString("JOB_GROUP"));
                    sj.setJobStatus(rs.getString("JOB_STATUS"));
                    sj.setCronExpression(null);//子任务不要
//                  sj.setCronExpression(rs.getString("CRON_EXPRESSION"));
                    sj.setDescription(rs.getString("DESCRIPTION"));
                    sj.setTargetMethod(rs.getString("TARGET_METHOD"));
                    sj.setTargetObject(rs.getString("TARGET_OBJECT"));
                    sj.setIsSpringBean(rs.getString("IS_SPRING_BEAN"));
                    sj.setClazz(rs.getString("CLAZZ"));
                    sj.setConcurrent(rs.getString("CONCURRENT"));
                    sj.setChildJobs(rs.getString("CHILD_JOBS"));
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sj;
    }

    /**
     * 根据主工作名称获取子工作
     * @param mainJob
     */
    public static String getChildsJobByMainJobName(String mainJob) {
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        con = new DBUtil().getDBConnection();
        String result = "";
        try {
            if (con != null) {
                smt = (Statement) con.createStatement();
                con.setAutoCommit(false);
                //因为只是主任务需要cron子任务不需要，所以只需要查找childs_jobs
                String sql = "SELECT CHILD_JOBS FROM QUARTZ_SCHEDULEJOB WHERE JOB_NAME = '"+mainJob+"'";
                rs = smt.executeQuery(sql);
                if(rs.next()){
                    result = rs.getString(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public Connection getDBConnection() {
        Connection conn = null;
        String driver = "";
        String url = "";
        String user = "";
        String password = "";
        Properties props = new Properties();
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream("default.properties"));
            driver = props.getProperty("jdbc.driver");
            url = props.getProperty("jdbc.url");
            user = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");

            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, user, password);

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
