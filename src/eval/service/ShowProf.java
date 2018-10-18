package eval.service;

public class ShowProf {
	private String proId;
	private String proName;
	
	public ShowProf(){
		proId = null;
		proName = null;
	}
	
	public ShowProf(String tn, String tnn){
		proId = tn;
		proName = tnn;
	}
	
	public String getProId() {
		return proId;
	}
	public String getProName() {
		return proName;
	}
	
	public void setProId(String proId) {
		this.proId = proId;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
}
