package org.fartpig.dbunit_extractor_web;

import java.util.Locale;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "org.fartpig.dbunit_extractor_web.service",
		"org.fartpig.dbunit_extractor_web.web" })
@EnableTransactionManagement
@EnableAsync
@EnableCaching
@EntityScan(basePackages = "org.fartpig.dbunit_extractor_web.model")
public class App extends SpringBootServletInitializer {

	private static Logger log = LoggerFactory.getLogger(App.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new MySiteMeshFilter());
		return filterRegistrationBean;
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
		return mapping;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.CHINA);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {

		@Override
		protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
			builder.addDecoratorPath("/*", "/index").addExcludedPath("/index")
					.addExcludedPath("/static/**").addTagRuleBundle(new Sm2TagRuleBundle());
		}

	}

	@Bean
	public CommandLineRunner commandLineRunner(org.springframework.context.ApplicationContext ctx) {

		return args -> {
			if (args.length > 1) {
			}
		};

	}

	@Bean
	public WebMvcConfigurerAdapter adapter() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void configureViewResolvers(ViewResolverRegistry arg0) {
				InternalResourceViewResolver resolver = new InternalResourceViewResolver();
				resolver.setPrefix("/WEB-INF/jsp/");
				resolver.setSuffix(".jsp");
				resolver.setViewClass(JstlView.class);
				arg0.viewResolver(resolver);
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
				super.addInterceptors(registry);
			}

		};
	}
}
