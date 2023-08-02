package org.openhab.automation.javascripting.annotation;

public class RuleParserException extends Exception {

    private static final long serialVersionUID = 5744217657057910494L;

    RuleParserException(String message, Throwable e) {
        super(message, e);
    }

    RuleParserException(String message) {
        super(message);
    }

}
