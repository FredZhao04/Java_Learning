package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Created by fred on 2018/6/10.
 */
public class Utils {
    public static QueryRunner getRunner(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner runner = new QueryRunner(dataSource);
        return runner;
    }
}
