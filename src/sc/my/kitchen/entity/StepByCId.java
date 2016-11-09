package sc.my.kitchen.entity;

import java.io.Serializable;

public class StepByCId implements Serializable{

	private String img;
	private String step;
	
	public StepByCId() {
		super();
	}

	public StepByCId(String img, String step) {
		super();
		this.img = img;
		this.step = step;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
}
