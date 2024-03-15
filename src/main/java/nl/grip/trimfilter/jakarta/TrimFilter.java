package nl.grip.trimfilter.jakarta;

import java.io.IOException;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;

public class TrimFilter implements jakarta.servlet.Filter {

    private String              excluded;
    private static final String EXCLUDE = "exclude";

    public void init(FilterConfig filterConfig) throws ServletException {
        if ((this.excluded = filterConfig.getInitParameter(EXCLUDE)) != null)
            this.excluded += ",";
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, jakarta.servlet.FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

        String str1 = ((jakarta.servlet.http.HttpServletRequest) request).getRequestURI();
        if (excluded(str1)) {
            chain.doFilter(request, response);
        } else {
            nl.grip.trimfilter.jakarta.TrimResponseWrapper localtrimResponseWrapper = new nl.grip.trimfilter.jakarta.TrimResponseWrapper(
                    (jakarta.servlet.http.HttpServletResponse) response);
            chain.doFilter(request, localtrimResponseWrapper);
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
