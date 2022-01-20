
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.thing.binding.ThingActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMail extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.mail");

    @Override
    protected void onLoad() {

        ThingActions thingActions = actions.get("mail", "mail:smtp:mailSender");
        actions.invoke(thingActions, "mail_at_receiver", "a subject", "mailcontent Java script onload()");

        logger.info("mail sent");
    }
}
