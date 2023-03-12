
import java.util.Map;

import org.openhab.automation.javascripting.annotations.CronTrigger;
import org.openhab.automation.javascripting.annotations.Rule;
import org.openhab.automation.javascripting.scriptsupport.Script;
import org.openhab.core.automation.Action;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronRule extends Script {

    private Logger logger = LoggerFactory.getLogger("org.openhab.automation.javascripting.cronrule");

    private int counter = 1;

    @Rule(name = "CronRule")
    @CronTrigger(id = "CronTrigger", cronExpression = "0 * * * * ?")
    public SimpleRule cronrule = new SimpleRule() {

        @Override
        public Object execute(Action module, Map<String, ?> inputs) {

            logger.info("Java cronrule execute {}", counter++);

            return "";
        }
    };

    @Override
    protected void onLoad() {
        logger.info("Java onLoad()");
    };
}
