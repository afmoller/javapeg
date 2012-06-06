package moller.util.version.containers;

public class ChangeLog {

	private String changeLog;

	public ChangeLog() {
		super();
		this.changeLog = "";
	}

	public ChangeLog(long timeStamp, String changeLog) {
		super();
		this.changeLog = changeLog;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
}
