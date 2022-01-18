

import java.util.Map;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.automation.Action;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemChangedRule extends ScriptBase {

	private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.cronrule");

	public int counter = 1;

	@Override
	protected void onLoad() {

		SimpleRule sr = new SimpleRule() {

			@Override
			public Object execute(Action module, Map<String, ?> inputs) {

				logger.info("Java cronrule execute {}", counter++);

				return null;
			}
		};

		Trigger trigger = createItemStateChangeTrigger("BatteryLevelChangedTrigger", "BatteryLevel");

		ruleBuilder(sr).withName("BatteryLevelChanged").withTrigger(trigger).activate();

	};

}
