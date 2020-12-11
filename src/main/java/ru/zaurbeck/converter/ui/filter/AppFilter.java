package ru.zaurbeck.converter.ui.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "wicket.wicket-boot", initParams = {
        @WebInitParam(name = "applicationClassName", value = "WicketConverterApplication")
}, urlPatterns = "/*")
public class AppFilter extends org.apache.wicket.protocol.http.WicketFilter {
    @Override
    public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException {
        super.init(isServlet, filterConfig);
    }
}
