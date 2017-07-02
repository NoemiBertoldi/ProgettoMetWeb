function blankPresFields()
{
    document.getElementById("cfPaz").style.backgroundColor = "white";
    document.getElementById("nomePaz").style.backgroundColor = "white";
    document.getElementById("cognomePaz").style.backgroundColor = "white";
    document.getElementById("dataNascitaPaz").style.backgroundColor = "white";
}

function validateCf(cf, id)
{
    if (cf.length != 16)
    {
        blankPresFields();
        document.getElementById(id).style.backgroundColor = "red";
        alert("Invalid Fiscal Code!");

        return false;
    }
    else
        return true;
}

function validateName(name, id)
{
    var pattern = /^[A-Za-z\s]+$/;

    if(! pattern.test(name))
    {
        blankPresFields();
        document.getElementById(id).style.backgroundColor = "red";
        alert("Invalid Name!");

        return false;
    }
    else
        return true;
}

function validateDate(date, id)
{
    var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;

    if (date == "")
        return true;

    if (!pattern.test(date))
    {
        blankPresFields();
        document.getElementById(id).style.backgroundColor = "red";
        alert("Invalid Date! ");

        return false;
    }
    else
        return true;
}

function validatePrescriptionForm()
{
    var cf = document.forms["form"]["cfPaz"].value;
    var nome = document.forms["form"]["nomePaz"].value;
    var cognome = document.forms["form"]["cognomePaz"].value;
    var data = document.forms["form"]["dataNascitaPaz"].value;
    var ret;

    ret = validateCf(cf, "cfPaz");

    if(ret == true)
        ret = validateName(nome, "nomePaz");
    if(ret == true)
        ret = validateName(cognome, "cognomePaz");
    if(ret == true)
        ret = validateDate(data, "dataNascitaPaz");

    return ret;
}
