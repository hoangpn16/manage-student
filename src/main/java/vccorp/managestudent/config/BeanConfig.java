//package vccorp.managestudent.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class BeanConfig {
//    @Bean
//    DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        HikariDataSource ds;
//
//        config.setPoolName("Connection manage-student database");
//        config.setJdbcUrl( "jdbc:mysql://localhost:3306/manage-student?characterEncoding=UTF-8" );
//        config.setUsername( "root" );
//        config.setPassword( "160200" );
//        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//        config.setMaximumPoolSize(20);
//        config.setAutoCommit(true);
//        ds = new HikariDataSource( config );
//        return ds;
//    }
//}
