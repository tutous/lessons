package de.tutous.spring.boot;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//@Configuration
//@EnableSwagger2WebMvc
//public class ApplicationSwaggerConf /** implements WebMvcConfigurer **/
//{
//
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)//
//				.select()
//				// select any apis
//				.apis(RequestHandlerSelectors.any())
//				// select any paths
//				.paths(PathSelectors.any())
//				// build
//				.build();
//	}
//
////	@Override
////	public void addResourceHandlers(ResourceHandlerRegistry registry) {
////		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
////		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
////	}
//
//}
