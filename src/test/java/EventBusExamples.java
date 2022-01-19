
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExamples extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.eventbus");

    @Override
    protected void onLoad() {

        events.sendCommand("Livingroom_Light", "OFF");

        Item item = itemRegistry.get("Morning_Temperature");
        events.postUpdate(item, 37.2f);

        Number state = (Number) item.getState();
        logger.info("new State: {}", state.floatValue());

        logger.info("eventbus done");
    }
}
