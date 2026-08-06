package diti.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "diti.repository")
public class AppConfig {

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/diti4_spring_mvc?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true");
        ds.setUsername("root");
        ds.setPassword("madmad@00"); // adapte selon ton installation

        System.out.println("DATASOURCE");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource());
        em.setPackagesToScan("diti.entity");
        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        // options Hibernate (important pour MySQL)
        java.util.Properties props = new java.util.Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        

        em.setJpaProperties(props);
        System.out.println("Entity manager ");
        return em;
    }

    @Bean public PlatformTransactionManager transactionManager( EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}