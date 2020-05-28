package ApiPackage;

import Payloads.AddPlacePayload;
import ReuseablePackage.ReuseMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class TestingCrud {
    int sum = 0;
    String getPlaceID, getAddress, cSize, purchaseAmount,
            courseOneTitle, courseTitle, coursePrice, copiesSold,
            key = "qaclick123", address = "faizan mamji";
    String response;
    JsonPath js;

    public void testPostApi() {
        ReuseMethods.baseUrlAddDelete();
        response = given().log().all().queryParam("key", key)
                .header("Content-Type", "application/json")
                .body(AddPlacePayload.AddPlace())
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();
        //System.out.println(response);
        js = ReuseMethods.convertToJson(response);
        getPlaceID = js.getString("place_id");
        System.out.println("The Place ID is " + getPlaceID);

        //update place id
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(AddPlacePayload.UpdatePlace(getPlaceID, key, address))
                .when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"))
                .header("Server", equalTo("Apache/2.4.18 (Ubuntu)"));

        //get request

        response = given().log().all().queryParam("key", key)
                .queryParam("place_id", getPlaceID)
                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        js = ReuseMethods.convertToJson(response);
        getAddress = js.getString("address");
        System.out.println("The value of address is: " + getAddress);
        Assert.assertEquals(getAddress, address);
    }

    public void testResponses() {
        js = ReuseMethods.convertToJson(AddPlacePayload.coursesPayload());
        cSize = js.getString("courses.size");
        System.out.println("The total courses are " + cSize);
        purchaseAmount = js.getString("dashboard.purchaseAmount");
        System.out.println("The purchaseAmount is " + purchaseAmount);
        courseOneTitle = js.getString("courses[0].title");
        System.out.println("The course title is " + courseOneTitle);
        for (int k = 0; k < Integer.parseInt(cSize); k++) {
            courseTitle = js.getString("courses[" + k + "].title");
            coursePrice = js.getString("courses[" + k + "].price");
            System.out.println("The courseTitle & Price of " + k + " item is " + courseTitle + " & " + coursePrice);
        }
        //Print no of copies sold by RPA Course
        for (int k = 0; k < Integer.parseInt(cSize); k++) {
            courseTitle = js.getString("courses[" + k + "].title");
            if (courseTitle.contains("RPA")) {
                copiesSold = js.getString("courses[" + k + "].copies");
                System.out.println("The total copies are " + copiesSold);
                break;
            }
        }

        //Verify if Sum of all Course prices matches with Purchase Amount
        for (int k = 0; k < Integer.parseInt(cSize); k++) {
            coursePrice = js.getString("courses[" + k + "].price");
            copiesSold = js.getString("courses[" + k + "].copies");
            int multiply = Integer.parseInt(coursePrice) * Integer.parseInt(copiesSold);
            sum = sum + multiply;
        }
        System.out.println("The total Value is " + sum);
        Assert.assertEquals(sum, Integer.parseInt(purchaseAmount));
    }

    @DataProvider(name = "BooksData")
    public Object[][] dataBooks() {
        return new Object[][]{{"a", "12113"}, {"b", "21342"}, {"c", "22342"}};
    }

    public static String GenerateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
        //GenerateStringFromResource("//Users//faizan//faiz.json")
    }

    public void AddDeleteBook(String isbn, String aisle) {
        try {
            ReuseMethods.baseUrlAddBook();
            String getResponse = given().log().all().body(AddPlacePayload.AddBookPayload(isbn, aisle))
                    .when().post("Library/Addbook.php")
                    .then().log().all().statusCode(200).extract().response().asString();
            js = ReuseMethods.convertToJson(getResponse);
            String getBookID = js.getString("ID");
            System.out.println("Id Is " + getBookID);
            DeleteBook(getBookID);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void DeleteBook(String bookID) {
        ReuseMethods.baseUrlAddBook();
        String Response = given().log().all().body(AddPlacePayload.DeleteBookPayload(bookID)).
                when().post("/Library/DeleteBook.php").
                then().log().all().assertThat().
                statusCode(200).body("msg", equalTo("book is successfully deleted")).extract().response().asString();
        System.out.println("Book is deleted successfully!");
    }
}
