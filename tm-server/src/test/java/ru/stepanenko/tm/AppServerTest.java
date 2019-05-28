package ru.stepanenko.tm;


import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.util.DataGenerator;

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
    private IUserService userService;

    @Inject
    private DataGenerator dataGenerator;

    @Before
    public void setUp() throws Exception {
        dataGenerator.generate();
        System.out.println("test begin");
    }


    @After
    public void tearDown() throws Exception {
        System.out.println("test end");
        dataGenerator.cleanUp();
    }

    @Test
    public void shouldAnswerWithTrue() throws DataValidateException {
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("test", userService.findOne("1").getLogin());
        }
    }
}
