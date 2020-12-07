package demo.tdengine;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

/**
 * TDengine with HikariCP
 *
 * 参考
 * 1.[Java Connector] https://www.taosdata.com/cn//documentation20/connector-java/
 *
 * @author liuxianqiang
 * @since 2020/11/12 14:53
 */
public class HikariCPDemo {
    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:TAOS://192.168.1.124:6030/demo");
        config.setUsername("root");
        config.setPassword("taosdata");
        config.setMinimumIdle(3);           //minimum number of idle connection
        config.setMaximumPoolSize(10);      //maximum number of connection in the pool
        config.setConnectionTimeout(10000); //maximum wait milliseconds for get connection from pool
        config.setIdleTimeout(60000);       // max idle time for recycle idle connection
        config.setConnectionTestQuery("describe demo.tb"); //validation query
        config.setValidationTimeout(3000);  //validation query timeout
        HikariDataSource ds = new HikariDataSource(config); //create datasource
        Connection conn = ds.getConnection(); // get connection

        System.out.println(conn);
    }
}
