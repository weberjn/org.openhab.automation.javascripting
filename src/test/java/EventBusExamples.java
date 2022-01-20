
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.types.DecimalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExamples extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.eventbus");

    
    
    @Override
    protected void onLoad() {

        events.sendCommand("Livingroom_Light", "OFF");

        Item item = itemRegistry.get("Morning_Temperature");
        
        ((NumberItem)item).setState(new DecimalType(0.0f));
        
        events.postUpdate(item, 37.2f);

        Number state = (Number) item.getState();
        logger.info("new State: {}", state.floatValue());

        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));

        state = (Number) item.getState();
        logger.info("new State again: {}", state.floatValue());

        
        logger.info("eventbus done");  
    }
}
