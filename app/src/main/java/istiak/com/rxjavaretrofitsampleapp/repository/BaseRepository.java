package istiak.com.rxjavaretrofitsampleapp.repository;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;

import istiak.com.rxjavaretrofitsampleapp.utils.ConnectionUtil;

public class BaseRepository {

    protected String auditNetworkFailure(Throwable t) {
        if (!ConnectionUtil.isNetworkAvailable())
            return ConnectionUtil.getNoConnectionMessage();
        if (t instanceof SocketException)
            return ConnectionUtil.getNoConnectionMessage();
        if (t instanceof SocketTimeoutException)
            return ConnectionUtil.getNoConnectionMessage();
        if (t instanceof SSLException)
            return ConnectionUtil.getNoConnectionMessage();
        if (t instanceof UnknownHostException)
            return "Time out";
        if (t instanceof SSLPeerUnverifiedException)
            return "SSL_PINNING_EXCEPTION";
        if (t instanceof IOException)  // Special case, when wifi proxy to point to charles proxy, but didn't allow the proxy request
            return "SSL_PINNING_EXCEPTION";
        return t.toString();
    }
}
