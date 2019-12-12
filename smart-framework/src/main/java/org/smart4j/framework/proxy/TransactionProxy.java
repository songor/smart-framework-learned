package org.smart4j.framework.proxy;

import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

public class TransactionProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Method method = proxyChain.getTargetMethod();
        if (method.isAnnotationPresent(Transaction.class)) {
            try {
                DatabaseHelper.beginTransaction();
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                throw new RuntimeException(e);
            } finally {
                DatabaseHelper.closeConnection();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }

}
