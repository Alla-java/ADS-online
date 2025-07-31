package ru.skypro.homework.filter;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр для обработки CORS запросов с базовой аутентификацией.
 * Добавляет необходимые заголовки для кросс-доменных запросов.
 */
@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {

    /**
     * Обрабатывает каждый запрос, добавляя CORS заголовки
     * @param httpServletRequest входящий запрос
     * @param httpServletResponse исходящий ответ
     * @param filterChain цепочка фильтров
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
