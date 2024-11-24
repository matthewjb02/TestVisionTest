package nl.hu.inno.hulp.grading;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradingApplication {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @PostConstruct
    public void logDatasourceUrl() {
        System.out.println("Configured Datasource URL: " + datasourceUrl);
    }

    public static void main(String[] args) {
        SpringApplication.run(GradingApplication.class, args);
    }

}
