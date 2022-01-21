
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Extlib extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.ext");

    @Override
    protected void onLoad() {

        String s = "";

        // commented out, haven't got the dependency to .ext here
        // s = org.openhab.automation.javarules.ext.T.ID;

        logger.info("ext done, got: " + s);
    }
}
