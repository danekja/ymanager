package org.danekja.ymanager.ws.rest;

@FunctionalInterface
public interface RESTInvokeHandler<T> {
    void invoke() throws Exception;
}
