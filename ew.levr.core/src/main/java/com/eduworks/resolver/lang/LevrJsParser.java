package com.eduworks.resolver.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.log4j.Logger;

/**
 *
 * @author fray
 */
public class LevrJsParser
{

    public static Logger log = Logger.getLogger(LevrJsParser.class);
    public static ScriptEngineManager factory;
    public static ScriptEngine engine;

    static
    {
        factory = new ScriptEngineManager();
        engine = factory.getEngineByName("nashorn");
        log.info("Nashorn Javascript Engine Loaded.");
    }

    public static Bindings decodeStreams(File fileToDecode)
    {
        Invocable inv = (Invocable) engine;
        FileReader fr;
        try
        {
            fr = new FileReader(fileToDecode);
            engine.eval(fr);
            Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            fr.close();
            if (bindings.size() > 0)
            {
                log.debug(new TreeSet<String>(bindings.keySet()));
            }
            return bindings;
        } catch (FileNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
