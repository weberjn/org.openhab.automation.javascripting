
import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.openhab.core.persistence.extensions.PersistenceExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistItems extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.persist");

    @Override
    protected void onLoad() {

        Item item = itemRegistry.get("Morning_Temperature");

        PersistenceExtensions.persist(item);

        logger.info("persist done");
    }
}
