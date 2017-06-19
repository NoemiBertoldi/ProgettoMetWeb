package Beans;

import org.apache.struts.action.ActionForm;

public class PresBean extends ActionForm
{
    private String cfPaz;
    private String nomePaz;
    private String cognomePaz;
    private String dataNascitaPaz;
    private String codRegMed;

    public PresBean()
    { }

    public String getCfPaz() {
        return cfPaz;
    }

    public void setCfPaz(String cfPaz) {
        this.cfPaz = cfPaz;
    }

    public String getNomePaz() {
        return nomePaz;
    }

    public void setNomePaz(String nomePaz) {
        this.nomePaz = nomePaz;
    }

    public String getCognomePaz() {
        return cognomePaz;
    }

    public void setCognomePaz(String cognomePaz) {
        this.cognomePaz = cognomePaz;
    }

    public String getDataNascitaPaz() {
        return dataNascitaPaz;
    }

    public void setDataNascitaPaz(String dataNascitaPaz) {
        this.dataNascitaPaz = dataNascitaPaz;
    }

    public String getCodRegMed() {
        return codRegMed;
    }

    public void setCodRegMed(String codRegMed) {
        this.codRegMed = codRegMed;
    }
}
