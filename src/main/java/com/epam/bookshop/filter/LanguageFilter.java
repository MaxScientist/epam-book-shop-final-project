package com.epam.bookshop.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.bookshop.constants.ParameterConstants.*;


public class LanguageFilter implements Filter {

    private String locale;
    private Integer localeId;
    private static final String CONFIG_LOCALE = "en";
    private static final Integer CONFIG_LOCALE_ID = 1;
    private static final String INIT_PARAM_LOCALE = "locale";
    private static final String INIT_PARAM_LOCALE_ID = "localeId";


    public void init(FilterConfig filterConfig) {
        locale = filterConfig.getInitParameter(INIT_PARAM_LOCALE);
        localeId = Integer.parseInt(filterConfig.getInitParameter(INIT_PARAM_LOCALE_ID));
        if (locale == null) {
            locale = CONFIG_LOCALE;
            localeId = CONFIG_LOCALE_ID;
        }
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (null == locale || null == localeId) {
            locale = CONFIG_LOCALE;
            localeId = CONFIG_LOCALE_ID;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        String sessionLocale = (String) session.getAttribute(LOCALE);
        Integer sessionLocaleID = (Integer) session.getAttribute(LOCALE_ID);
        if (sessionLocale == null || sessionLocaleID == null) {
            session.setAttribute(LOCALE, locale);
            session.setAttribute(LOCALE_ID, localeId);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
