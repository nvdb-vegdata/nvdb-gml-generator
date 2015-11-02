package XsdParser.Iterator;

import XsdParser.Parser.IteratorListener;

import java.util.*;

public class ObjectIterator implements IObjectIterator {

    private IObjectIterator iterator;
    private int depth;
    private Stack<IteratorContext> iteratorStack = new Stack<IteratorContext>();
    private String name;
    private Object property;
    private IteratorListener listener;
    private Set<Object> visitedProperties = new HashSet<Object>();


    public ObjectIterator(String name, IteratorListener listener, Object object) {
        this.iterator = new RootIterator(name, object);
        this.listener = listener;
    }

    public boolean iterate() {

        if (property != null) {
            if (!isSingleValued(property) && !visitedProperties.contains(property)) {
                visitedProperties.add(property);
                IObjectIterator childIterator = createIteratorFor(property);
                enterChildProperty(childIterator);
                return iterate();
            }
        }

        if(iterateCurrentIterator()){
            return iterate();
        }

        if (hasParentProperty()) {
            exitToParentProperty();
            return iterate();
        }

        return false;
    }
    private boolean hasParentProperty(){
        return iteratorStack.size() > 0;
    }

    private boolean iterateCurrentIterator(){
        if (iterator.iterate()) {
            name = iterator.getName();
            property = iterator.getProperty();
            return true;
        }
        return false;
    }

    private void enterChildProperty(IObjectIterator childIterator) {
        listener.entered(property);
        iteratorStack.push(new IteratorContext(iterator, depth, name, property));
        iterator = childIterator;
        depth++;
        name = null;
        property = null;
    }


    private void exitToParentProperty() {
        IteratorContext context = iteratorStack.pop();
        iterator = context.getIterator();
        listener.exited(iterator.getProperty());
        depth--;
        name = null;
        property = null;
    }



    public String getName() {
        return name;
    }

    public Object getProperty() {
        return property;
    }

    private IObjectIterator createIteratorFor(Object property) {
        try {

            if (property.getClass().isArray()) {
                return new ArrayIterator(name, property);
            }

            if (property instanceof Iterable) {
                return new CollectionIterator(name, (Iterable<?>) property);
            }

            if (property instanceof Map) {
                return new MapIterator(name, (Map<?, ?>) property);
            }

            return new PropertyIterator(property);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    private boolean isSingleValued(Object property) {
        if (property == null) {
            return true;
        }

        final Class<? extends Object> type = property.getClass();

        if (type.isPrimitive() || type.isEnum()) {
            return true;
        }

        if (property instanceof Number) {
            return true;
        }

        if (property instanceof CharSequence) {
            return true;
        }

        if (property instanceof Date) {
            return true;
        }

        if (property instanceof Boolean) {
            return true;
        }

        if (property instanceof Character) {
            return true;
        }

        if (property instanceof Void) {
            return true;
        }
        if (property instanceof Map.Entry) {
            return true;
        }

        return false;
    }
}
