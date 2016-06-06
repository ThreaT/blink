package cool.blink.back.core;

import java.io.File;

public final class Environment {

    private final String name;
    private final String url;
    private final String username;
    private final String password;
    private Integer port;
    private String additionalField1;
    private String additionalField2;
    private File additionalField3;

    public Environment(final String name, final String url) {
        this.name = name;
        this.url = url;
        this.username = null;
        this.password = null;
        this.port = null;
        this.additionalField1 = null;
        this.additionalField2 = null;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final File additionalField3) {
        this.name = name;
        this.url = url;
        this.username = null;
        this.password = null;
        this.port = null;
        this.additionalField1 = null;
        this.additionalField2 = null;
        this.additionalField3 = additionalField3;
    }

    public Environment(final String name, final String username, final String password) {
        this.name = name;
        this.url = null;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = null;
        this.additionalField2 = null;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = null;
        this.additionalField2 = null;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password, final String additionalField1) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = additionalField1;
        this.additionalField2 = null;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password, final String additionalField1, final File additionalField3) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = additionalField1;
        this.additionalField2 = null;
        this.additionalField3 = additionalField3;
    }

    public Environment(final String name, final String url, final String username, final String password, final File additionalField3) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = null;
        this.additionalField2 = null;
        this.additionalField3 = additionalField3;
    }

    public Environment(final String name, final String url, final String username, final String password, final String additionalField1, final String additionalField2) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = additionalField1;
        this.additionalField2 = additionalField2;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password, final String additionalField1, final String additionalField2, final File additionalField3) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = null;
        this.additionalField1 = additionalField1;
        this.additionalField2 = additionalField2;
        this.additionalField3 = additionalField3;
    }

    public Environment(final String name, final String url, final String username, final String password, final Integer port) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = port;
        this.additionalField1 = null;
        this.additionalField2 = null;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password, final Integer port, final String additionalField1) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = port;
        this.additionalField1 = additionalField1;
        this.additionalField2 = null;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password, final Integer port, final String additionalField1, final File additionalField3) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = port;
        this.additionalField1 = additionalField1;
        this.additionalField2 = null;
        this.additionalField3 = additionalField3;
    }

    public Environment(final String name, final String url, final String username, final String password, final Integer port, final String additionalField1, final String additionalField2) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = port;
        this.additionalField1 = additionalField1;
        this.additionalField2 = additionalField2;
        this.additionalField3 = null;
    }

    public Environment(final String name, final String url, final String username, final String password, final Integer port, final String additionalField1, final String additionalField2, final File additionalField3) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = port;
        this.additionalField1 = additionalField1;
        this.additionalField2 = additionalField2;
        this.additionalField3 = additionalField3;
    }

    public Environment(final Environment environment, final String url) {
        this.name = environment.getName();
        this.url = url;
        this.username = environment.getUsername();
        this.password = environment.getPassword();
        this.port = environment.getPort();
        this.additionalField1 = environment.getAdditionalField1();
        this.additionalField2 = environment.getAdditionalField2();
        this.additionalField3 = environment.getAdditionalField3();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAdditionalField1() {
        return additionalField1;
    }

    public void setAdditionalField1(String additionalField1) {
        this.additionalField1 = additionalField1;
    }

    public String getAdditionalField2() {
        return additionalField2;
    }

    public void setAdditionalField2(String additionalField2) {
        this.additionalField2 = additionalField2;
    }

    public File getAdditionalField3() {
        return additionalField3;
    }

    public void setAdditionalField3(File additionalField3) {
        this.additionalField3 = additionalField3;
    }

}
