package cool.blink.site.search.read;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Blink;
import cool.blink.back.core.Response;
import cool.blink.back.core.Url;
import cool.blink.back.search.Query;
import cool.blink.back.search.Result;
import cool.blink.back.search.Score;
import cool.blink.back.utilities.Longs;
import cool.blink.back.utilities.Urls;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Onclick;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.property.BackgroundColor;
import cool.blink.front.html.property.Border;
import cool.blink.front.html.property.Cursor;
import cool.blink.front.html.property.FontSize;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.BorderStyleValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.ColorNameValue;
import cool.blink.front.html.property.value.CursorValue;
import cool.blink.front.html.property.value.FontSizeValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.site.Application;
import cool.blink.site.home.read.Home;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Search extends Scenario {

    public static final SearchTemplate searchTemplate = new SearchTemplate();

    public Search(Url... urls) {
        super(Search.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Running fit: {0}", this.toString());
        return Urls.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Search.class.getName()).log(Level.INFO, "Running main: {0}", this.toString());
        try {
            Response response = new SearchTemplate(new Query(Longs.generateUniqueId(), request.getParameters().get("query")));
            Application.getWebServer().send(request, response);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>
     * Ensure that processing completes in under 10 ms.
     * </li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(Search.class.getName()).log(Level.INFO, "Running test: {0}", this.toString());
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public static final class SearchTemplate extends Response {

        private final Document document;
        private final Score score;
        private final Query query;
        private final List<Result> bestResults;
        private final Div resultsBox;

        protected SearchTemplate() {
            Logger.getLogger(SearchTemplate.class.getName()).log(Level.INFO, "Preparing SearchTemplate...");
            this.score = new Score();
            this.query = null;
            this.bestResults = null;
            this.resultsBox = (Div) new Div().append(new Style(new Width(270, WidthValue.pixels)));
            this.document = new Document().append(this.resultsBox);
            super.setCode(200);
            super.setPayload(this.document.print());
        }

        public SearchTemplate(final Query query) {
            this.document = searchTemplate.getDocument();
            this.score = searchTemplate.getScore();
            this.query = query;
            this.bestResults = searchTemplate.getScore().findBestResults(this.query, Blink.getResults(), 3, 10);
            Div tempResultsBox = searchTemplate.getResultsBox();
            for (Result result : this.getBestResults()) {
                String url = "";
                switch (result.getTitle()) {
                    case "HTML to Front Converter":
                        url = "'htmltofront'";
                        break;
                    case "Scenario Creator":
                        url = "'scenariocreator'";
                        break;
                }
                tempResultsBox = (Div) tempResultsBox.append(new Div().append(new Onclick("location.href=" + url)).append(new Style(new Width(100, WidthValue.percent), new BackgroundColor(ColorNameValue.White), new Cursor(CursorValue.pointer), new Height(50, HeightValue.pixels), new FontSize(14, FontSizeValue.pixels), new Border(1, BorderWidthValue.pixels, ColorNameValue.LightGray, BorderStyleValue.solid))).append(new Text(result.getTitle() + " - " + result.getDescription())));
            }
            this.resultsBox = tempResultsBox;
            super.setCode(200);
            super.setPayload(searchTemplate.getDocument().replaceAll(searchTemplate.getResultsBox(), this.resultsBox).print());
        }

        public final Document getDocument() {
            return document;
        }

        public final Score getScore() {
            return score;
        }

        public final Query getQuery() {
            return query;
        }

        public final List<Result> getBestResults() {
            return bestResults;
        }

        public final Div getResultsBox() {
            return resultsBox;
        }

    }

    @Override
    public String toString() {
        return "Search{" + super.toString() + '}';
    }

}
