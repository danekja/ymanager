package cz.zcu.yamanager.ws.rest;

@FunctionalInterface
public interface RESTInvokeHandler<T> {
    void invoke() throws Exception;
}
