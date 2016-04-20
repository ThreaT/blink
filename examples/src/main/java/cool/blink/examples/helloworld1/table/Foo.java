package cool.blink.examples.helloworld1.table;

public class Foo {

    private Long id;
    private String name;

    public Foo() {
    }

    public Foo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo{" + "id=" + id + ", name=" + name + '}';
    }

}
