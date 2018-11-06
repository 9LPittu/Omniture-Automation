package com.jcrew.helper.outlook.msgparser.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcrew.helper.outlook.msgparser.Message;
import com.jcrew.helper.outlook.msgparser.MsgParser;
import junit.framework.TestCase;

public class ParserTest extends TestCase {

    public void testParsing() {
        try {
            MsgParser msgp = new MsgParser();

            Handler[] handlers = Logger.getLogger("").getHandlers();
            for (int index = 0; index < handlers.length; index++) {
                handlers[index].setLevel(Level.INFO);
            }
            Logger logger = Logger.getLogger(MsgParser.class.getName());
            logger.setLevel(Level.INFO);

            Message msg = null;

            File testDir = new File("src/test/resources");
            File[] testFiles = testDir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".msg");
                }
            });

            for (File testFile : testFiles) {
                msg = msgp.parseMsg(testFile);
                System.out.println(msg.getConvertedBodyHTML());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
