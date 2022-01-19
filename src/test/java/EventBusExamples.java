
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExamples extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.command");

    @Override
    protected void onLoad() {

        events.sendCommand("Livingroom_Light", "OFF");

        Item item = itemRegistry.get("Morning_Temperature");
        events.postUpdate(item, 37.2f);

        logger.info("command sent");
    }
}
