package com.caci.test.bricks.data.context;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "dataEntityManagerFactory", transactionManagerRef = "dataTransactionManager", basePackages = {
        "com.caci.test.bricks.data.repository" })
@PropertySource("classpath:application.properties")
public class PersistanceContext {

	private static final Logger logger = LoggerFactory.getLogger(PersistanceContext.class);

	private static final String HIB_JDBC_DRIVER_KEY = "hibernate.ds.jdbc.classname.driver";
	private static final String HIB_JDBC_URL_KEY = "hibernate.ds.jdbc.url";
	private static final String HIB_DS_USERNAME_KEY = "hibernate.ds.username";
	private static final String HIB_DS_PASSWORD_KEY = "hibernate.ds.password";
	
	private static final String HIB_JPA_DIALECT = "hibernate.jpa.sql.dialect";
	private static final String HIB_JPA_SQL_SHOW = "hibernate.jpa.sql.show";
	private static final String HIB_JPA_SQL_FORMAT = "hibernate.jpa.sql.format";
	private static final String HIB_JPA_SQL_INSERTS= "hibernate.jpa.sql.inserts";	
	private static final String HIB_JPA_SQL_UPDATES = "hibernate.jpa.sql.updates";
	
	private static final String HIB_JPA_DATABASE = "hibernate.jpa.vendoradapter.database";
	private static final String HIB_JPA_DEFAULT_TIMEOUT = "hibernate.jpa.transaction.default.timeout";
	public static final String VALIDATION_QUERY = "SELECT 1";
	public static final boolean TEST_ON_BORROW = true;

	@Bean(name = "dataDS")
	public DataSource dataSource(Environment env) {
		
		DataSource ds = new DataSource();
		
		final String url = env.getProperty(HIB_JDBC_URL_KEY);
		final String user = env.getProperty(HIB_DS_USERNAME_KEY);
		
		logger.info("***************************************************** BRICKS-APP Database Connection *****************************************************");
		logger.info("Url: " + url + " - User: " +  user);
		
		ds.setDriverClassName(env.getProperty(HIB_JDBC_DRIVER_KEY));		
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(env.getProperty(HIB_DS_PASSWORD_KEY));
        ds.setTestOnBorrow(TEST_ON_BORROW);
        ds.setValidationQuery(VALIDATION_QUERY);
		return ds;
	}
	
	@Bean(name="dataJpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter(Environment env) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setDatabase(Database.valueOf(env.getProperty(HIB_JPA_DATABASE)));
		return jpaVendorAdapter;
	}

    @Bean(name = "dataEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataDS")DataSource dataSource, Environment env, @Qualifier("dataJpaVendorAdapter")JpaVendorAdapter jpaVendorAdapter) {

		Properties jpaProperties = new Properties();
		
		jpaProperties.put("hibernate.dialect", env.getProperty(HIB_JPA_DIALECT));
		jpaProperties.put("hibernate.show_sql", env.getProperty(HIB_JPA_SQL_SHOW));
		jpaProperties.put("hibernate.format_sql", env.getProperty(HIB_JPA_SQL_FORMAT));
		jpaProperties.put("hibernate.order_inserts", env.getProperty(HIB_JPA_SQL_INSERTS));
		jpaProperties.put("hibernate.order_updates", env.getProperty(HIB_JPA_SQL_UPDATES));
		
		jpaProperties.put("hibernate.jdbc.batch_size", "30");
		jpaProperties.put("hibernate.order_inserts", "true");
		jpaProperties.put("hibernate.order_updates", "true");
		jpaProperties.put("hibernate.jdbc.batch_versioned_data", "true");

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource(env));
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter(env));
		entityManagerFactory.setPackagesToScan("com.caci.test.bricks.data.domain");
		entityManagerFactory.setJpaProperties(jpaProperties);
        entityManagerFactory.setPersistenceUnitName("BRICKS-DATA");
		
		return entityManagerFactory;
		
	}

    @Bean(name = "dataTransactionManager")
	public JpaTransactionManager transactionManager(@Qualifier("dataEntityManagerFactory")EntityManagerFactory entityManagerFactory, Environment env) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDefaultTimeout(Integer.parseInt(env.getProperty(HIB_JPA_DEFAULT_TIMEOUT)));
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
