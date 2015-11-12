package cool.blink.front.template.javascript;

public class CurrentYear {

    private String function;

    public CurrentYear() {
        function = "document.write(new Date().getFullYear());";
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

}
