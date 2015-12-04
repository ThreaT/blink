package cool.blink.back.cloud;

import java.util.Arrays;
import java.util.List;

public final class Prerun {

    private final List<Dialog> dialogs;

    public Prerun(final Dialog... dialogs) {
        this.dialogs = Arrays.asList(dialogs);
        for (Dialog dialog : dialogs) {
            dialog.execute();
        }
    }

    public final List<Dialog> getDialogs() {
        return dialogs;
    }

}
