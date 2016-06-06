package cool.blink.back.database;

import cool.blink.back.core.Environment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static java.util.Arrays.asList;
import java.util.List;
import org.apache.ibatis.jdbc.ScriptRunner;

public abstract class Builder {

    private final String structure;
    private final List<Environment> environments;

    public Builder() {
        this.structure = "";
        this.environments = null;
    }

    public Builder(final Environment... environments) {
        this.structure = "";
        this.environments = asList(environments);
    }

    public Builder(final String structure, final Environment... environments) {
        this.structure = structure;
        this.environments = asList(environments);
    }

    public final String getStructure() {
        return structure;
    }

    public final List<Environment> getEnvironments() {
        return environments;
    }

    /**
     * This method will be run when a blink application is started
     *
     */
    public synchronized void execute() {

    }

    /**
     * This method will be run when a blink application is started and will
     * install a dump script to a specified environment
     *
     * @param environment environment
     * @param driverName driverName
     * @param dump dump
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws FileNotFoundException FileNotFoundException
     * @throws SQLException SQLException
     */
    public synchronized void execute(final Environment environment, final String driverName, final String dump) throws ClassNotFoundException, FileNotFoundException, SQLException {
        Class.forName(driverName);
        Connection connection1 = DriverManager.getConnection(environment.getUrl(), environment.getUsername(), environment.getPassword());
        ScriptRunner scriptRunner = new ScriptRunner(connection1);
        scriptRunner.setLogWriter(null);
        StringReader stringReader = new StringReader(dump);
        Reader reader = new BufferedReader(stringReader);
        scriptRunner.runScript(reader);
    }

    /**
     * This method can be used to create all physical tables in a database
     */
    public synchronized void createTables() {

    }

    /**
     * This method is used to extract all data from a data source and store it
     * in memory for processing later on
     */
    public synchronized void retrieveData() {

    }

    /**
     * This method takes all data stored in memory by installUsingMaster(final
     * File masterDatabase) or installUsingSource(final Database database) and
     * inserts it into the database
     */
    public synchronized void commitAll() {

    }

    @Override
    public String toString() {
        return "Builder{" + "structure=" + structure + ", environments=" + environments + '}';
    }

}
