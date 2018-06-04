/**
 * Created by fred on 2018/6/4.
 */
public class PhoneBean {
    private String phone;
    private String province;
    private String city;
    private String vendor;

    @Override
    public String toString() {
        return "PhoneBean{" +
                "phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public PhoneBean(String phone, String province, String city, String vendor) {

        this.phone = phone;
        this.province = province;
        this.city = city;
        this.vendor = vendor;
    }

    public PhoneBean() {

    }
}
