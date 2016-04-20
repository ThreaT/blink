package cool.blink.back.webserver;

public class Report {

    private Integer total;
    private Integer successful;
    private String percentage;
    private String log;

    public Report() {

    }

    public Report(Integer total, Integer successful, String percentage, String log) {
        this.total = total;
        this.successful = successful;
        this.percentage = percentage;
        this.log = log;
    }

    public String calculatePercentage(Integer total, Integer success) {
        return "" + success * (100 / total);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage + "%";
    }

    /**
     * @param log [line number][message]
     */
    public void appendLog(String[][] log) {
        this.log = this.log + "\nline " + log[0][0] + ": " + log[0][1] + "\n\n";
    }

    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------------------------------\nReport: " + "total=" + total + ", successful=" + successful + ", percentage=" + percentage + "\n------------------------------------------------------------------------" + log;
    }

}
