package nl.grip.trimfilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class TrimOutputStreamTest extends TestCase {

    public void testTrimmedOutput() throws IOException {

        String input = "<!DOCTYPE HTML>\n<html lang=\"nl\">\n  \n\t\t\n<body>some  text</body></html>";

        byte[] output = this.getTrimmedOutput(this.getBytes(input));

        // System.out.println(this.getString(output));

        assertEquals("<!DOCTYPE HTML>\n<html lang=\"nl\">\n<body>some text</body></html>", this.getString(output));

        // leave text inside <pre> alone
        input = "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><pre>\n  \n\t\t\n</pre>some  text</body></html>";
        output = this.getTrimmedOutput(this.getBytes(input));
        assertEquals("<!DOCTYPE HTML>\n<html lang=\"nl\"><body><pre>\n  \n\t\t\n</pre>some text</body></html>", this.getString(output));

        // leave text inside <PRE> alone
        input = "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><PRE>\n  \n\t\t\n</PRE>some  text</body></html>";
        output = this.getTrimmedOutput(this.getBytes(input));
        assertEquals("<!DOCTYPE HTML>\n<html lang=\"nl\"><body><PRE>\n  \n\t\t\n</PRE>some text</body></html>", this.getString(output));

        // leave text inside comments alone
        input = "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><!--\n  \n\t\t\ncomment here-->some  text</body></html>";
        output = this.getTrimmedOutput(this.getBytes(input));
        assertEquals("<!DOCTYPE HTML>\n<html lang=\"nl\"><body><!--\n  \n\t\t\ncomment here-->some text</body></html>", this.getString(output));

        // leave text inside <textarea> alone
        input = "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><textareA>\n  \n\t\t\ntextarea\n\n</textArea>some  text</body></html>";
        output = this.getTrimmedOutput(this.getBytes(input));
        assertEquals("<!DOCTYPE HTML>\n<html lang=\"nl\"><body><textareA>\n  \n\t\t\ntextarea\n\n</textArea>some text</body></html>", this.getString(output));

        // leave text inside <script> alone
        input = "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><scripT language=\"text/javascript\">\n  \n\t\t\nsome script('<');\n\n</sCript>some  text</body></html>";
        output = this.getTrimmedOutput(this.getBytes(input));
        assertEquals(
                "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><scripT language=\"text/javascript\">\n  \n\t\t\nsome script('<');\n\n</sCript>some text</body></html>",
                this.getString(output));

        // leave text inside <style> alone
        input = "<!DOCTYPE HTML>\n<html lang=\"nl\"><body><Style type=\"text/css\">\n  \n\t\t\n#body  {'<'}\n\n</stYle>some  text</body></html>";
        output = this.getTrimmedOutput(this.getBytes(input));
        assertEquals("<!DOCTYPE HTML>\n<html lang=\"nl\"><body><Style type=\"text/css\">\n  \n\t\t\n#body  {'<'}\n\n</stYle>some text</body></html>",
                this.getString(output));

    }

    public byte[] getTrimmedOutput(byte[] input) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        TrimOutputStream trimOutputStream = new TrimOutputStream(byteArrayOutputStream);

        for (int i = 0; i < input.length; i++) {
            trimOutputStream.write(input[i]);
        }

        return byteArrayOutputStream.toByteArray();

    }

    public byte[] getBytes(String input) throws UnsupportedEncodingException {
        return input.getBytes("UTF-8");
    }

    public String getString(byte[] input) throws UnsupportedEncodingException {
        return new String(input, "UTF-8");
    }
}
