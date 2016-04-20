package cool.blink.back.webserver;

import java.util.Arrays;
import java.util.List;

/**
 * All scenarios must implement this interface in order to connect to the blink
 * framework.
 */
public class Scenario implements Cloneable {

    private Class clazz;
    private List<Url> urls;
    private Method fit;
    private Method main;
    private Method test;

    public Scenario(Class clazz, Url... urls) {
        this.clazz = clazz;
        this.urls = Arrays.asList(urls);
        this.fit = new Method(clazz, "fit", new Class[]{Request.class});
        this.main = new Method(clazz, "main", new Class[]{Request.class});
        this.test = new Method(clazz, "test", new Class[]{Request.class});
    }

    /**
     * Used to determine if scenario is fit to webServerProcess request or not.
     *
     * @param request request
     * @return Boolean Boolean
     */
    public Boolean fit(Request request) {
        return false;
    }

    /**
     * Used for scenario main webServerProcessing.
     *
     * @param request request
     */
    public void main(Request request) {

    }

    /**
     * Used for scenario automated acceptance tests. This method is required
     * because it would be too slow to perform all tests on every request. This
     * method allows tests to be run only when explicitly requested.
     *
     * When overriding this method, it is best practice to include acceptance
     * requirement comments for traceability purposes. i.e.
     *
     * <ol>
     * <li>Given a request parameter "name", When "name" is equal to "Foo", Then
     * return SUCCESS</li>
     * <li>Given a request parameter "name", When "name" is equal to null, Then
     * return FAILURE</li>
     * </ol>
     *
     * @param request request
     * @return Report Report
     */
    public Report test(Request request) {
        return null;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    /**
     * @return A request that can be customised for testing purposes.
     */
    /**
     * @return A response that can be customised for testing purposes.
     */
    /**
     * @return method responsible for determining the request eligibility.
     */
    public Method getFit() {
        return fit;
    }

    public void setFit(Method fit) {
        this.fit = fit;
    }

    /**
     * @return method responsible for webServerProcessing the request.
     */
    public Method getMain() {
        return main;
    }

    public void setMain(Method main) {
        this.main = main;
    }

    /**
     * @return method responsible for testing the request.
     */
    public Method getTest() {
        return test;
    }

    public void setTest(Method test) {
        this.test = test;
    }

    @Override
    public Scenario clone() throws CloneNotSupportedException {
        return (Scenario) super.clone();
    }

    @Override
    public String toString() {
        return "Scenario{" + "clazz=" + clazz + ", urls=" + urls + ", fit=" + fit + ", main=" + main + ", test=" + test + '}';
    }

}
