import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import java.io.*;
import java.util.zip.*;

public class DataBase {
    static DB db = null;

    public static void main(String[] args) {
        if (!new File("db/dbdir").exists()) {
            long startTime = System.currentTimeMillis();
            try {
                ZipInputStream Zin = new ZipInputStream(new FileInputStream("db/dbdir.zip"));
                BufferedInputStream Bin = new BufferedInputStream(Zin);
                String Parent = "db"; // 输出路径（文件夹目录）
                File Fout = null;
                ZipEntry entry;
                try {
                    while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                        Fout = new File(Parent, entry.getName());
                        if (!Fout.exists()) {
                            (new File(Fout.getParent())).mkdirs();
                        }
                        FileOutputStream out = new FileOutputStream(Fout);
                        BufferedOutputStream Bout = new BufferedOutputStream(out);
                        int b;
                        while ((b = Bin.read()) != -1) {
                            Bout.write(b);
                        }
                        Bout.close();
                        out.close();
                        System.out.println(Fout + "解压成功");
                    }
                    Bin.close();
                    Zin.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("耗费时间： " + (endTime - startTime) + " ms");
        }
        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setDataDir("db/dbdir");
        config.setPort(3307); // 0 => autom. detect free port

        try {
            db = DB.newEmbeddedDB(config.build());

            db.start();

            String dbName = "easyreport2"; // or just "test"
            if (!dbName.equals("test")) {
                // mysqld out-of-the-box already has a DB named "test"
                // in case we need another DB, here's how to create it first
                db.createDB(dbName);
            }

            // db.source("db/easyreport2.sql", "root", null, dbName);
            // db.source("db/china_weather_air_mysql.sql", "root", null, dbName);
            // while(true){
            // Thread.sleep(1000);
            // }
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
