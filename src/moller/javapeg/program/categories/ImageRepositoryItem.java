package moller.javapeg.program.categories;
/**
 * This class was created : 2010-01-21 by Fredrik Möller
 * Latest changed         : 2010-01-28 by Fredrik Möller
 *                        : 2010-02-06 by Fredrik Möller
 */

import moller.util.io.Status;

public class ImageRepositoryItem implements Comparable<ImageRepositoryItem>{
	
	private String path;
	private Status pathStatus;
	
	public ImageRepositoryItem() {
		this.path = null;
		this.pathStatus = null;
	}
		
	public ImageRepositoryItem(String path, Status pathStatus) {
		super();
		this.path = path;
		this.pathStatus = pathStatus;
	}
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
	
	@Override
	public int compareTo(ImageRepositoryItem iri) {
		return this.path.compareTo(iri.getPath());
	}
}