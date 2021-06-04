//package Task1.config;
//
//import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//public class HibernateConfig {
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost/db1");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("123");
//
//        return dataSource;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("Task1.domain.entity");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }
//
//    private Properties hibernateProperties() {
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty(
//                "hibernate.hbm2ddl.auto", "none");
//        //generate tables from entities
///*
//        hibernateProperties.setProperty(
//                "hibernate.hbm2ddl.auto", "create-drop");
//*/
//        hibernateProperties.setProperty(
//                "hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
//        hibernateProperties.setProperty(
//                "hibernate.show_sql", "false");
//        hibernateProperties.setProperty(
//                "hibernate.format_sql", "true");
///*
//        hibernateProperties.setProperty(
//                "hibernate.use_sql_comments", "true");
//*/
//        return hibernateProperties;
//    }
//}
