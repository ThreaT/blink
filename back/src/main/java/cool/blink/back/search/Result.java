package cool.blink.back.search;

public class Result {

    private String title;
    private String description;

    public Result(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Result{" + "title=" + title + ", description=" + description + '}';
    }

}
