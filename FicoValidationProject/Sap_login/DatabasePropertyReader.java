package Sap_login;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabasePropertyReader {
    private static final DatabasePropertyReader databasePropertyReader = new DatabasePropertyReader();
    private final Properties databaseProperties = new Properties();
    //private final Logger logger = LoggerFactory.getLogger(DatabasePropertyReader.class);

    private DatabasePropertyReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            //logger.error("Unable to load database property file.");
        }
    }

    public static DatabasePropertyReader getPropertyReader() {
        return databasePropertyReader;
    }

    private void loadProperties() throws IOException {
    	System.out.println(System.getProperty("user.dir"));
        FileInputStream databaseInput = new FileInputStream("Sap_login/databaseconnection.properties");
        databaseProperties.load(databaseInput);
        databaseInput = new FileInputStream("Sap_login/databasequeries.properties");
        databaseProperties.load(databaseInput);
        databaseInput = new FileInputStream("Sap_login/environment.properties");
        databaseProperties.load(databaseInput);

        String execEnvironment = databaseProperties.getProperty("BMEnvironment");
        //String dbSchemaName = databaseProperties.getProperty(execEnvironment + ".schema");
        //databaseProperties.setProperty("schema", dbSchemaName);
        
        if (execEnvironment.equalsIgnoreCase("steel") || execEnvironment.equalsIgnoreCase("titanium") || execEnvironment.equalsIgnoreCase("platinum")){
              execEnvironment = "jcms";
        }
        
        databaseProperties.setProperty("dbEnvironment", execEnvironment);
    }

    public String getProperty(String property) {
        return readProperty(property);
    }

    public boolean hasProperty(String key) {
        return databaseProperties.containsKey(key);
    }

    private String readProperty(String key) {
        String value = databaseProperties.getProperty(key);

        if (!hasProperty(key)) {
            throw new RuntimeException("Property '" + key + "' is not defined in databaseconnection or databasequeries file");
        }

        return value;
    }
}
