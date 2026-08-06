package az.library.library.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class LiquibasePostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(LiquibasePostProcessor.class);
    private volatile boolean migrated = false;

    @Value("${spring.liquibase.change-log:classpath:db/changelog/db.changelog-master.yaml}")
    private String changeLog;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource && !migrated) {
            synchronized (this) {
                if (!migrated) {
                    runLiquibase((DataSource) bean);
                    migrated = true;
                }
            }
        }
        return bean;
    }

    private void runLiquibase(DataSource dataSource) {
        log.info("Starting Liquibase migrations...");
        try (Connection conn = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(conn));
            String path = changeLog.replace("classpath:", "");
            try (Liquibase liquibase = new Liquibase(path,
                    new ClassLoaderResourceAccessor(), database)) {
                liquibase.update("");
            }
            log.info("Liquibase migrations completed successfully");
        } catch (Exception e) {
            throw new RuntimeException("Liquibase migration failed", e);
        }
    }
}
