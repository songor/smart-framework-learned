package org.smart4j.spring.aspectj;

import org.smart4j.spring.Apology;

public class ApologyImpl implements Apology {

    @Override
    public void saySorry(String name) {
        System.out.println("Sorry " + name);
    }

}
