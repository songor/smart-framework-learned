package org.smart4j.framework.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.smart4j.demo.UnacceptableCustomController;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassHelper.class)
public class MockIncorrectActionTypeControllerHelperTest {

    /**
     * The ExceptionInInitializerError is used as a wrapper to indicate that an exception arises in the static initializer block or the evaluation of a static variable’s value.
     * Thus, we have to ensure that the original exception is fixed, in order for the JVM to be able to load our class successfully.
     */
    @Test
    public void shouldThrowExceptionWhenActionTypeIncorrect() throws ClassNotFoundException {
        PowerMockito.mockStatic(ClassHelper.class);
        Set<Class<?>> mockSet = new HashSet<>();
        mockSet.add(UnacceptableCustomController.class);
        PowerMockito.when(ClassHelper.getControllerClassSet()).thenReturn(mockSet);

        try {
            Class.forName(ControllerHelper.class.getName());
        } catch (ExceptionInInitializerError e) {
            assertThat(e.getCause()).isInstanceOf(RuntimeException.class).hasMessage("Incorrect @Action method define");
        }
    }

}