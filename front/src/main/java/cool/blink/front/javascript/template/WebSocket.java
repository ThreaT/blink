package cool.blink.front.javascript.template;

import cool.blink.front.utilities.Longs;

public final class WebSocket {

    private final String function;
    private final String openSocketCall;
    private final String sendMessageCall;
    private final String closeSocketCall;

    public WebSocket(final Boolean openConnectionAutomatically, final String alreadyOpenedTask, final String serverUrl, final String openTask, final String messageTask, final String closeTask, final String messageElementSource) {
        Long currentNanoTime = +Longs.generateUniqueId();
        String openSocketName = "openSocket_" + currentNanoTime;
        String sendName = "send_" + currentNanoTime;
        String closeSocketName = "closeSocket_" + currentNanoTime;
        StringBuilder functionBuilder = new StringBuilder();
        if (openConnectionAutomatically) {
            functionBuilder.append("(function () {").append(openSocketName).append("();})();");
        }
        functionBuilder.append("var webSocket;");
        functionBuilder.append("function ").append(openSocketName).append("() {");
        functionBuilder.append("if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {");
        functionBuilder.append(alreadyOpenedTask);
        functionBuilder.append("return;");
        functionBuilder.append("}");
        functionBuilder.append("webSocket = new WebSocket(\"").append(serverUrl).append("\");");
        functionBuilder.append("webSocket.onopen = function (event) {");
        functionBuilder.append("if (event.data === undefined) {");
        functionBuilder.append("return;");
        functionBuilder.append("}");
        functionBuilder.append(openTask);
        functionBuilder.append("};");
        functionBuilder.append("webSocket.onmessage = function (event) {");
        functionBuilder.append(messageTask);
        functionBuilder.append("};");
        functionBuilder.append("webSocket.onclose = function (event) {");
        functionBuilder.append(closeTask);
        functionBuilder.append("};");
        functionBuilder.append("}");
        functionBuilder.append("function ").append(closeSocketName).append("() {");
        functionBuilder.append("webSocket.close();");
        functionBuilder.append("}");
        functionBuilder.append("function ").append(sendName).append("() {");
        functionBuilder.append("var text = ").append(messageElementSource);
        functionBuilder.append("webSocket.send(text);");
        functionBuilder.append("}");
        this.function = functionBuilder.toString();
        this.openSocketCall = openSocketName + "();";
        this.sendMessageCall = sendName + "();";
        this.closeSocketCall = closeSocketName + "();";
    }

    public String getFunction() {
        return function;
    }

    public String getOpenSocketCall() {
        return openSocketCall;
    }

    public String getSendMessageCall() {
        return sendMessageCall;
    }

    public String getCloseSocketCall() {
        return closeSocketCall;
    }

}
