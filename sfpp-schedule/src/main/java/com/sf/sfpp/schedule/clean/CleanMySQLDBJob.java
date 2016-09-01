package com.sf.sfpp.schedule.clean;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.sf.sfpp.common.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/31
 */
public class CleanMySQLDBJob {
    private final static Logger log = LoggerFactory.getLogger(CleanMySQLDBJob.class);
    private final static String SHOW_TABLES_SQL = "show tables";

    private final DruidDataSource druidDataSource;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    public CleanMySQLDBJob(String mySQLConnectionString, String username, String password, String includes, String excludes) throws SQLException {
        druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(mySQLConnectionString);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.init();
    }

    public void cleanTables() {
        DruidPooledConnection connection = null;
        Statement statement = null;
        try {
            connection = druidDataSource.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SHOW_TABLES_SQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            String columnName = metaData.getColumnLabel(1);
            while (resultSet.next()) {
                executorService.submit(new deleteJob(resultSet.getString(columnName), druidDataSource));
            }
            resultSet.close();
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }
}

class deleteJob implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(deleteJob.class);
    private final String sql;

    private final DruidDataSource druidDataSource;

    deleteJob(String tableName, DruidDataSource druidDataSource) {
        this.sql = new StringBuilder().append("delete from ").append(tableName).append(" where is_deleted = 1").toString();
        this.druidDataSource = druidDataSource;
    }

    @Override
    public void run() {
        DruidPooledConnection connection = null;
        Statement statement = null;
        try {
            connection = druidDataSource.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(sql);
            log.info("deleted:{}", i);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }
}
