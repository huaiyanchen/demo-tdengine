package demo.tdengine;

import com.taosdata.jdbc.TSDBConnection;
import com.taosdata.jdbc.TSDBDriver;
import com.taosdata.jdbc.TSDBResultSet;
import com.taosdata.jdbc.TSDBSubscribe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * TDengine 订阅 基本示例
 *
 * 参考
 * 1.[Java Connector] https://www.taosdata.com/cn//documentation20/connector-java/
 * 2.[常见问题] https://github.com/taosdata/TDengine/issues/
 *
 * @author liuxianqiang
 * @since 2020/11/12 13:15
 */
public class SubscribeDemo {

    public static void main(String[] args) throws Exception {
        Class.forName("com.taosdata.jdbc.TSDBDriver");
        String jdbcUrl = "jdbc:TAOS://192.168.1.124:6030/demo?user=root&password=taosdata";
        Properties connProps = new Properties();
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
        Connection conn = DriverManager.getConnection(jdbcUrl, connProps);

        TSDBSubscribe sub = ((TSDBConnection)conn).subscribe("topic", "select * from tb", false);

        // 可以用Timer
        int total = 0;
        while(true) {
            TSDBResultSet rs = sub.consume();
            int count = 0;
            while(rs.next()) {
                Timestamp ts = rs.getTimestamp("ts");
                int temperature = rs.getInt("temperature");
                float humidity = rs.getFloat("humidity");
                System.out.printf("inserted row (%s, %d, %s)\n", ts, temperature, humidity);
                count++;
            }
            total += count;
            System.out.printf("%d rows consumed, total %d\n", count, total);

            Thread.sleep(5000);
        }

//        sub.close(true);
//        stmt.close();
//        conn.close();;
    }
}
