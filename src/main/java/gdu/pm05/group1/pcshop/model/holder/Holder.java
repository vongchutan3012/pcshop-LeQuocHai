package gdu.pm05.group1.pcshop.model.holder;

public abstract class Holder<T> {
    // FIELDS:
    protected T value;

    // CONSTRUCTORS:
    public Holder() {

    }

    public Holder(T value) {
        this.value = value;
    }

    // METHODS:
    @Override
    public abstract String toString();

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
