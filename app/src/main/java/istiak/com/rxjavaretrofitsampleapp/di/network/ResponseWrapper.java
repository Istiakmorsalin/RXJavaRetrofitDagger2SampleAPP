package istiak.com.rxjavaretrofitsampleapp.di.network;

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
