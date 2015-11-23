package cool.blink.front.javascript.template;

public class InnerHtml {

    private String function;

    public InnerHtml() {

    }

    public InnerHtml(String elementId) {
        function = "";
        function += "document.getElementById(\"" + elementId + "\").innerHTML;";
    }

    public InnerHtml(String elementId, String innerHtml) {
        function = "";
        function += "document.getElementById(\"" + elementId + "\").innerHTML = " + innerHtml + ";";
    }

    public InnerHtml add(String elementId, String innerHtml) {
        function = "";
        function += "document.getElementById(\"" + elementId + "\").innerHTML += " + innerHtml + ";";
        return this;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

}
