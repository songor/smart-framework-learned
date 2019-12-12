package org.smart4j.framework.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.demo.ControllerTimeIntervalAspect;
import org.smart4j.demo.CustomController;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class MockAopHelperTest {

    @Test
    public void shouldWeavingTimeIntervalToControllerSuccess() throws ClassNotFoundException {
        mockStatic(LoggerFactory.class);
        Logger LOGGER = mock(Logger.class);
        when(LoggerFactory.getLogger(ControllerTimeIntervalAspect.class)).thenReturn(LOGGER);

        Class.forName(AopHelper.class.getName());
        CustomController controller = BeanHelper.getBean(CustomController.class);
        controller.custom();

        verify(LOGGER, times(1)).info(anyString());
    }

}