package ZtechAplication;
//Pagina 32 do arquivo referencia do professor

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
public class DataConfiguration {
//
//	//tem que configurar em relação ao banco
//    String url = "jdbc:mysql://localhost:3306/testes2?useSSL=false&serverTimezone=UTC";
//    String username = "root";
//    String password = "1234";
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/testes?useSSL=false&serverTimezone=UTC");
//        dataSource.setUsername("root"); 
//        dataSource.setPassword("1234"); 
//
//        return dataSource;
//    }
//
//    
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setDatabase(Database.MYSQL);
//        adapter.setShowSql(true);
//        adapter.setGenerateDdl(true);
//        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
//        adapter.setPrepareConnection(true);
//
//        return adapter;
//    }

}
