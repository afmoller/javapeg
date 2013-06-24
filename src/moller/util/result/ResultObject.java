package moller.util.result;

public class ResultObject {

    private Boolean result;
    private Object object;

    public ResultObject(Boolean result, Object object) {
        super();
        this.result = result;
        this.object = object;
    }

    public Boolean getResult() {
        return result;
    }
    public Object getObject() {
        return object;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
    public void setObject(Object object) {
        this.object = object;
    }
}
