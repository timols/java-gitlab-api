package org.gitlab.api;

import java.io.IOException;

/**
 * Gitlab API Exception
 */
public class GitlabAPIException extends IOException {

    private int responseCode;

    public GitlabAPIException(String message, Integer responseCode, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
