
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.model.script.actions.Exec;
import org.openhab.core.model.script.actions.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticActions extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.actions");

    @Override
    protected void onLoad() {

        String res = HTTP.sendHttpGetRequest("http://localhost/");

        String cmd = "termux-media-player play camera-shutter.mp3";
        Exec.executeCommandLine("ssh", "nexus9", cmd);

        logger.info("static actions done");
    }
}
