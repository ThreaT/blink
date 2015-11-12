package cool.blink.front.template.javascript;

import cool.blink.front.utilities.Longs;

public class Offline {

    private final String function;
    private final String call;

    public Offline() {
        String functionName = "offline" + "_" + Longs.generateUniqueId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("function ").append(functionName).append("() {");
        stringBuilder.append("var markup = document.documentElement.innerHTML;");
        stringBuilder.append("if(typeof(Storage) !== \"undefined\") {");
        stringBuilder.append("if (sessionStorage.payload) { //If cache exists");
        stringBuilder.append("document.documentElement.innerHTML = markup;");
        stringBuilder.append("} else { //If cache does not exist");
        stringBuilder.append("sessionStorage.payload = markup;");
        stringBuilder.append("}");
        stringBuilder.append("} else {");
        stringBuilder.append("alert(\"Your browser does not support web storage, please consider changing or updating your browser.\");");
        stringBuilder.append("}");
        stringBuilder.append("}");
        this.function = stringBuilder.toString();
        this.call = functionName + "();";
    }

    public String getFunction() {
        return function;
    }

    public String getCall() {
        return call;
    }

}
