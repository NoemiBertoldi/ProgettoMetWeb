function blankPharmacyFields()
{
    blankPersonnelFields();
    document.getElementById("nomeF").style.backgroundColor = "white";
    document.getElementById("indirizzo").style.backgroundColor = "white";
    document.getElementById("telefono").style.backgroundColor = "white";
}

function blankPersonnelFields()
{
    document.getElementById("cf").style.backgroundColor = "white";
    document.getElementById("nome").style.backgroundColor = "white";
    document.getElementById("cognome").style.backgroundColor = "white";
    document.getElementById("username").style.backgroundColor = "white";
    document.getElementById("password").style.backgroundColor = "white";
    document.getElementById("passwordConfirm").style.backgroundColor = "white";
    document.getElementById("dataNascita").style.backgroundColor = "white";
    document.getElementById("codRegionale").style.backgroundColor = "white";
    document.getElementById("dataNascita").style.backgroundColor = "white";
}

function validateCf(cf, user)
{
    if (cf.length != 16)
    {
        if(user == "reg")
            blankPharmacyFields();
        else
            blankPersonnelFields();

        alert("Codice Fiscale non valido!");
        document.getElementById("cf").style.backgroundColor = "red";
        return false;
    }
    else
        return true;
}

function validateName(name, id, user)
{
    var pattern = /^[A-Za-z\s]+$/;

    if(! pattern.test(name))
    {
        if(user == "reg")
            blankPharmacyFields();
        else
            blankPersonnelFields();

        document.getElementById(id).style.backgroundColor = "red";
        return false;
    }
    else
        return true;
}

function validatePassword(pass1, pass2, user)
{
    if(pass1 != pass2)
    {
        if(user == "reg")
            blankPharmacyFields();
        else
            blankPersonnelFields();

        alert("Le password non corrispondono");
        document.getElementById("password").style.backgroundColor = "red";
        document.getElementById("passwordConfirm").style.backgroundColor = "red";
        return false;
    }
    else
        return true;
}

function validateDate(date, user)
{
    var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/ ;

    if(date == "")
        return true;

    if(! pattern.test(date))
    {
        if(user == "reg")
            blankPharmacyFields();
        else
            blankPersonnelFields();

        document.getElementById("dataNascita").style.backgroundColor = "red";
        return false;
    }
    else
        return true;
}

function validateNumber(number, user)
{
    var pattern = /^([0-9]{10})$/ ;

    if(number == "")
        return true;

    if(! pattern.test(number))
    {
        if(user == "reg")
            blankPharmacyFields();
        else
            blankPersonnelFields();

        document.getElementById("telefono").style.backgroundColor = "red";
        return false;
    }
    else
        return true;
}

function validatePersonnelForm()
{
    var nome = document.forms["form"]["nome"].value;
    var cognome = document.forms["form"]["cognome"].value;
    var cf = document.forms["form"]["cf"].value;
    var pass1 = document.forms["form"]["password"].value;
    var pass2 = document.forms["form"]["passwordConfirm"].value;
    var data = document.forms["form"]["dataNascita"].value;
    var ret = true;

    ret = validateName(nome, "nome", "pers");
    if(ret == true)
        ret = validateName(cognome, "cognome", "pers");
    if(ret == true)
        ret = validateCf(cf, "pers");
    if(ret == true)
        ret = validatePassword(pass1, pass2, "pers");
    if(ret == true)
        ret = validateDate(data, "pers");

    return ret;
}

function validatePharmacyForm()
{
    var nome = document.forms["form"]["nome"].value;
    var cognome = document.forms["form"]["cognome"].value;
    var cf = document.forms["form"]["cf"].value;
    var pass1 = document.forms["form"]["password"].value;
    var pass2 = document.forms["form"]["passwordConfirm"].value;
    var data = document.forms["form"]["dataNascita"].value;
    var tel = document.forms["form"]["telefono"].value;
    var ret = true;

    ret = validateName(nome, "nome", "reg");
    if(ret == true)
        ret = validateName(cognome, "cognome", "reg");
    if(ret == true)
        ret = validateCf(cf, "reg");
    if(ret == true)
        ret = validatePassword(pass1, pass2, "reg");
    if(ret == true)
        ret = validateDate(data, "reg");
    if(ret == true)
        ret = validateNumber(tel, "reg");

    return ret;
}
