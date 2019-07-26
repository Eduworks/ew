import com.eduworks.cruncher.net.CruncherHttpGet;
import com.eduworks.lang.EwHashMap;
import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CruncherHttpGetTest {
    @Test
    public void CruncherHttpGetTestUtf8() {
        CruncherHttpGet c = new CruncherHttpGet();

        c.build("obj", "https://opensalt.net/ims/case/v1p0/CFPackages/c5fb3436-d7cb-11e8-824f-0242ac160002");

        try {
            EwJsonObject jo = (EwJsonObject) c.resolve(new Context(), new EwHashMap<>(), new EwHashMap<>());
            System.out.println(System.getProperty("file.encoding"));
            System.out.println(Charset.defaultCharset().toString());
            byte [] bArray = {'w'};
            InputStream is = new ByteArrayInputStream(bArray);
            InputStreamReader reader = new InputStreamReader(is);
            String defaultCharacterEncoding = reader.getEncoding();
            System.out.println(defaultCharacterEncoding);
            System.out.println(jo.getJSONArray("CFItems").getJSONObject(13).getString("fullStatement"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}