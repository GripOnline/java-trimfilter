package nl.grip.trimfilter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class ServletTrimOutputStream extends ServletOutputStream {
    private final TrimOutputStream stream;

    public ServletTrimOutputStream(OutputStream paramOutputStream) {
        this.stream = new TrimOutputStream(new DataOutputStream(paramOutputStream));
    }

    @Override
    public void write(int paramInt) throws IOException {
        this.stream.write(paramInt);
    }

    @Override
    public void flush() throws IOException {
        stream.flush();
    }

}
