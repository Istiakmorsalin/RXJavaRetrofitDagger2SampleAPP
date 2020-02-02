package istiak.com.rxjavaretrofitsampleapp.network;

public class ResponseWrapper {
    protected String errorMessage;

    public ResponseWrapper() {
        errorMessage = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
