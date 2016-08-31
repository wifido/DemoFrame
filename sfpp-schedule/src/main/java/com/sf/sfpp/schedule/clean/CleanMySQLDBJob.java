package com.sf.sfpp.schedule.clean;

import com.alibaba.druid.pool.DruidDataSource;
import com.sf.sfpp.common.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/31
 */
public class CleanMySQLDBJob {
    private final static Logger log = LoggerFactory.getLogger(CleanMySQLDBJob.class);
    private final static String SHOW_TABLES_SQL = "show tables";

    private final String mySQLConnectionString;
    private final String username;
    private final String password;
    private final String includes;
    private final String excludes;
    private final DruidDataSource druidDataSource;


    public CleanMySQLDBJob(String mySQLConnectionString, String username, String password, String includes, String excludes) throws SQLException {
        this.mySQLConnectionString = mySQLConnectionString;
        this.username = username;
        this.password = password;
        this.includes = includes;
        this.excludes = excludes;
        druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(mySQLConnectionString);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(10);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.init();
    }

    public void cleanTables() {
        Statement statement = null;
        try {
            statement = druidDataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SHOW_TABLES_SQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            String columnName = metaData.getColumnLabel(1);
            while(resultSet.next()){
                System.out.println(resultSet.getString(columnName));
            }
            resultSet.close();
        } catch (SQLException e) {
            log.warn(ExceptionUtils.getStackTrace(e));
        }
    }

    public static void main(String[] args) throws SQLException {
        CleanMySQLDBJob cleanMySQLDBJob = new CleanMySQLDBJob("jdbc:mysql://10.202.7.82:3306/pcomp1?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true",
                "mycat", "mycat", "", "");
        cleanMySQLDBJob.cleanTables();
    }
}
