package cool.blink.back.core;

import cool.blink.back.cache.Etag;
import java.util.Arrays;

public class Response {

    private Integer code;
    /**
     * Generally contains the objects that build the response payload.
     *
     * @param httpExchange httpExchange
     * @param request request
     * @return response response
     */
    private String payload;
    private String contentType;
    private Etag etag;
    private byte[] bytes;

    public Response() {
        this.code = 0;
        this.payload = "";
        this.bytes = this.payload.getBytes();
    }

    public Response(Integer code, String payload) {
        this.code = code;
        this.payload = payload;
        this.bytes = this.payload.getBytes();
    }

    public Response(Integer code, String payload, String contentType, Etag etag) {
        this.code = code;
        this.payload = payload;
        this.contentType = contentType;
        this.etag = etag;
        this.bytes = this.payload.getBytes();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
        this.bytes = this.payload.getBytes();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Etag getEtag() {
        return etag;
    }

    public void setEtag(Etag etag) {
        this.etag = etag;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "Response{" + "code=" + code + ", payload=" + payload + ", contentType=" + contentType + ", etag=" + etag + ", bytes=" + Arrays.toString(bytes) + '}';
    }

}
