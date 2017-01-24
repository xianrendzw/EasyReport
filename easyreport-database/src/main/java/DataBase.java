import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import java.io.*;
import java.util.zip.*;

public class DataBase {
    static DB db = null;

    public static void main(String[] args) {
    	System.out.println("add shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread(){
          	 @Override
               public void run() {
                   System.out.println("clean some work.");
                   if (db != null) {
                       try {
                           db.stop();
                       } catch (ManagedProcessException e1) {
                           e1.printStackTrace();
                       }
                   }
               }
          });
        
    	boolean init =false;
        if (!new File("db/dbdir").exists()) {
        	init=true;
        }
        

        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setDataDir("db/dbdir");
        config.setPort(3307); // 0 => autom. detect free port

        try {
            db = DB.newEmbeddedDB(config.build());

            db.start();

//            String dbName = "easyreport2"; // or just "test"
//            if (!dbName.equals("test")) {
//                // mysqld out-of-the-box already has a DB named "test"
//                // in case we need another DB, here's how to create it first
//                db.createDB(dbName);
//            }
             
            System.out.println(System.getProperty("user.dir") );
            
            if(init){
            	System.out.println("start import data, that will cost a lot time, please wait...");
            	System.out.println("table easyResport2 start...");
            	db.run("SET character_set_client = utf8; SET character_set_connection = utf8; SET character_set_database = utf8; SET character_set_results = utf8;SET character_set_server = utf8; SET collation_connection = utf8_bin; SET collation_database = utf8_bin; SET collation_server = utf8_bin; "
            	+"source "+System.getProperty("user.dir").replaceAll("\\\\","/")+"/src/main/resources/db/easyreport2.sql;");
            	System.out.println("table easyResport2 end");
            	System.out.println("table china_weather_air_mysql start...");
            	db.run("SET character_set_client = utf8; SET character_set_connection = utf8; SET character_set_database = utf8; SET character_set_results = utf8;SET character_set_server = utf8; SET collation_connection = utf8_bin; SET collation_database = utf8_bin; SET collation_server = utf8_bin; "
					+"source "+System.getProperty("user.dir").replaceAll("\\\\","/")+"/src/main/resources/db/china_weather_air_mysql.sql;");
            	System.out.println("table china_weather_air_mysql end");
//            	db.run("grant all privileges on *.* to data@localhost identified by 'data123';",null,null,null);
//            	db.run("CREATE USER 'data' IDENTIFIED BY 'data123';",null,null,null);
//            	db.run("insert into mysql.user (host,user,password) values ('%','data',password('data123'));flush privileges;",null,null,"mysql");

            	System.out.println("end init");
            	
            }
            
            System.out.println("press ENTER to call System.exit() and run the shutdown routine.");  
            try {  
                System.in.read();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            System.exit(0); 

        } catch (Exception e) {
            e.printStackTrace();
            if (db != null) {
                try {
                    db.stop();
                } catch (ManagedProcessException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
