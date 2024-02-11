package nl.grip.trimfilter.jakarta;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nl.grip.trimfilter.TrimOutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

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

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }

}
