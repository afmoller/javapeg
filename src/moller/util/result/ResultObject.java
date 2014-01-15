package moller.util.result;

public class ResultObject<T extends Object> {

    private Boolean result;
    private T object;

    public ResultObject(Boolean result, T object) {
        super();
        this.result = result;
        this.object = object;
    }

    public Boolean getResult() {
        return result;
    }
    public T getObject() {
        return object;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
    public void setObject(T object) {
        this.object = object;
    }
}
