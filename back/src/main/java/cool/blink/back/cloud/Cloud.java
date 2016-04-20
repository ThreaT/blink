package cool.blink.back.cloud;

import java.util.List;

public class Cloud {

    private List<Prerun> preruns;
    private List<Postrun> postruns;

    public List<Prerun> getPreruns() {
        return preruns;
    }

    public void setPreruns(List<Prerun> preruns) {
        this.preruns = preruns;
    }

    public List<Postrun> getPostruns() {
        return postruns;
    }

    public void setPostruns(List<Postrun> postruns) {
        this.postruns = postruns;
    }

}
