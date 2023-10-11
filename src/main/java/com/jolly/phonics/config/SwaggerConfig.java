package com.jolly.phonics.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	
	/*private ArrayList<ApiKey> apiKeys() {
		ArrayList<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("JWT", AUTHORIZATION_HEADER, "header"));
		return apiKeys;
	}*/
	
	private List<SecurityScheme> apiKeys() {
	    List<SecurityScheme> apiKeys = new ArrayList<>();
	    apiKeys.add(new ApiKey("JWT", AUTHORIZATION_HEADER, "header"));
	    return apiKeys;
	}
	
	private ArrayList<SecurityContext> securityContexts(){
		ArrayList<SecurityContext> sf = new ArrayList<>();
		sf.add(securityContext());
		return sf;
	}
	
	private SecurityContext securityContext() {
	    AuthorizationScope[] scopes = { new AuthorizationScope("global", "access everything") };
	    SecurityReference reference = new SecurityReference("JWT", scopes);
	    List<SecurityReference> references = new ArrayList<>();
	    references.add(reference);
	    return SecurityContext.builder()
	            .securityReferences(references)
	            .build();
	}

	/*@Bean
    public Docket atividadeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jolly.phonics"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(metaInfo())
				.securityContexts()
				.securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jolly.phonics"))
                .paths(PathSelectors.any())
                .build();
                
    }*/
	@Bean
    public Docket atividadeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaInfo())
                .securityContexts(securityContexts())
                .securitySchemes(apiKeys())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jolly.phonics"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Jolly Phonics",
                "API REST Jolly Phonics.",
                "1.0",
                "Terms of Service",
                new Contact("João VR", "www.una.br/",
                        " "),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
}
