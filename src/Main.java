import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    static String dir="E:\\radar-data\\楼下-停车-10bin\\";
    static int[] sensors={2,4,5,6,7};//雷达编号集合
    static String resDir1="雷达\\";//结果目录名

    static int sensorNumb;//当前编号


    public static void main(String[] args) {


        File mk=new File(dir);
        String[] files=mk.list();

        File file = new File(dir+resDir1);
        if (!file.exists()) {
            file.mkdir();
        }

        for (int j = 0; j < sensors.length; j++) {//不同节点
            sensorNumb=sensors[j];
            String resDir2=sensorNumb+"号\\";
            File file2 = new File(dir+resDir1+resDir2);
            if (!file2.exists()) {
                file2.mkdir();
            }
            for (int i = 0; i <files.length ; i++) {//
                engine(dir+files[i], dir+resDir1+resDir2+files[i]);
            }
        }



    }

    public static void engine(String input, String output) {
        ArrayList<String> targetOut = new ArrayList<>();//输出值

        try {
            InputStream is = new FileInputStream(input);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            is.close();
            String targetIn = new String(bytes);//输入字符串

            mySplit(targetOut,targetIn);


            File file = new File(output);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter out = new FileWriter(file);
            // System.out.println(target);
            //out.write(" ");
            int i = 0;
            for (String key : targetOut) {
                i++;
                if (i % 11 != 0)
                    out.write(key + ",");
                else
                    out.write(key + "\r\n");
            }
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void mySplit(ArrayList<String> targetOut, String targetIn) throws ParseException {

        String[] tars = targetIn.split("Thread-4  INFO com.cadre.wvds.sniffer.SnifferApp - <"+sensorNumb+">:      mag sample:");
        for(int i=1;i<tars.length;i++)

        {
            String[] tars0 = tars[i].split(", r1=", 2);

            //String[] temp = tars[0].split("Thread-4  INFO com.cadre.wvds.sniffer.SnifferApp - <8>:", 2);

            String time=tars[i-1].substring(tars[i-1].length() - 24, tars[i-1].length() - 5);

            // String[] tarsx1 = tars[1].split(", x1=", 2);///
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(time);
            //日期转时间戳（毫秒）
            long time1=date.getTime();
            targetOut.add(String.valueOf(time1));

            String[] tars1 = tars0[1].split(", r2=", 2);

            targetOut.add(tars1[0]);//第一个
            String[] tars2 = tars1[1].split(", r3=", 2);
            targetOut.add(tars2[0]);//

            String[] tars3 = tars2[1].split(", r4=", 2);
            targetOut.add(tars3[0]);

            String[] tars4 = tars3[1].split(", r5=", 2);
            targetOut.add(tars4[0]);

            String[] tars5 = tars4[1].split(", r6=", 2);
            targetOut.add(tars5[0]);

            String[] tars6 = tars5[1].split(", r7=", 2);
            targetOut.add(tars6[0]);

            String[] tars7 = tars6[1].split(", r8=", 2);
            targetOut.add(tars7[0]);

            String[] tars8 = tars7[1].split(", r9=", 2);
            targetOut.add(tars8[0]);

            String[] tars9 = tars8[1].split(", r10=", 2);
            targetOut.add(tars9[0]);


            String[] tars10 = tars9[1].split(", x1=", 2);
            targetOut.add(tars10[0]);

        }
    }

}
