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
public class CrudDemo {

    public static void main(String[] args) throws Exception {
        Class.forName("com.taosdata.jdbc.TSDBDriver");
        String jdbcUrl = "jdbc:TAOS://192.168.1.124:6030/power?user=root&password=taosdata";
        Properties connProps = new Properties();
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
        Connection conn = DriverManager.getConnection(jdbcUrl, connProps);

        // create statement
        Statement stmt = conn.createStatement();
        // create database
        stmt.executeUpdate("create database if not exists demo");
        // use database
        stmt.executeUpdate("use demo");
        // create table
        stmt.executeUpdate("create table if not exists tb (ts timestamp, temperature int, humidity float)");

        // insert data
        for (int i = 1; i <= 100; i++) {
            stmt.executeUpdate("insert into tb values(now + " + i + "S, " + i + ", " + i*100+ ",)");
        }

        // query data
        ResultSet resultSet = stmt.executeQuery("select * from tb");
        while(resultSet.next()){
            Timestamp ts = resultSet.getTimestamp(1);
            int temperature = resultSet.getInt(2);
            float humidity = resultSet.getFloat("humidity");
            System.out.printf("%s, %d, %s\n", ts, temperature, humidity);
        }

        resultSet.close();
        stmt.close();
        conn.close();;
    }
}
