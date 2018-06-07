import java.util.Arrays;

/**
 * Created by fred on 2018/6/7.
 */
public class LocationBean {
    private double[] queryLocation;
    private AddrList[] addrList;

    public LocationBean() {
    }

    public LocationBean(double[] queryLocation, AddrList[] addrList) {
        this.queryLocation = queryLocation;
        this.addrList = addrList;
    }

    public double[] getQueryLocation() {
        return queryLocation;
    }

    public void setQueryLocation(double[] queryLocation) {
        this.queryLocation = queryLocation;
    }

    public AddrList[] getAddrList() {
        return addrList;
    }

    public void setAddrList(AddrList[] addrList) {
        this.addrList = addrList;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "queryLocation=" + Arrays.toString(queryLocation) +
                ", addrList=" + Arrays.toString(addrList) +
                '}';
    }
}
