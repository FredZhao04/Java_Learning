/**
 * Created by fred on 2018/6/4.
 */
public class HttpBean {
    private String phone;
    private String url;
    private long upStream;
    private long downStream;

    @Override
    public String toString() {
        return "HttpBean{" +
                "phone='" + phone + '\'' +
                ", url='" + url + '\'' +
                ", upStream=" + upStream +
                ", downStream=" + downStream +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getUpStream() {
        return upStream;
    }

    public void setUpStream(long upStream) {
        this.upStream = upStream;
    }

    public long getDownStream() {
        return downStream;
    }

    public void setDownStream(long downStream) {
        this.downStream = downStream;
    }

    public HttpBean(String phone, String url, long upStream, long downStream) {

        this.phone = phone;
        this.url = url;
        this.upStream = upStream;
        this.downStream = downStream;
    }

    public HttpBean() {

    }
}
