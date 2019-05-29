package ru.stepanenko.tm;



import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.util.DataGenerator;


/**
 * Unit test for simple AppServer.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class AppServerTest {
    /**
     * Rigorous Test :-)
     */

    @Autowired
    private IUserService userService;

    @Autowired
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
