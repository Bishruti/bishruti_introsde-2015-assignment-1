package pojos;

public class HealthProfile {
	private String lastupdate;
	private double weight; // in kg
	private double height; // in m

	public HealthProfile(String lastupdate, double weight, double height) {
        this.lastupdate = lastupdate;
		this.weight = weight;
		this.height = height;
	}

	public HealthProfile() {
		this.lastupdate = "2014-05-08T21:16:51.000+2:00";
		this.weight = 85.5;
		this.height = 1.72;
	}

	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

    // add accessor for the newly created BMI
    public double getBMI() {
        return this.weight/(Math.pow(this.height, 2));
    }

	public String toString() {
		return "LastUpdate= "+lastupdate +"Height= "+height+", Weight= "+weight;
	}

}
