import java.util.Arrays;

/**
 * Created by fred on 2018/6/7.
 */
public class AddrList {
    private String type;
    private String status;
    private String name;
    private String id;
    private String admCode;
    private String admName;
    private String addr;
    private double[] nearestPoint;
    private double distance;

    public AddrList() {
    }

    public AddrList(String type, String status, String name, String id, String admCode, String admName, String addr, double[] nearestPoint, double distance) {
        this.type = type;
        this.status = status;
        this.name = name;
        this.id = id;
        this.admCode = admCode;
        this.admName = admName;
        this.addr = addr;
        this.nearestPoint = nearestPoint;
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdmCode() {
        return admCode;
    }

    public void setAdmCode(String admCode) {
        this.admCode = admCode;
    }

    public String getAdmName() {
        return admName;
    }

    public void setAdmName(String admName) {
        this.admName = admName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double[] getNearestPoint() {
        return nearestPoint;
    }

    public void setNearestPoint(double[] nearestPoint) {
        this.nearestPoint = nearestPoint;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "AddrList{" +
                "type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", admCode='" + admCode + '\'' +
                ", admName='" + admName + '\'' +
                ", addr='" + addr + '\'' +
                ", nearestPoint=" + Arrays.toString(nearestPoint) +
                ", distance=" + distance +
                '}';
    }
}
