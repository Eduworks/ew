import com.eduworks.cruncher.service.CruncherDropboxList;
import com.eduworks.cruncher.service.CruncherDropboxLoad;
import com.eduworks.resolver.Context;
import com.eduworks.util.io.InMemoryFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.util.HashMap;


public class DropboxTest {
    @Test
    public void ListTest() throws JSONException {

        CruncherDropboxList cdl = new CruncherDropboxList();
        cdl.build("path","");
        cdl.build("accessToken","");
        cdl.build("clientIdentifier","dropbox/eduworks_test");
        JSONArray result = (JSONArray) cdl.resolve(new Context(),new HashMap<>(),null);
        System.out.println(result.toString());
        CruncherDropboxLoad l = new CruncherDropboxLoad();
        l.build("path",result.getString(0));
        l.build("accessToken","");
        l.build("clientIdentifier","dropbox/eduworks_test");
        InMemoryFile imf = (InMemoryFile) l.resolve(new Context(),new HashMap<>(),null);
        System.out.println(imf.data.length);
    }
}
