package demo.tdengine;

import com.taosdata.jdbc.TSDBDriver;

import java.sql.*;
import java.util.Properties;

/**
 * TDengine CRUD 基本示例
 *
 * 参考
 * 1.[Java Connector] https://www.taosdata.com/cn//documentation20/connector-java/
 *
 * @author liuxianqiang
 * @since 2020/11/12 13:15
 */
public class QueryDemo {

    public static void main(String[] args) throws Exception {
        Class.forName("com.taosdata.jdbc.TSDBDriver");
        String jdbcUrl = "jdbc:TAOS://127.0.0.1:6030/log?user=root&password=taosdata";
        Properties connProps = new Properties();
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
        Connection conn = DriverManager.getConnection(jdbcUrl, connProps);

        // create statement
        Statement stmt = conn.createStatement();

        // query data
        int count = 0;
        while (true) {
            stmt.executeQuery("select * from log");
            System.out.println("++count=" + ++count);
            Thread.sleep(300);
        }
    }
}
