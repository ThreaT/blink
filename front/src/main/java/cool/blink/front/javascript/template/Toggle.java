package cool.blink.front.javascript.template;

public final class Toggle {

    private final String function;

    public Toggle(final String elementId, final ToggleValue toggleValue) {
        String functionName = "toggle_" + System.nanoTime();
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
        String newToggle = "function " + functionName + "(" + "id" + ")" + "{";
        newToggle += "var e = document.getElementById('" + elementId + "');";
        newToggle += "if (e.style." + style + " != '" + off + "') {";
        newToggle += "e.style." + style + " = '" + off + "';";
        newToggle += "} else {";
        newToggle += "e.style." + style + " = '" + on + "';";
        newToggle += "}";
        newToggle += "}";
        this.function = newToggle;
    }

    public final String getFunction() {
        return function;
    }

}
