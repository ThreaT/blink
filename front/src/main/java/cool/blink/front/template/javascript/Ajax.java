package cool.blink.front.template.javascript;

import cool.blink.front.html.property.value.MethodValue;
import cool.blink.front.utilities.Maps;
import java.util.Arrays;
import java.util.Map;

public final class Ajax {

    private final String function;
    private final String call;

    public Ajax(final String idOfElementToChange, final MethodValue methodValue, final String serverUrl) {
        StringBuilder functionBuilder = new StringBuilder();
        String functionName = "sendRequest_" + System.nanoTime() + "()";
        functionBuilder.append("\r");
        functionBuilder.append("function ").append(functionName).append("{");
        functionBuilder.append("var xmlhttp;");
        functionBuilder.append("if (window.XMLHttpRequest) {");
        functionBuilder.append("xmlhttp=new XMLHttpRequest();");
        functionBuilder.append("} else {");
        functionBuilder.append("xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");");
        functionBuilder.append("}");
        functionBuilder.append("xmlhttp.onreadystatechange=function() {");
        functionBuilder.append("if (xmlhttp.readyState===4 && xmlhttp.status===200) {");
        functionBuilder.append("document.getElementById(\"").append(idOfElementToChange).append("\").innerHTML=xmlhttp.responseText;");
        functionBuilder.append("}");
        functionBuilder.append("};\n");
        functionBuilder.append("xmlhttp.open(\"").append(methodValue.toString()).append("\",\"").append(serverUrl).append("\",true);");
        functionBuilder.append("xmlhttp.send();");
        functionBuilder.append("}");
        this.function = functionBuilder.toString();
        this.call = functionName + ";";
    }

    public Ajax(final String idOfElementToChange, final MethodValue methodValue, final String serverUrl, final Map.Entry<String, String>... parameterKeysAndValues) {
        StringBuilder functionBuilder = new StringBuilder();
        String functionName = "sendRequest_" + System.nanoTime() + "()";
        functionBuilder.append("\r");
        functionBuilder.append("function ").append(functionName).append("{");
        functionBuilder.append("var xmlhttp;");
        if (Maps.countParameters(parameterKeysAndValues) > 0) {
            for (Map.Entry<String, String> entry : parameterKeysAndValues) {
                functionBuilder.append("var").append(" ").append(entry.getKey()).append("=").append("document.getElementById(\"").append(entry.getValue()).append("\")").append(".value;");
            }
        }
        functionBuilder.append("if (window.XMLHttpRequest) {");
        functionBuilder.append("xmlhttp=new XMLHttpRequest();");
        functionBuilder.append("} else {");
        functionBuilder.append("xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");");
        functionBuilder.append("}");
        functionBuilder.append("xmlhttp.onreadystatechange=function() {");
        functionBuilder.append("if (xmlhttp.readyState===4 && xmlhttp.status===200) {");
        functionBuilder.append("document.getElementById(\"").append(idOfElementToChange).append("\").innerHTML=xmlhttp.responseText;");
        functionBuilder.append("}");
        functionBuilder.append("};\n");
        functionBuilder.append("xmlhttp.open(\"").append(methodValue.toString()).append("\",\"").append(serverUrl);
        if (Maps.countParameters(parameterKeysAndValues) > 0) {
            functionBuilder.append("?");
            for (Map.Entry<String, String> entry : parameterKeysAndValues) {
                if (!Maps.isFirstEntry(Arrays.asList(parameterKeysAndValues), entry)) {
                    functionBuilder.append("+").append("\"");
                }
                functionBuilder.append(entry.getKey());
                functionBuilder.append("=");
                functionBuilder.append("\"+");
                functionBuilder.append(entry.getKey());
            }
        }
        functionBuilder.append(",true);");
        functionBuilder.append("xmlhttp.send();");
        functionBuilder.append("}");
        this.function = functionBuilder.toString();
        this.call = functionName + ";";
    }

    public String getFunction() {
        return function;
    }

    public String getCall() {
        return call;
    }

}
