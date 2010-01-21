package moller.javapeg.program.categories;
/**
 * This class was created : 2010-01-21 by Fredrik Möller
 * Latest changed         
 */

import moller.util.io.Status;

public class ImageRepositoryItem {
	
	private String path;
	private Status pathStatus;
		
	public String getPath() {
		return path;
	}
	public Status getPathStatus() {
		return pathStatus;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setPathStatus(Status pathStatus) {
		this.pathStatus = pathStatus;
	}
	public void setPathStatus(String pathStatus) {
		if(pathStatus.equals("EXISTS")) {
			this.pathStatus = Status.EXISTS;
		} else if(pathStatus.equals("NOT_AVAILABLE")) {
			this.pathStatus = Status.NOT_AVAILABLE;
		} else if(pathStatus.equals("DOES_NOT_EXIST")) {
			this.pathStatus = Status.DOES_NOT_EXIST;
		} 	
	}
}
