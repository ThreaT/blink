package cool.blink.front.javascript.template;

public final class SearchBoxToggle {

    private final String function;
    private final String call;

    public SearchBoxToggle(final String searchBoxId, final String searchBarId, final ToggleValue toggleValue) {
        String functionName = "searchBoxToggle_" + System.nanoTime();
        String style = "";
        String on = "";
        String off = "";
        if (toggleValue.equals(ToggleValue.display)) {
            style = "display";
            on = "block";
            off = "none";
        } else if (toggleValue.equals(ToggleValue.visibility)) {
            style = "visiblity";
            on = "visible";
            off = "hidden";
        }
        String newToggle = "\r";
        newToggle += "function " + functionName + "()" + "{";
        newToggle += "var e = document.getElementById('" + searchBoxId + "');";
        newToggle += "var f = document.getElementById('" + searchBarId + "');";
        newToggle += "if ((e.style." + style + " !== '" + off + "') && (f.value.length <= 0)) {";
        newToggle += "e.style." + style + " = '" + off + "';";
        newToggle += "} else if ((e.style." + style + " !== '" + on + "') && (f.value.length > 0)) {";
        newToggle += "e.style." + style + " = '" + on + "';";
        newToggle += "}";
        newToggle += "}";
        this.function = newToggle;
        this.call = functionName + "();";
    }

    public final String getFunction() {
        return function;
    }

    public final String getCall() {
        return call;
    }

}
