package eu.mcsistemi.Entity;

public class DailyOpeningPlan {
	
	private int id;
	private String name;
	private String open;
	private String close;
	
	public DailyOpeningPlan(int id, String name, String open, String close) {
		super();
		this.id = id;
		this.name = name;
		this.open = convertTiming(open);
		this.close = convertTiming(close);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = convertTiming(open);
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = convertTiming(close);
	}

	private String convertTiming(String time) {
		return "" + Integer.parseInt(time.substring(0, 2)) + ":" + time.substring(2);
	}
	
	public String toString() {
		return "\n      id: " + this.id + "\n" +
			   "      name: " + this.name + "\n" +
			   "      open: " + this.open + "\n" +
			   "      close: " + this.close + "\n";
	}
}
