package nl.grip.trimfilter;

import java.io.IOException;

public class TrimFilter implements javax.servlet.Filter {

    private boolean             no_init = true;
    private String              excluded;
    private static final String EXCLUDE = "exclude";

    public void init(javax.servlet.FilterConfig paramFilterConfig) throws javax.servlet.ServletException {
        this.no_init = false;
        if ((this.excluded = paramFilterConfig.getInitParameter(EXCLUDE)) != null)
            this.excluded += ",";
    }

    public void setFilterConfig(javax.servlet.FilterConfig paramFilterConfig) {
        if (!(this.no_init))
            return;
        this.no_init = false;
        if ((this.excluded = paramFilterConfig.getInitParameter(EXCLUDE)) != null)
            this.excluded += ",";
    }

    @Override
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response, javax.servlet.FilterChain paramFilterChain)
            throws IOException, javax.servlet.ServletException {

        String str1 = ((javax.servlet.http.HttpServletRequest) request).getRequestURI();
        if (excluded(str1)) {
            paramFilterChain.doFilter(request, response);
        } else {
            nl.grip.trimfilter.jvx.TrimResponseWrapper localtrimResponseWrapper = new nl.grip.trimfilter.jvx.TrimResponseWrapper(
                    (javax.servlet.http.HttpServletResponse) response);
            paramFilterChain.doFilter(request, localtrimResponseWrapper);
        }
    }

    private boolean excluded(String paramString) {
        if ((paramString == null) || (this.excluded == null))
            return false;
        return (this.excluded.indexOf(paramString + ",") >= 0);
    }

    @Override
    public void destroy() {
    }

}
