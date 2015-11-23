package cool.blink.examples.interfaces;

import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.ButtonType;
import cool.blink.front.html.attribute.Id;
import cool.blink.front.html.attribute.Onclick;
import cool.blink.front.html.attribute.ScriptType;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Button;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Doctype;
import cool.blink.front.html.element.H2;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Script;
import cool.blink.front.html.property.value.ButtonTypeValue;
import cool.blink.front.html.property.value.DoctypeValue;
import cool.blink.front.html.property.value.MethodValue;
import cool.blink.front.javascript.template.Ajax;

/**
 * Please disable CORS on your browser before attempting to run this example
 */
public class AjaxRequest {

    public static void main(String args[]) {
        Doctype doctype = new Doctype(DoctypeValue.HTML5);
        Html html = new Html();
        Head head = new Head();
        Ajax ajax = new Ajax("myDiv", MethodValue.GET, "https://github.com/ThreaT/blink");
        Script script = (Script) new Script().append(new Text(ajax.getFunction())).append(new ScriptType("text/javascript"));
        Body body = new Body();
        Div myDiv = (Div) new Div().append(new Id("myDiv")).append(new H2().append(new Text("Let AJAX change this text")));
        Button button = (Button) new Button().append(new ButtonType(ButtonTypeValue.button)).append(new Onclick(ajax.getCall())).append(new Text("Change content"));
        Document document = new Document()
                .append(
                        doctype
                )
                .append(
                        html.append(
                                head.append(
                                        script
                                )
                        ).append(
                                body.append(
                                        myDiv
                                ).append(
                                        button
                                )
                        )
                );
        System.out.println(document.print());
    }
}
