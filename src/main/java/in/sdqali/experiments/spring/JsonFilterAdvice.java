package in.sdqali.experiments.spring;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class JsonFilterAdvice implements ResponseBodyAdvice<List> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        List<Annotation> annotations = Arrays.asList(returnType.getMethodAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(JsonFilter.class));
    }

    @Override
    public List beforeBodyWrite(List body, MethodParameter returnType, MediaType selectedContentType, Class<? extends
            HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        List<Map<String, Object>> values = (List<Map<String, Object>>) body;

        // Identify keys we are interested in.
        JsonFilter annotation = returnType.getMethodAnnotation(JsonFilter.class);
        List<String> possibleFilters = Arrays.asList(annotation.keys());

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        List<Map<String, Object>> result = values.stream().filter(map -> {
            boolean match = true;
            Enumeration<String> parameterNames = servletRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if(possibleFilters.contains(parameterName)) {
                    String parameterValue = servletRequest.getParameter(parameterName);
                    Object valueFromMap = map.get(parameterName);
                    match = (valueFromMap != null) && valueFromMap.toString().equals(parameterValue.toString());
                }
            }
            return match;
        }).collect(Collectors.toList());
        return result;
    }
}
