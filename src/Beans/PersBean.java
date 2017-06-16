package Beans;

import org.apache.struts.action.ActionForm;

public class PersBean extends ActionForm
{
    private String cf;
    private String username;
    private String password;
    private String passwordConfirm;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String codRegionale;
    private String role;

    public PersBean()
    {}

    public String getCf() {
        return cf;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCf(String cf) {
        this.cf = cf;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCodRegionale() {
        return codRegionale;
    }

    public void setCodRegionale(String codRegionale) {
        this.codRegionale = codRegionale;
    }
}

