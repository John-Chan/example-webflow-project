package io.hosuaby.webflow;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.DelegatingFlowViewResolver;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.mvc.view.FlowViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * Web flow configuration.
 */
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    /**  Thymeleaf view resolver for Spring MVC */
    @Autowired
    private ThymeleafViewResolver viewResolver;

    /**
     * @return Flow Definition Registry bean
     */
    @Bean
    public FlowDefinitionRegistry registry() {
        return getFlowDefinitionRegistryBuilder(builderServices())
                .setBasePath("classpath:flows")
                .addFlowLocation("/teapots/new/new.xml")
                .build();
    }

    /**
     * @return central service for executing flows
     */
    @Bean
    public FlowExecutor executor() {
        return getFlowExecutorBuilder(registry()).build();
    }

    /**
     * @return Flow handler mapping to intercept HTTP requests to the flow
     */
    @Bean
    public FlowHandlerMapping handlerMapping() {
        FlowHandlerMapping mapping = new FlowHandlerMapping();
        mapping.setOrder(1);
        mapping.setFlowRegistry(registry());
        return mapping;
    }

    /**
     * @return Spring MVC adapter that handles dispatcher requests to the flows
     */
    @Bean
    public FlowHandlerAdapter handlerAdapter() {
        FlowHandlerAdapter adapter = new FlowHandlerAdapter();
        adapter.setFlowExecutor(executor());
        return adapter;
    }

    /**
     * @return view resolver that delegates view resolution to thymeleaf
     *         resolver
     */
    @Bean
    public FlowViewResolver viewResolver() {
        return new DelegatingFlowViewResolver(new ArrayList<ViewResolver>() {
            private static final long serialVersionUID = 1L;

            {
                add(viewResolver);
            }
        });
    }

    /**
     * @return view factory resolver creator
     */
    @Bean
    public MvcViewFactoryCreator viewFactoryResolverCreator() {
        MvcViewFactoryCreator viewFactoryResolver = new MvcViewFactoryCreator();
        viewFactoryResolver.setFlowViewResolver(viewResolver());
        viewFactoryResolver.setUseSpringBeanBinding(true);
        return viewFactoryResolver;
    }

    /**
     * @return Flow builder services
     */
    @Bean
    public FlowBuilderServices builderServices() {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(viewFactoryResolverCreator())
                .setDevelopmentMode(true)
                .build();
    }

}
