package com.eduworks.scripter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.github.rcaller.rstuff.*;
import com.github.rcaller.util.Globals;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherR extends Cruncher {
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        String rPath = optAsString("path", null, c, parameters, dataStreams);
        String rCode = getObj(c, parameters, dataStreams).toString();
        String returns = getAsString("return", c, parameters, dataStreams);

        RCallerOptions rco = null;
        if (rPath == null)
            rco = RCallerOptions.create();
        else
            rco = RCallerOptions.create(rPath, rPath, FailurePolicy.RETRY_5, 9223372036854775807L, 100L, RProcessStartUpOptions.create());
        RCaller caller = RCaller.create(rco);
        RCode code = RCode.create();
        // Passing Java objects to R
        code.addRCode(rCode);
        for (String key : keySet()) {
            if (key.equals("obj")) continue;
            if (key.equals("return")) continue;
            if (key.equals("path")) continue;
            Object value = get(key, c, parameters, dataStreams);
            interpretAndSet(code, key, value);
        }
        caller.setRCode(code);
        try {
            caller.redirectROutputToStream(System.out);
            caller.runAndReturnResult(returns);
        } finally {
            caller.stopStreamConsumers();
        }
        try {
            return XML.toJSONObject(caller.getParser().getXMLFileAsString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void interpretAndSet(RCode code, String key, Object value) throws JSONException {
        if (value instanceof JSONArray) {
            JSONArray ary = (JSONArray) value;
            if (ary.get(0) instanceof JSONArray) {
                JSONArray ary2 = ary.getJSONArray(0);
                double[][] matrix = new double[ary.length()][ary2.length()];
                for (int i = 0; i < ary.length(); i++) {
                    JSONArray aryx = ary.getJSONArray(i);
                    for (int j = 0; j < aryx.length(); j++) {
                        matrix[i][j] = aryx.optDouble(j, Double.parseDouble(aryx.optString(j)));
                    }
                }
                code.addDoubleMatrix(key, matrix);
            } else {
                try {
                    double[] matrix = new double[ary.length()];
                    for (int i = 0; i < ary.length(); i++) {
                        double val = Double.parseDouble(ary.get(i).toString());
                        matrix[i] = val;
                    }
                    code.addDoubleArray(key, matrix);
                } catch (NumberFormatException p) {
                    String[] matrix = new String[ary.length()];
                    for (int i = 0; i < ary.length(); i++) {
                        String val = ary.get(i).toString();
                        matrix[i] = val;
                    }
                    code.addStringArray(key, matrix);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Allows LEVR to run R";
    }

    @Override
    public String getReturn() {
        return "String";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "String", "path", "String", "returns", "String", "<any>", "String");
    }
}
