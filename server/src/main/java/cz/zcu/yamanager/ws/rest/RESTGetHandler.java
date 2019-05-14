package cz.zcu.yamanager.ws.rest;

import java.lang.FunctionalInterface;

@FunctionalInterface
public interface RESTGetHandler<T> {
    T get() throws Exception;
}
