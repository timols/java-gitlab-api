package org.gitlab.api.http;

/**
 * @author Shaburov Oleg
 * Created by Oleg Shaburov on 03.05.2018
 * shaburov.o.a@gmail.com
 */
public enum Method {
    /**
     * HTTP GET method
     */
    GET,
    /**
     * HTTP PUT method
     */
    PUT,
    /**
     * HTTP POST method
     */
    POST,
    /**
     * HTTP PATCH method
     */
    PATCH,
    /**
     * HTTP DELETE method
     */
    DELETE,
    /**
     * HTTP HEAD method
     */
    HEAD,
    /**
     * HTTP OPTIONS
     */
    OPTIONS,
    /**
     * HTTP TRACE
     */
    TRACE;
}
