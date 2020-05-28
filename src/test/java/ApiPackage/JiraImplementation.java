package ApiPackage;

import LoadConfigurations.FetchConfigData;
import Payloads.JiraPayloads;
import ReuseablePackage.JiraReuseMethods;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class JiraImplementation {
    JsonPath js;
    FetchConfigData objFD;
    String getSessionValue, getIssueID;

    public void jiraLoginAuthentication() {
        objFD = new FetchConfigData();
        JiraReuseMethods.baseUrlJira();
        String getResponse = given().log().all()
                .header(JiraReuseMethods.contentType(), JiraReuseMethods.contentValue())
                .body(JiraPayloads.postAuthenticationPayload(objFD.getUserName(), objFD.getPassWord()))
                .when().post(JiraReuseMethods.postAuthenticationResponse())
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        js = JiraReuseMethods.convertToJson(getResponse);
        getSessionValue = js.getString("session.value");
        System.out.println("The Value is " + getSessionValue);
    }

    public void createIssue(String cookieVal) {
        JiraReuseMethods.baseUrlJira();
        String getResponse = given().log().all()
                .header(JiraReuseMethods.headerCookie(), JiraReuseMethods.headerCookieValue(cookieVal))
                .header(JiraReuseMethods.contentType(), JiraReuseMethods.contentValue())
                .body(JiraPayloads.createIssue())
                .when().post(JiraReuseMethods.postCreateIssue())
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();
        js = JiraReuseMethods.convertToJson(getResponse);
        getIssueID = js.getString("id");
        System.out.println("The ticket id is: " + js.getString("key"));
    }

    public void deleteIssue(String cookieVal, String issueID) {
        JiraReuseMethods.baseUrlJira();
        given().log().all().header(JiraReuseMethods.headerCookie(), JiraReuseMethods.headerCookieValue(cookieVal))
                .when().delete(JiraReuseMethods.deleteResource(issueID))
                .then().log().all().assertThat().statusCode(204);
    }

    public void addComments(String cookieVal, String issueID) {
        JiraReuseMethods.baseUrlJira();
        System.out.println(JiraReuseMethods.addCommentsResource(getIssueID));
        given().log().all().header(JiraReuseMethods.headerCookie(), JiraReuseMethods.headerCookieValue(cookieVal))
                .header(JiraReuseMethods.contentType(), JiraReuseMethods.contentValue())
                .body(JiraPayloads.addCommentsPayload())
                .when().post(JiraReuseMethods.addCommentsResource(issueID))
                .then().log().all().assertThat().statusCode(201);
    }

    public void executeEndToEnd() {
        createIssue(getSessionValue);
        addComments(getSessionValue, getIssueID);
        //deleteIssue(getSessionValue, getIssueID);
    }
}
