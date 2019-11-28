package istiak.com.rxjavaretrofitsampleapp.di.network;

public class VoidResponseWrapper extends ResponseWrapper {
    boolean success;

    public VoidResponseWrapper() {
        super();
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

