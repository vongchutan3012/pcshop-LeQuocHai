package gdu.pm05.group1.pcshop.model.dbhandler;

public class HQLParameter {
    // FIELDS:
    private String name;
    private Object value;

    // COSNTRUCTORS:
    public HQLParameter() {
    }
    public HQLParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    // METHODS:
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
