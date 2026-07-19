package az.library.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System API")
                        .description("Kitabxana İdarəetmə Sistemi — Kitablar, Müəlliflər, Kategoriyalar, Nəşriyyatlar, Üzvlər, Ödənişlər, Rezervasiyalar və Kitab Nüsxələri üzrə REST API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Library Dev Team")
                                .email("me@rajabmirzayev.dev")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development")));
    }
}
