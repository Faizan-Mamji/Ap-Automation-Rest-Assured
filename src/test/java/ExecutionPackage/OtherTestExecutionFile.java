package ExecutionPackage;

import ApiPackage.TestingCrud;
import org.testng.annotations.Test;

public class OtherTestExecutionFile extends TestingCrud {

    @Test(priority = 1, enabled = false)
    public void A() {
        testPostApi();
    }

    @Test(priority = 2, enabled = false)
    public void B() {
        testResponses();
    }

    @Test(priority = 3, dataProvider = "BooksData")
    public void C(String isbn, String aisle) {
        AddDeleteBook(isbn, aisle);
    }

}
