
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Userlib extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.userlib");

    @Override
    protected void onLoad() {

    	String s = "";
    	
  //      String s = org.openhab.automation.javarules.userlib.MyUtility.MY_ID;

        logger.info("transform done, got: " + s);
    }
}
