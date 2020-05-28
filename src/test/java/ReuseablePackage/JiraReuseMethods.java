package ReuseablePackage;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JiraReuseMethods {

    public static String baseUrlJira() {
        return RestAssured.baseURI = "http://localhost:8080";
    }

    public static String postAuthenticationResponse() {
        return "/rest/auth/1/session";
    }

    public static String postCreateIssue() {
        return "/rest/api/2/issue/";
    }

    public static String contentType() {
        return "Content-Type";
    }

    public static String contentValue() {
        return "application/json";
    }

    public static String headerCookie() {
        return "Cookie";
    }

    public static String headerCookieValue(String cookieValue) {
        return "JSESSIONID=" + cookieValue;
    }

    public static JsonPath convertToJson(String Response) {
        JsonPath js = new JsonPath(Response);
        return js;
    }

    public String getfilePath(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String deleteResource(String issueID) {
        return "/rest/api/2/issue/" + issueID;
    }

    public static String addCommentsResource(String issueID) {
        return "/rest/api/2/issue/" + issueID + "/comment";
    }
}
