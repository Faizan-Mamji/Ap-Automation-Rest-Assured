package Payloads;

public class JiraPayloads {
    public static String postAuthenticationPayload(String userName, String passWord) {
        return "{ \n" +
                "\t\"username\": \"" + userName + "\",\n" +
                "\t\"password\": \"" + passWord + "\"\n" +
                "}";
    }

    public static String createIssue() {
        return "{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \"MJT\"\n" +
                "       },\n" +
                "       \"summary\": \"REST ye merry gentlemen.\",\n" +
                "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Bug\"\n" +
                "       }\n" +
                "   }\n" +
                "}";
    }

    public static String addCommentsPayload() {
        return "{\n" +
                "    \"body\": \"Comments added aumatically using rest assured\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
    }
}
