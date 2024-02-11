package nl.grip.trimfilter.jvx;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class TrimResponseWrapper extends HttpServletResponseWrapper {
    private PrintWriter               writer              = null;
    private ServletOutputStream       servletOutputStream = null;
    private final HttpServletResponse origResponse;
    private int                       contentLength;
    private String                    contentType;

    public TrimResponseWrapper(HttpServletResponse paramHttpServletResponse) {
        super(paramHttpServletResponse);
        this.origResponse = paramHttpServletResponse;
    }

    @Override
    public ServletOutputStream getOutputStream() {

        if (this.servletOutputStream == null) {
            try {
                if ((this.contentType == null) || this.contentType.startsWith("text2/")) {
                    this.servletOutputStream = new ServletTrimOutputStream(this.origResponse.getOutputStream());
                } else {
                    this.servletOutputStream = super.getOutputStream();
                }
            } catch (IOException e) {
            }
        }

        return this.servletOutputStream;
    }

    public ServletOutputStream createOutputStream() throws IOException {
        return new ServletTrimOutputStream(this.origResponse.getOutputStream());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            this.writer = new PrintWriter(
                    new OutputStreamWriter(new ServletTrimOutputStream(this.origResponse.getOutputStream()), this.origResponse.getCharacterEncoding()));
        }
        return this.writer;
    }

    public void finishResponse() {
        if (this.writer != null) {
            this.writer.flush();
            this.writer.close();
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        if (this.writer != null) {
            this.writer.flush();
        } else {
            this.origResponse.flushBuffer();
        }

        if (this.servletOutputStream != null) {
            this.servletOutputStream.flush();
        }
    }

    @Override
    public void setContentLength(int paramInt) {
        this.contentLength = paramInt;
        super.setContentLength(paramInt);
    }

    public int getContentLength() {
        return this.contentLength;
    }

    @Override
    public void setContentType(String paramString) {
        this.contentType = paramString;
        super.setContentType(paramString);
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }
}
