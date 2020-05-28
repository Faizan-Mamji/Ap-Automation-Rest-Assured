package LoadConfigurations;

import java.io.FileInputStream;
import java.util.Properties;

public class FetchConfigData {
    FileInputStream fs;
    Properties prop = new Properties();
    String getU;
    String getW;

    public FetchConfigData() {
        try {
            fs = new FileInputStream("Configurations/config.properties");
            prop.load(fs);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public String getUserName() {
        getU = prop.getProperty("user");
        return getU;
    }

    public String getPassWord() {
        getW = prop.getProperty("pass");
        return getW;
    }
}
