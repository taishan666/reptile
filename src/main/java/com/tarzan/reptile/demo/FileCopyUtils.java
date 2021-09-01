package com.tarzan.reptile.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

public abstract class FileCopyUtils {
    public static final int BUFFER_SIZE = 4096;

    public FileCopyUtils() {
    }

    public static int copy(File in, File out) throws IOException {
        Assert.notNull(in, "No input File specified");
        Assert.notNull(out, "No output File specified");
        return copy(Files.newInputStream(in.toPath()), Files.newOutputStream(out.toPath()));
    }

    public static void copy(byte[] in, File out) throws IOException {
        Assert.notNull(in, "No input byte array specified");
        Assert.notNull(out, "No output File specified");
        copy((InputStream)(new ByteArrayInputStream(in)), (OutputStream)Files.newOutputStream(out.toPath()));
    }

    public static byte[] copyToByteArray(File in) throws IOException {
        Assert.notNull(in, "No input File specified");
        return copyToByteArray(Files.newInputStream(in.toPath()));
    }

    public static int copy(InputStream in, OutputStream out) throws IOException {
        Assert.notNull(in, "No InputStream specified");
        Assert.notNull(out, "No OutputStream specified");

        int var2;
        try {
            var2 = StreamUtils.copy(in, out);
        } finally {
            try {
                in.close();
            } catch (IOException var12) {
            }

            try {
                out.close();
            } catch (IOException var11) {
            }

        }

        return var2;
    }

    public static void copy(byte[] in, OutputStream out) throws IOException {
        Assert.notNull(in, "No input byte array specified");
        Assert.notNull(out, "No OutputStream specified");

        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException var8) {
            }

        }

    }

    public static byte[] copyToByteArray(@Nullable InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            copy((InputStream)in, (OutputStream)out);
            return out.toByteArray();
        }
    }

    public static int copy(Reader in, Writer out) throws IOException {
        Assert.notNull(in, "No Reader specified");
        Assert.notNull(out, "No Writer specified");

        try {
            int byteCount = 0;
            char[] buffer = new char[4096];

            int bytesRead;
            for(boolean var4 = true; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
            int var5 = byteCount;
            return var5;
        } finally {
            try {
                in.close();
            } catch (IOException var15) {
            }

            try {
                out.close();
            } catch (IOException var14) {
            }

        }
    }

    public static void copy(String in, Writer out) throws IOException {
        Assert.notNull(in, "No input String specified");
        Assert.notNull(out, "No Writer specified");

        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException var8) {
            }

        }

    }

    public static String copyToString(@Nullable Reader in) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringWriter out = new StringWriter();
            copy((Reader)in, (Writer)out);
            return out.toString();
        }
    }
}
