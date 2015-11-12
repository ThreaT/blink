package cool.blink.back.cache;

import cool.blink.back.core.Blink;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.back.utilities.Longs;
import java.util.Map;

public final class Etag {

    private static final String key = "etag";
    private final String value;

    public Etag() {
        this.value = Longs.generateUniqueId().toString();
    }

    public Etag(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final Etag getEtag(final Request request) {
        try {
            for (Map.Entry<String, String> parameter : request.getParameters().entrySet()) {
                if (parameter.getKey().equalsIgnoreCase(Etag.key)) {
                    return new Etag(parameter.getValue());
                }
            }
        } catch (NullPointerException ex) {

        }
        return null;
    }

    public static final Boolean isScenarioSupported(final Etag etag) {
        for (Scenario scenario : Blink.getScenarios()) {
            if (scenario.getEtag().equals(etag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final String toString() {
        return "Etag{" + "value=" + value + '}';
    }

}
