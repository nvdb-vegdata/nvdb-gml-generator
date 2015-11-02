package XsdParser.Iterator;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class MapIterator implements IObjectIterator {

    private final String name;
    private Iterator<?> entryIterator;
    private Object currentEntry;
    private int nextIndex = -1;

    public MapIterator(String name, Map<?, ?> map) {
        this.name = name;
        this.entryIterator = map.entrySet().iterator();
    }

    @Override
    public boolean iterate() {
        if (entryIterator.hasNext()) {
            nextIndex++;
            currentEntry = ((Entry<?, ?>) entryIterator.next()).getValue();
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return name + "[" + nextIndex + "]";
        // return "entry";
    }

    @Override
    public Object getProperty() {
        return currentEntry;
    }

}
