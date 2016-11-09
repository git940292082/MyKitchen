package sc.my.kitchen.entity;

public class RootByMenu {

	private String resultcode;

	private String reason;

	private ResultByMenu result;

	private int error_code;

	public void setResultcode(String resultcode){
	this.resultcode = resultcode;
	}
	public String getResultcode(){
	return this.resultcode;
	}
	public void setReason(String reason){
	this.reason = reason;
	}
	public String getReason(){
	return this.reason;
	}
	public void setResult(ResultByMenu result){
	this.result = result;
	}
	public ResultByMenu getResult(){
	return this.result;
	}
	public void setError_code(int error_code){
	this.error_code = error_code;
	}
	public int getError_code(){
	return this.error_code;
	}
	
}
