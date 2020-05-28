package ReuseablePackage;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ReuseMethods {
    public static JsonPath convertToJson(String Response) {
        JsonPath js = new JsonPath(Response);
        return js;
    }

    public static String baseUrlAddDelete() {
        return RestAssured.baseURI = "https://rahulshettyacademy.com";
    }

    public static String baseUrlAddBook() {
        return RestAssured.baseURI = "http://216.10.245.166";
    }
}
