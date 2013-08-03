package com.example.Thoth;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Utils {
    public static List<Integer> LastQuestionDisposition;
    public static List<Integer> LastSevenDisposition;
    public static List<Integer> LastCelticDisposition;
    public static List<Integer> LastModifiedDisposition;
    public static List<Integer> LastCentersDisposition;
    public static List<Integer> LastRelationsDisposition;
    public static List<Integer> LastBalanceDisposition;

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static String GetCardCode(int id) {
        String code = null;
        for (int i = 0; i < 22; i++) {
            if (id == i)
                return "M " + String.valueOf(i);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                if (id == i * 14 + (j + 1) + 21)
                    return ("m " + String.valueOf(i) + " - " + String.valueOf(j));
            }
        }
        return code;
    }

    public static String GetResourceName(String string) {
        if (string.startsWith("M")) {
            String num = string.split(" ")[1];
            switch (Integer.parseInt(num)) {
                case 0:
                    return "fool";
                case 1:
                    return "magus";
                case 2:
                    return "priestess";
                case 3:
                    return "empress";
                case 4:
                    return "emperor";
                case 5:
                    return "hierophant";
                case 6:
                    return "lovers";
                case 7:
                    return "chariot";
                case 8:
                    return "adjustment";
                case 9:
                    return "hermit";
                case 10:
                    return "fortune";
                case 11:
                    return "lust";
                case 12:
                    return "hangedman";
                case 13:
                    return "death";
                case 14:
                    return "art";
                case 15:
                    return "devil";
                case 16:
                    return "tower";
                case 17:
                    return "star";
                case 18:
                    return "moon2";
                case 19:
                    return "sun";
                case 20:
                    return "aeon";
                case 21:
                    return "universe";
                default:
                    break;
            }
        }
        else if (string.startsWith("m")) {
            int type = Integer.parseInt(string.split(" ")[1]);
            int value = Integer.parseInt(string.split(" ")[3]);
            switch(type) {
                case 0:
                    return GetMinor("cups", value);
                case 1:
                    return GetMinor("disks", value);
                case 2:
                    return GetMinor("wands", value);
                case 3:
                    return GetMinor("swords", value);
                default:
                    break;
            }
        }
        return null;
    }

    public static String GetMinor(String type, int value) {
        String val;
        switch(value) {
            case 10:
                val = "princessof" + type;
                break;
            case 11:
                val = "princeof" + type;
                break;
            case 12:
                val = "queenof" + type;
                break;
            case 13:
                val = "knightof" + type;
                break;
            default:
                val = String.format("%02d", value + 1);
                break;
        }
        return (type + val);
    }
}