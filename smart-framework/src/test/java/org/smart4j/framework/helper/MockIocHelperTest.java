package org.smart4j.framework.helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.smart4j.demo.InvalidCustomController;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanHelper.class)
public class MockIocHelperTest {

    @Before
    public void init() {
        PowerMockito.mockStatic(BeanHelper.class);
        Map<Class<?>, Object> mockMap = new HashMap<>();
        mockMap.put(InvalidCustomController.class, new InvalidCustomController());
        PowerMockito.when(BeanHelper.getBeanMap()).thenReturn(mockMap);
    }

    /**
     * The ExceptionInInitializerError is used as a wrapper to indicate that an exception arises in the static initializer block or the evaluation of a static variable’s value.
     * Thus, we have to ensure that the original exception is fixed, in order for the JVM to be able to load our class successfully.
     */
    @Test
    public void shouldThrowExceptionWhenInjectNotExistBean() throws ClassNotFoundException {
        try {
            Class.forName(IocHelper.class.getName());
        } catch (ExceptionInInitializerError e) {
            assertThat(e.getCause()).isInstanceOf(RuntimeException.class).hasMessage("Bean instance does not exist");
        }
    }

}