package cool.blink.back.cloud;

public class Destination {

    private DestinationType destinationType;
    private String absolutePath;
    private String hostname;
    private String username;
    private String password;
    private Integer port;

    public Destination(String absolutePath) {
        this.destinationType = DestinationType.LOCAL;
        this.absolutePath = absolutePath;
        this.hostname = "";
        this.username = "";
        this.password = "";
        this.port = 0;
    }

    public Destination(String absolutePath, String hostname, String username, String password) {
        this.destinationType = DestinationType.FTP;
        this.absolutePath = absolutePath;
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = 21;
    }

    public Destination(String absolutePath, String hostname, String username, String password, Integer port) {
        this.destinationType = DestinationType.SFTP;
        this.absolutePath = absolutePath;
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
