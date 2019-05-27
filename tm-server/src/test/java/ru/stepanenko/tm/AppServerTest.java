package ru.stepanenko.tm;


import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.stepanenko.tm.api.service.IPropertyService;
import javax.inject.Inject;

/**
 * Unit test for simple AppServer.
 */

@RunWith(CdiTestRunner.class)
public class AppServerTest {
    /**
     * Rigorous Test :-)
     */

    @Inject
    private IPropertyService propertyService;

    @Test
    public void shouldAnswerWithTrue() {
        System.out.println(propertyService.getJdbcURL());
    }
}
