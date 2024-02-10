package nl.grip.trimfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrimFilter implements Filter {
    private FilterConfig        config;
    private boolean             no_init = true;
    private String              excluded;
    private static final String EXCLUDE = "exclude";

    public void init(FilterConfig paramFilterConfig) throws ServletException {
        this.no_init = false;
        this.config = paramFilterConfig;
        if ((this.excluded = paramFilterConfig.getInitParameter(EXCLUDE)) != null)
            this.excluded += ",";
    }

    public void destroy() {
        this.config = null;
    }

    public FilterConfig getFilterConfig() {
        return this.config;
    }

    public void setFilterConfig(FilterConfig paramFilterConfig) {
        if (!(this.no_init))
            return;
        this.no_init = false;
        this.config = paramFilterConfig;
        if ((this.excluded = paramFilterConfig.getInitParameter(EXCLUDE)) != null)
            this.excluded += ",";
    }

    public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
            throws IOException, ServletException {

        String str1 = ((HttpServletRequest) paramServletRequest).getRequestURI();
        if (excluded(str1)) {
            paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
        } else {
            TrimResponseWrapper localtrimResponseWrapper = new TrimResponseWrapper((HttpServletResponse) paramServletResponse);
            paramFilterChain.doFilter(paramServletRequest, localtrimResponseWrapper);
        }
    }

    private boolean excluded(String paramString) {
        if ((paramString == null) || (this.excluded == null))
            return false;
        return (this.excluded.indexOf(paramString + ",") >= 0);
    }

}
