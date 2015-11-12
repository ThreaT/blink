package cool.blink.front.html.property.value;

public enum InputTypeValue {

    button("button"),
    checkbox("checkbox"),
    color("color"),
    date("date"),
    datetime("datetime"),
    datetime_local("datetime-local"),
    email("email"),
    file("file"),
    hidden("hidden"),
    image("image"),
    month("month"),
    number("number"),
    password("password"),
    radio("radio"),
    range("range"),
    reset("reset"),
    search("search"),
    submit("submit"),
    tel("tel"),
    text("text"),
    time("time"),
    url("url"),
    week("week");

    private final String inputTypeValue;

    private InputTypeValue(final String inputTypeValue) {
        this.inputTypeValue = inputTypeValue;
    }

    @Override
    public final String toString() {
        return inputTypeValue;
    }

}
