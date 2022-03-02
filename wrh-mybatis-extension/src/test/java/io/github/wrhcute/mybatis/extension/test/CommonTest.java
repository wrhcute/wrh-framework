package io.github.wrhcute.mybatis.extension.test;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;


/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName CommonTest.java
 * @Description TODO
 * @createTime 2022年03月02日 11:33:00
 */
public class CommonTest {

    protected SqlSessionFactory sqlSessionFactory;


    @Before
    public void before() {
        System.setProperty("javax.xml.accessExternalDTD","all");
        DataSource dataSource = new PooledDataSource(
                "com.mysql.cj.jdbc.Driver"
                , "jdbc:mysql://172.18.186.253:3306/open_order?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&serverTimezone=GMT%2B8"
                , "root"
                , "6#nwY719!cqeL3M!");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
/*        configuration.addMapper(UserMapper.class);
        configuration.addMappers("");
        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(new Properties());
        configuration.addInterceptor(pageHelper);
        configuration.addInterceptor(new PojoInterceptor());
        configuration.setLogImpl(Slf4jImpl.class);*/
        URL resource = CommonTest.class.getResource("/TestMapper.xml");
        String mapperPath = resource.getPath();
        File mapperFile = new File(mapperPath);
        try {
            XMLMapperBuilder mapBuilder
                    = new XMLMapperBuilder(new FileInputStream(mapperFile)
                    , configuration, mapperFile.getAbsolutePath(), configuration.getSqlFragments());
            mapBuilder.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Test
    public void test_fsqlcall(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            sqlSession.select("selectOrderByIds", Arrays.asList(3, 4, 5), new ResultHandler() {
                @Override
                public void handleResult(ResultContext resultContext) {
                    Object resultObject = resultContext.getResultObject();
                    System.out.println(resultObject);
                }
            });
        }
    }
}
