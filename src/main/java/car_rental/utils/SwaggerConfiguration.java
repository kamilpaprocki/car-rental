package car_rental.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swagger(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("car_rental"))
                //.paths(PathSelectors.regex("/.*"))
                .paths(any())
                .build()
                .apiInfo(apiInfo());
    }


    public ApiInfo apiInfo(){
        return new ApiInfo(
                "Car rental project",
                "This project is a simulation of car rental company.",
                "v1",
                null,
                new Contact("Kamil", null, "kamil.paprocki1@gmail.com"),
                "Kamil Paprocki",
                null,
                Collections.emptyList());



    }

}
