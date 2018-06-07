/**
 * Created by fred on 2018/6/7.
 */
public class LocalBean {
    private String page;
    private String event_type;
    private String time;
    private String uid;
    private String longitude;
    private String latitude;
    private String province;
    private String city;
    private String district;

    public LocalBean() {
    }

    public LocalBean(String page, String event_type, String time, String uid, String longitude, String latitude, String province, String city, String district) {
        this.page = page;
        this.event_type = event_type;
        this.time = time;
        this.uid = uid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "LocalBean{" +
                "page='" + page + '\'' +
                ", event_type='" + event_type + '\'' +
                ", time='" + time + '\'' +
                ", uid='" + uid + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
