package caloryquiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class PlayerCheckArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
      log.info("Support Parameter 실행");
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(PlayerCheck.class);
        boolean hasPlayerType = caloryquiz.back.cal.player.Player.class.isAssignableFrom(parameter.getParameterType());

        return hasParameterAnnotation&&hasPlayerType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("ResolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session==null)
            return null;

        return session.getAttribute("player");
    }
}
