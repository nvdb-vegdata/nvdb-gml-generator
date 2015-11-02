package XsdParser.Iterator;



class RootIterator implements IObjectIterator {
	
	public enum Status {NEW, OPENED, CLOSED}

	private final Object object;
	private Status status = Status.NEW;
	private final String name;

	public RootIterator(String name, Object object) {
		this.name = name;
		this.object = object;
	}
	
	@Override
	public boolean iterate() {
		if (status == Status.NEW) {
			status = Status.OPENED; 
			return true;
		} else if (status == Status.OPENED) {
			status = Status.CLOSED; 
			return false;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Object getProperty() {
		if (status == Status.OPENED) {
			return object;
		}
		return null;
	}
	
}
