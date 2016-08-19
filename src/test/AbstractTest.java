package test;

// Spring resource imports
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

// jUnit imports
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/test-context.xml" })
@WebAppConfiguration
public abstract class AbstractTest {}
