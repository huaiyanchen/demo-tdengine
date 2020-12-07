package demo.tdengine;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * TDengine with HikariCP
 *
 * 参考
 * 1.[Java Connector] https://www.taosdata.com/cn//documentation20/connector-java/
 *
 * @author liuxianqiang
 * @since 2020/11/12 14:53
 */
public class DruidDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName","com.taosdata.jdbc.TSDBDriver");
        properties.put("url","jdbc:TAOS://192.168.1.124:6030/demo");
        properties.put("username","root");
        properties.put("password","taosdata");
        properties.put("maxActive","10"); //maximum number of connection in the pool
        properties.put("initialSize","3");//initial number of connection
        properties.put("maxWait","10000");//maximum wait milliseconds for get connection from pool
        properties.put("minIdle","3");//minimum number of connection in the pool
        properties.put("timeBetweenEvictionRunsMillis","3000");// the interval milliseconds to test connection
        properties.put("minEvictableIdleTimeMillis","60000");//the minimum milliseconds to keep idle
        properties.put("maxEvictableIdleTimeMillis","90000");//the maximum milliseconds to keep idle
        properties.put("validationQuery","select server_status()"); //validation query
        properties.put("testWhileIdle","true"); // test connection while idle
        properties.put("testOnBorrow","false"); // don't need while testWhileIdle is true
        properties.put("testOnReturn","false"); // don't need while testWhileIdle is true

        properties.put("init","true"); // temp add
        //create druid datasource
        DataSource ds = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = ds.getConnection(); // get connection

        System.out.println(connection);
    }
}
