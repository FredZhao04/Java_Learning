package ip;

public class IpBean {
	private String startIp;
	private String endIp;
	private long startDecimalIp;
	private long endDecimalIp;
	private String province;
	private String vendor;
	public IpBean() {
		super();
	}
	public IpBean(String startIp, String endIp, long startDecimalIp, long endDecimalIp, String province,
			String vendor) {
		super();
		this.startIp = startIp;
		this.endIp = endIp;
		this.startDecimalIp = startDecimalIp;
		this.endDecimalIp = endDecimalIp;
		this.province = province;
		this.vendor = vendor;
	}
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	public long getStartDecimalIp() {
		return startDecimalIp;
	}
	public void setStartDecimalIp(long startDecimalIp) {
		this.startDecimalIp = startDecimalIp;
	}
	public long getEndDecimalIp() {
		return endDecimalIp;
	}
	public void setEndDecimalIp(long endDecimalIp) {
		this.endDecimalIp = endDecimalIp;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	@Override
	public String toString() {
		return "IpBean [startIp=" + startIp + ", endIp=" + endIp + ", startDecimalIp=" + startDecimalIp
				+ ", endDecimalIp=" + endDecimalIp + ", province=" + province + ", vendor=" + vendor + "]";
	}
	
	
}
