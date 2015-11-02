package XsdParser.Iterator;


class IteratorContext {

	private final IObjectIterator iterator;
	private final int depth;
    private final String name;
    private final Object property;

	public IteratorContext(IObjectIterator iterator, int depth, String name, Object property) {
		this.iterator = iterator;
		this.depth = depth;
        this.name = name;
        this.property = property;
	}

	public IObjectIterator getIterator() {
		return iterator;
	}

	public int getDepth() {
		return depth;
	}


	public String getName() {
        return name;
    }

	public Object getProperty() {
        return property;
    }

}
