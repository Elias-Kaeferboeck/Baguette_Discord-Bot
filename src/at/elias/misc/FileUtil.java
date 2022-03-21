package at.elias.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtil {

    public static String getTokenFromFile() {
        try {
            File f = new File(System.getProperty("user.dir") + Constants.TOKEN_PATH);
            BufferedReader r = new BufferedReader(new FileReader(f));
            return r.readLine();
        } catch (Exception e) {
            new CustomLogger("FileUtil", "There was a Problem reading the token from the file \"" + System.getProperty("user.dir") + Constants.TOKEN_PATH + "\".").setError(true).setHasOutline(true).send();
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
