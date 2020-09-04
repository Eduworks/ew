package com.eduworks.cruncher.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;

public class PemReader extends BufferedReader {
    private static final String BEGIN = "-----BEGIN ";
    private static final String END = "-----END ";

    public PemReader(Reader var1) {
        super(var1);
    }

    public PemObject readPemObject() throws IOException {
        String var1;
        for(var1 = this.readLine(); var1 != null && !var1.startsWith("-----BEGIN "); var1 = this.readLine()) {
        }

        if (var1 != null && var1.trim().startsWith("-----BEGIN ") && var1.trim().endsWith("KEY-----"))
        {
            int firstdash = 0;
            var1 = var1.trim();
            int seconddash = var1.indexOf("-----",0+5);
            int thirddash = var1.indexOf("-----",seconddash+5);
            int fourthdash = var1.indexOf("-----",thirddash+5);
            String beginChunk = var1.substring(0+5,seconddash);
            String endChunk = var1.substring(thirddash+5,fourthdash);
            beginChunk = beginChunk.replace("BEGIN ","");
            endChunk = endChunk.replace("END ","");
            String mainChunk = var1.substring(seconddash+5,thirddash);
            return new PemObject(beginChunk,new ArrayList(),Base64.decode(mainChunk.toString()));
        }

        if (var1 != null) {
            var1 = var1.substring("-----BEGIN ".length());
            int var2 = var1.indexOf(45);
            String var3 = var1.substring(0, var2);
            if (var2 > 0) {
                return this.loadObject(var3);
            }
        }

        return null;
    }

    private PemObject loadObject(String var1) throws IOException {
        String var3 = "-----END " + var1;
        StringBuffer var4 = new StringBuffer();
        ArrayList var5 = new ArrayList();

        String var2;
        while((var2 = this.readLine()) != null) {
            if (var2.indexOf(":") >= 0) {
                int var6 = var2.indexOf(58);
                String var7 = var2.substring(0, var6);
                String var8 = var2.substring(var6 + 1).trim();
                var5.add(new PemHeader(var7, var8));
            } else {
                if (var2.indexOf(var3) != -1) {
                    break;
                }

                var4.append(var2.trim());
            }
        }

        if (var2 == null) {
            throw new IOException(var3 + " not found");
        } else {
            return new PemObject(var1, var5, Base64.decode(var4.toString()));
        }
    }
}
