
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.transform.actions.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transformations extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.transform");

    @Override
    protected void onLoad() {

        String s = Transformation.transform("REGEX", ".*(hello).*", "hello, world");

        logger.info("transform done, got: " + s);
    }
}
