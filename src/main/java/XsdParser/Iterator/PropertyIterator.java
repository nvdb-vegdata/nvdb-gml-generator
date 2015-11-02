package XsdParser.Iterator;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

class PropertyIterator implements IObjectIterator {

    private final Object object;
    private final PropertyDescriptor[] properties;
    private int nextIndex = -1;
    private PropertyDescriptor currentProperty;

    public PropertyIterator(Object object) {
        this.object = object;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            properties = beanInfo.getPropertyDescriptors();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean iterate() {
        if (nextIndex + 1 >= properties.length) {
            return false;
        }

        nextIndex++;
        currentProperty = properties[nextIndex];
        if (currentProperty.getReadMethod() == null || "class".equals(currentProperty.getName())) {
            return iterate();
        }
        return true;
    }

    @Override
    public String getName() {
        if (currentProperty == null) {
            return null;
        }
        return currentProperty.getName();
    }

    @Override
    public Object getProperty() {
        try {
            if (currentProperty == null) {
                return null;
            }
            return currentProperty.getReadMethod().invoke(object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
