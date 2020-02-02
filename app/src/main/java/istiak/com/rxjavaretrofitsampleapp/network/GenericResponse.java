package istiak.com.rxjavaretrofitsampleapp.network;

public class GenericResponse<T> extends ResponseWrapper {

    private T response;

    public GenericResponse() {
        super();
        response = null;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
