package Beans;

import org.apache.struts.action.ActionForm;

public class AnalysisBean extends ActionForm
{
    private String datai;
    private String dataf;

    public AnalysisBean()
    {}

    public String getDatai() {
        return datai;
    }

    public void setDatai(String datai) {
        this.datai = datai;
    }

    public String getDataf() {
        return dataf;
    }

    public void setDataf(String dataf) {
        this.dataf = dataf;
    }
}
