package cool.blink.back.cloud;

public final class Condition {

    private final Comparator comparator;
    private final Object verifier;
    private final Dialog dialogForTrueResult;
    private final Dialog dialogForFalseResult;
    private Object output;
    private Boolean result;
    private Dialog dialogForResult;

    public enum Comparator {

        MORE_THAN,
        MORE_THAN_OR_EQUAL_TO,
        LESS_THAN,
        LESS_THAN_OR_EQUAL_TO,
        CONTAINS,
        EQUAL_TO

    }

    public Condition(final Comparator comparator, final Object verifier, final Dialog dialogForTrueResult, final Dialog dialogForFalseResult) {
        this.comparator = comparator;
        this.verifier = verifier;
        this.dialogForTrueResult = dialogForTrueResult;
        this.dialogForFalseResult = dialogForFalseResult;
    }

    public final Comparator getComparator() {
        return comparator;
    }

    public final Object getVerifier() {
        return verifier;
    }

    public final Boolean getResult() {
        return result;
    }

    public final Dialog getDialogForTrueResult() {
        return dialogForTrueResult;
    }

    public final Dialog getDialogForFalseResult() {
        return dialogForFalseResult;
    }

    public final Object getOutput() {
        return output;
    }

    public final void setOutput(final Object output) {
        this.output = output;
    }

    public Dialog getDialogForResult() {
        return dialogForResult;
    }

    public void setDialogForResult(Dialog dialogForResult) {
        this.dialogForResult = dialogForResult;
    }

    public final void compare() {
        System.out.println("Output from last operation: " + this.output);
        switch (this.comparator) {
            case MORE_THAN:
                this.result = (Integer) this.output > (Integer) this.output;
                break;
            case MORE_THAN_OR_EQUAL_TO:
                this.result = (Integer) this.output >= (Integer) this.output;
                break;
            case LESS_THAN:
                this.result = (Integer) this.output < (Integer) this.verifier;
                break;
            case LESS_THAN_OR_EQUAL_TO:
                this.result = (Integer) this.output <= (Integer) this.verifier;
                break;
            case CONTAINS:
                this.result = ((String) this.output).contains((String) this.verifier);
                break;
            default:
                this.result = output.equals(verifier);
                break;
        }
        if (this.result) {
            this.dialogForResult = this.dialogForTrueResult;
        } else {
            this.dialogForResult = this.dialogForFalseResult;
        }
        this.dialogForResult.execute();
    }

}
