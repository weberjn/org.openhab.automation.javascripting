package org.openhab.automation.javascripting.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openhab.automation.javascripting.scriptsupport.Script;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRuleActionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleAnnotationParser {

    private static Logger logger = LoggerFactory.getLogger(RuleAnnotationParser.class);

    private Script scriptBase;

    public RuleAnnotationParser(Script scriptBase) {
        this.scriptBase = scriptBase;
    }

    public void parse() throws IllegalArgumentException, IllegalAccessException {
        Class<? extends Object> c = scriptBase.getClass();

        logger.debug("parse: {}", c.getName());

        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {

            Class<?> ftype = f.getType();

            if (org.openhab.core.automation.Rule.class.isAssignableFrom(ftype)
                    && SimpleRuleActionHandler.class.isAssignableFrom(ftype)) {

                if (f.isAnnotationPresent(Rule.class)) {
                    Annotation ra = f.getAnnotation(Rule.class);

                    Rule r = (Rule) ra;
                    String ruleName = r.name();
                    String fieldname = f.getName();
                    logger.debug("{} : Rule: {} ", fieldname, ruleName);

                    List<Trigger> triggers = new ArrayList<Trigger>();

                    Object object = f.get(scriptBase);
                    SimpleRule simpleRule = (SimpleRule) object;

                    Annotation[] as = f.getDeclaredAnnotations();
                    for (Annotation a : as) {

                        if (a instanceof CronTrigger) {
                            CronTrigger trigger = (CronTrigger) a;

                            createCronTrigger(triggers, trigger);
                        }

                        if (a instanceof CronTriggers) {
                            CronTriggers cronTriggers = (CronTriggers) a;

                            for (CronTrigger trigger : cronTriggers.value()) {
                                createCronTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof SystemTrigger) {
                            SystemTrigger trigger = (SystemTrigger) a;

                            createSystemTrigger(triggers, trigger);
                        }

                        if (a instanceof SystemTriggers) {
                            SystemTriggers systemTriggers = (SystemTriggers) a;

                            for (SystemTrigger trigger : systemTriggers.value()) {
                                createSystemTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof ItemStateChangeTrigger) {
                            ItemStateChangeTrigger trigger = (ItemStateChangeTrigger) a;

                            createItemStateChangeTrigger(triggers, trigger);
                        }

                        if (a instanceof ItemStateChangeTriggers) {
                            ItemStateChangeTriggers itemStateChangeTriggers = (ItemStateChangeTriggers) a;

                            for (ItemStateChangeTrigger trigger : itemStateChangeTriggers.value()) {
                                createItemStateChangeTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof ItemStateUpdateTrigger) {
                            ItemStateUpdateTrigger trigger = (ItemStateUpdateTrigger) a;

                            createItemStateUpdateTrigger(triggers, trigger);
                        }

                        if (a instanceof ItemStateUpdateTriggers) {
                            ItemStateUpdateTriggers itemStateUpdateTriggers = (ItemStateUpdateTriggers) a;

                            for (ItemStateUpdateTrigger trigger : itemStateUpdateTriggers.value()) {
                                createItemStateUpdateTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof ThingStateUpdateTrigger) {
                            ThingStateChangeTrigger trigger = (ThingStateChangeTrigger) a;

                            createThingStateChangeTrigger(triggers, trigger);
                        }

                        if (a instanceof ThingStateChangeTriggers) {
                            ThingStateChangeTriggers thingStateChangeTriggers = (ThingStateChangeTriggers) a;

                            for (ThingStateChangeTrigger trigger : thingStateChangeTriggers.value()) {
                                createThingStateChangeTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof ThingStateUpdateTrigger) {
                            ThingStateUpdateTrigger trigger = (ThingStateUpdateTrigger) a;

                            createThingStateUpdateTrigger(triggers, trigger);
                        }

                        if (a instanceof ThingStateUpdateTriggers) {
                            ThingStateUpdateTriggers thingStateUpdateTriggers = (ThingStateUpdateTriggers) a;

                            for (ThingStateUpdateTrigger trigger : thingStateUpdateTriggers.value()) {
                                createThingStateUpdateTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof ItemCommandTrigger) {
                            ItemCommandTrigger trigger = (ItemCommandTrigger) a;

                            createItemCommandTrigger(triggers, trigger);
                        }

                        if (a instanceof ItemCommandTriggers) {
                            ItemCommandTriggers itemCommandTriggers = (ItemCommandTriggers) a;

                            for (ItemCommandTrigger trigger : itemCommandTriggers.value()) {
                                createItemCommandTrigger(triggers, trigger);
                            }
                        }

                        if (a instanceof ChannelEventTrigger) {
                            ChannelEventTrigger trigger = (ChannelEventTrigger) a;

                            createChannelEventTrigger(triggers, trigger);
                        }

                        if (a instanceof ChannelEventTriggers) {
                            ChannelEventTriggers channelEventTriggers = (ChannelEventTriggers) a;

                            for (ChannelEventTrigger trigger : channelEventTriggers.value()) {
                                createChannelEventTrigger(triggers, trigger);
                            }
                        }

                    }

                    scriptBase.activateRule(ruleName, simpleRule, triggers);
                }
            }
        }
    }

    private void createCronTrigger(List<Trigger> triggers, CronTrigger ct) {
        String id = ct.id();
        String cronExpression = ct.cronExpression();

        logger.debug("CronTrigger: {}", cronExpression);

        Trigger trigger = scriptBase.createGenericCronTrigger(id, cronExpression);
        triggers.add(trigger);
    }

    private void createSystemTrigger(List<Trigger> triggers, SystemTrigger ct) {
        String id = ct.id();
        String startlevel = ct.startlevel();

        logger.debug("SystemTrigger, startlevel: {}", startlevel);

        Trigger trigger = scriptBase.createSystemStartlevelTrigger(id, startlevel);
        triggers.add(trigger);
    }

    private void createItemStateChangeTrigger(List<Trigger> triggers, ItemStateChangeTrigger isct) {
        String id = isct.id();
        String item = isct.item();

        logger.debug("ItemStateChangeTrigger: {}", item);

        Trigger trigger;

        if (isct.newState().isEmpty() && isct.previousState().isEmpty()) {
            trigger = scriptBase.createItemStateChangeTrigger(id, item);
        } else if (isct.previousState().isEmpty()) {
            trigger = scriptBase.createItemStateChangeTrigger(id, item, isct.newState());
        } else {
            trigger = scriptBase.createItemStateChangeTrigger(id, item, isct.newState(), isct.previousState());
        }
        triggers.add(trigger);
    }

    private void createItemStateUpdateTrigger(List<Trigger> triggers, ItemStateUpdateTrigger isut) {
        String id = isut.id();
        String item = isut.item();

        logger.debug("ItemStateUpdateTrigger: {}", item);

        Trigger trigger;

        if (isut.newState().isEmpty() && isut.previousState().isEmpty()) {
            trigger = scriptBase.createItemStateChangeTrigger(id, item);
        } else if (isut.previousState().isEmpty()) {
            trigger = scriptBase.createItemStateUpdateTrigger(id, item, isut.newState());
        } else {
            trigger = scriptBase.createItemStateUpdateTrigger(id, item, isut.newState(), isut.previousState());
        }
        triggers.add(trigger);
    }

    private void createThingStateChangeTrigger(List<Trigger> triggers, ThingStateChangeTrigger tsct) {
        String id = tsct.id();
        String thingUID = tsct.thingUID();

        logger.debug("ThingStateChangeTrigger: {}", thingUID);

        Trigger trigger;

        if (tsct.newState().isEmpty() && tsct.previousState().isEmpty()) {
            trigger = scriptBase.createThingChangeTrigger(id, thingUID);
        } else if (tsct.previousState().isEmpty()) {
            trigger = scriptBase.createThingChangeTrigger(id, thingUID, tsct.newState());
        } else {
            trigger = scriptBase.createThingChangeTrigger(id, thingUID, tsct.newState(), tsct.previousState());
        }
        triggers.add(trigger);
    }

    private void createThingStateUpdateTrigger(List<Trigger> triggers, ThingStateUpdateTrigger tsut) {
        String id = tsut.id();
        String thingUID = tsut.thingUID();

        logger.debug("ThingStateUpdateTrigger: {}", thingUID);

        Trigger trigger;

        if (tsut.newState().isEmpty() && tsut.previousState().isEmpty()) {
            trigger = scriptBase.createThingUpdateTrigger(id, thingUID);
        } else if (tsut.previousState().isEmpty()) {
            trigger = scriptBase.createThingUpdateTrigger(id, thingUID, tsut.newState());
        } else {
            trigger = scriptBase.createThingUpdateTrigger(id, thingUID, tsut.newState(), tsut.previousState());
        }
        triggers.add(trigger);
    }

    private void createItemCommandTrigger(List<Trigger> triggers, ItemCommandTrigger ict) {
        String id = ict.id();
        String item = ict.item();
        String command = ict.command();

        logger.debug("ItemCommandTrigger: {}", command);

        Trigger trigger = scriptBase.createItemCommandTrigger(id, item, command);
        triggers.add(trigger);
    }

    private void createChannelEventTrigger(List<Trigger> triggers, ChannelEventTrigger cet) {
        String id = cet.id();
        String channelUID = cet.channelUID();
        String event = cet.event();

        logger.debug("ChannelEventTrigger: {}", channelUID);

        Trigger trigger;

        if (event.isEmpty()) {
            trigger = scriptBase.createChannelEventTrigger(id, channelUID);
        } else {
            trigger = scriptBase.createChannelEventTrigger(id, channelUID, event);
        }
        triggers.add(trigger);
    }
}
