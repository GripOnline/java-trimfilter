package nl.grip.trimfilter.jvx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nl.grip.trimfilter.TrimOutputStream;

public class ServletTrimOutputStream extends javax.servlet.ServletOutputStream {

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

    public boolean isReady() {
        return false;
    }

    public void setWriteListener(javax.servlet.WriteListener writeListener) {
    }

}
