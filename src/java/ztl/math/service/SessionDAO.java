/*
 * This Application and all Its resources are the property of
 * John Mtonga(Application Developer)
 */
package ztl.math.service;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import ztl.math.obj.Session;


/**
 *
 * @author John Mtonga(Application Developer)
 */
public class SessionDAO {

    public static void createSession(Session s) throws IOException {
        File f = new File("math_sessions/session");

        if (!f.exists()) {
            f.mkdirs();
        }
        FileWriter w = new FileWriter(String.format("%s/%s.txt", f.getPath(), s.getMSISDN()));
        w.write(new Gson().toJson(s));
        w.flush();
        w.close();
    }

    /**
     *
     * @param MSISDN
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Session getSession(String MSISDN) throws FileNotFoundException, IOException {
        File f = new File("math_sessions/session");

        if (!f.exists()) {
            f.mkdirs();
        }

        FileReader reader = new FileReader(String.format("%s/%s.txt", f.getPath(), MSISDN));
        Session s = new Gson().fromJson(reader, Session.class);
        reader.close();
        return s;
    }

    /**
     *
     * @param transactionID
     * @param MSISDN
     */
    public static void deleteSession(String MSISDN) {
        File f = new File(String.format("math_sessions/session/%s.txt", MSISDN));

        if (f.exists()) {
            f.delete();
        }
    }
}
