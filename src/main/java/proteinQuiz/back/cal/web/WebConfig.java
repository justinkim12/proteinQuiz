package proteinQuiz.back.cal.web;

import proteinQuiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver.PlayerCheckArgumentResolver;
import proteinQuiz.back.cal.web.Interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PlayerCheckArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/","/api/players");

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://web-proteinquizfront-20zynm2mlk2a7j4g.sel4.cloudtype.app")
                .allowedMethods("POST","GET","PUT","DELETE","HEAD","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}
