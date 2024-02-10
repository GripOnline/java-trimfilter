package nl.grip.trimfilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TrimOutputStream {

    OutputStream outputStream;

    byte         tagStart        = '<';

    boolean      inTagName       = false;
    List<Byte>   currentTag      = null;
    Byte         lastWrittenByte = null;

    byte[]       copyUntil       = null;
    List<Byte>   copyUntilBuf    = null;

    byte[]       commentStart    = "<!".getBytes();
    byte[]       commentEnd      = ">".getBytes();
    byte[]       preStart        = "<pre".getBytes();
    byte[]       preEnd          = "</pre".getBytes();
    byte[]       textareaStart   = "<textarea".getBytes();
    byte[]       textareaEnd     = "</textarea".getBytes();
    byte[]       scriptStart     = "<script".getBytes();
    byte[]       scriptEnd       = "</script".getBytes();
    byte[]       styleStart      = "<style".getBytes();
    byte[]       styleEnd        = "</style".getBytes();

    public TrimOutputStream(OutputStream outputStream) {
        super();
        this.outputStream = outputStream;

    }

    public void write(int paramInt) throws IOException {

        if (paramInt == 60) { // <

            inTagName = true;
            this.currentTag = new ArrayList<Byte>(10);
            this.currentTag.add((byte) paramInt);

        } else if ((paramInt == 32) || (paramInt == 47) || (paramInt == 62)) {
            // " ", "/" or ">"

            inTagName = false;

        } else if (inTagName) {
            // add current byte to tag name
            if ((paramInt >= 65) && (paramInt <= 90)) {
                // convert to lowercase
                currentTag.add((byte) (paramInt + 32));
            } else {
                currentTag.add((byte) paramInt);
            }
        }

        if (this.isCurrentTag(this.commentStart)) {
            // it's a comment tag, copy everything until end of tag ('>')

            this.copyUntil = this.commentEnd;
            this.copyUntilBuf = new ArrayList<Byte>();

        } else if (this.isCurrentTag(this.preStart)) {

            // copy until </pre>
            this.copyUntil = this.preEnd;
            this.copyUntilBuf = new ArrayList<Byte>();

        } else if (this.isCurrentTag(this.textareaStart)) {

            // copy until </textarea>
            this.copyUntil = this.textareaEnd;
            this.copyUntilBuf = new ArrayList<Byte>();

        } else if (this.isCurrentTag(this.scriptStart)) {

            // copy until </script>
            this.copyUntil = this.scriptEnd;
            this.copyUntilBuf = new ArrayList<Byte>();

        } else if (this.isCurrentTag(this.styleStart)) {

            // copy until </style>
            this.copyUntil = this.styleEnd;
            this.copyUntilBuf = new ArrayList<Byte>();

        }

        if (this.copyUntil != null) {

            // add current byte to matching pattern if needed
            int testByte = paramInt;
            if ((testByte >= 65) && (testByte <= 90)) {
                // convert to lowercase
                testByte = testByte + 32;
            }
            if (testByte == this.copyUntil[this.copyUntilBuf.size()]) {
                this.copyUntilBuf.add((byte) testByte);
            } else if (this.copyUntilBuf.size() > 0) {
                // reset matching pattern
                this.copyUntilBuf = new ArrayList<Byte>();
            }

            // copy current byte
            this.outputStream.write(paramInt);

            if (this.copyUntilBuf.size() == this.copyUntil.length) {

                // all bytes matched, done copying
                this.copyUntil = null;
            }

        } else if (inTagName == false) {

            // trimming mode ON
            if ((lastWrittenByte != null) && ((paramInt == ' ') || (paramInt == '\n')) && ((lastWrittenByte == ' ') || (lastWrittenByte == '\n'))) {

                // do not add second space/newline (not after newline or space)

            } else if ((paramInt == '\t') || (paramInt == '\r')) {
                // do not add it

            } else {

                // add it
                this.outputStream.write(paramInt);
                this.lastWrittenByte = (byte) paramInt;

            }

        } else {

            // no trimming yet
            this.outputStream.write(paramInt);
            this.lastWrittenByte = (byte) paramInt;

        }

    }

    public boolean isCurrentTag(byte[] testTag) {
        if ((this.currentTag != null) && (this.currentTag.size() == testTag.length)) {

            for (int i = 0; i < testTag.length; i++) {
                if (this.currentTag.get(i) != testTag[i]) {
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }
    }

    public void flush() throws IOException {
        this.outputStream.flush();
    }

}
