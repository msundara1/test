package com.bt.creditappservices.container;

import com.google.common.base.Predicates;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author msundara
 */
@SpringBootApplication(scanBasePackages = {"com.bt.creditappservices"})
@EnableJpaRepositories(basePackages="com.bt.creditappservices")
@EnableTransactionManagement
@EntityScan(basePackages={"com.bt.creditappservices"})
@EnableAutoConfiguration
public class CreditAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(CreditAppApplication.class, args);
  }

  private static final Set<String> DEFAULT_PRODUCES = new HashSet<String>(
      Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
  private static final Set<String> DEFAULT_CONSUMES = new HashSet<String>(
      Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
}

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.bt.creditappservices"})
class SwaggerConfig implements  WebMvcConfigurer {

  @Bean
  public Docket swaggerV1Apis() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("CreditApp API v1")
        .select()
        .paths(Predicates.or(PathSelectors.regex("/status/.*"), PathSelectors.regex("/v1/creditapp/.*")))
        .build()
        .apiInfo(new ApiInfoBuilder().version("v1").title("CreditApp API").description("CreditApp API version 1 documentation").build());
  }

}

