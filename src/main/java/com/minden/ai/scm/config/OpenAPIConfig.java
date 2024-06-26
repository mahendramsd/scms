package com.minden.ai.scm.config;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

/**
 * @author mahendrasridayarathna
 * @created 29/04/2024 - 1:37 pm
 * @project IntelliJ IDEA
 */
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL in Development environment");


        Contact contact = new Contact();
        contact.setEmail("mahendrasridayarathna@gmail.com");
        contact.setName("Mahendra");
        contact.setUrl("");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Simple Course Management System")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Course Management System.").termsOfService("")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
