package moller.util.version.containers;
/**
 * This class was created : 2009-05-14 by Fredrik Möller
 * Latest changed         : 2009-05-15 by Fredrik Möller
 */

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