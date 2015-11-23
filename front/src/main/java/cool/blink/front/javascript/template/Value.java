package cool.blink.front.javascript.template;

public class Value {

    private String function = "";
    
    public Value() {
        
    }

    public Value(String elementId) {
        function += "document.getElementById(\"" + elementId + "\").value;";
    }
    
    public Value(String elementId, String value) {
        function += "document.getElementById(\"" + elementId + "\").value = " + value + ";";
    }

    public Value add(String elementId, String value) {
        function += "document.getElementById(\"" + elementId + "\").value += " + value + ";";
        return this;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

}
