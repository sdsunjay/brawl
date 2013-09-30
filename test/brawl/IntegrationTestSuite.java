package brawl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Suite of all currently passing integration tests.
 * @author Brian Yabuki (byabuki)
 */
public class IntegrationTestSuite extends TestCase {

    public IntegrationTestSuite(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite("SuiteIntegrationTest");
        
        suite.addTestSuite(ComputerIntTest.class);
        suite.addTestSuite(GameControllerIntTest.class);
        suite.addTestSuite(GameModelIntTest.class);

        return suite;
    }
}