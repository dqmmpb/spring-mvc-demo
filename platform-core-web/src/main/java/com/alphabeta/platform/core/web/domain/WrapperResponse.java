package com.alphabeta.platform.core.web.domain;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class WrapperResponse extends HttpServletResponseWrapper {

    private class ResponsePrintWriter extends PrintWriter {
        ByteArrayOutputStream output;

        public ResponsePrintWriter(ByteArrayOutputStream output) {
            super(output);
            this.output = output;
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return output;
        }
    }

    private ResponsePrintWriter writer;
    private ByteArrayOutputStream output;

    public WrapperResponse(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        output = new ByteArrayOutputStream();
        writer = new ResponsePrintWriter(output);
    }

    public void finalize() throws Throwable {
        super.finalize();
        output.close();
        writer.close();
    }

    public String getContent() {
        try {
            writer.flush();
            return writer.getByteArrayOutputStream().toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "UnsupportedEncoding";
        }
    }

    public void close() throws IOException {
        writer.close();
    }

    public PrintWriter getWriter() throws IOException {
        return writer;
    }

}
