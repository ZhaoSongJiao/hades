package com.yunqutech.hades.business.major;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultFileLog implements PrintLog {

    public void doPrintLog(String log) {
        doStaticPrintLog(log);
    }

    public static void doStaticPrintLog(String log) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
            String dateStr = sdf.format(date);
            File dir = new File("./sqlLog");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            int count = 0;
            String logFileName = "./sqlLog/" + dateStr + "_" + count + ".txt";
            File logFile = new File(logFileName);
            while (logFile.exists()) {
                count++;
                logFileName = "./sqlLog/" + dateStr + "_" + count + ".txt";
                logFile = new File(logFileName);
                if (count >= 100) {
                    System.out.println("doing too many times");
                    throw new RuntimeException("doing too many times loop");
                }
            }
            logFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(logFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(log.getBytes());
            bos.flush();
            bos.close();
            fos.close();
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
