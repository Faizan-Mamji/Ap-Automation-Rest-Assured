package ExecutionPackage;

import ApiPackage.JiraImplementation;
import org.testng.annotations.Test;

public class JiraExecution extends JiraImplementation {
    @Test(priority = 1)
    public void loginAuthentication() {
        jiraLoginAuthentication();
    }

    @Test(priority = 2)
    public void e2eTest() {
        executeEndToEnd();
    }
}
