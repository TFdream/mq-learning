package mq.tutorials.rocketmq.util;

import org.xml.sax.InputSource;
import java.io.*;

/**
 * @author Ricky Fung
 * @version 1.0
 * @since 2018-07-04 23:13
 */
public class IoUtils {

    public static InputSource toInputSource(String str, String encoding){
        InputSource source = new InputSource(new StringReader(str));
        source.setEncoding(encoding);
        return source;
    }

    public static void closeQuietly(InputStream input){
        closeQuietly((Closeable)input);
    }

    public static void closeQuietly(OutputStream output){
        closeQuietly((Closeable)output);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeQuietly(Closeable... closeables){
        for (Closeable closeable : closeables){
            closeQuietly(closeable);
        }
    }

    public static long copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buff = new byte[bufferSize];
        return copy(in, out, buff);
    }

    public static long copy(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[1024];
        return copy(in, out, buff);
    }

    public static long copy(InputStream in, OutputStream out, byte[] buff) throws IOException {
        long count = 0;
        int len = -1;
        while((len=in.read(buff, 0, buff.length))!=-1){
            out.write(buff, 0, len);
            count += len;
        }
        return count;
    }
}
