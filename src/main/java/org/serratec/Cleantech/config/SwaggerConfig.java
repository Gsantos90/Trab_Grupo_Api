package org.serratec.Cleantech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cleantechOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cleantech API")
                        .description("API do projeto Cleantech - Sistema de gestão sustentável")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Cleantech")
                                .email("cleantech@serratec.org")
                                .url("https://www.serratec.org"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}