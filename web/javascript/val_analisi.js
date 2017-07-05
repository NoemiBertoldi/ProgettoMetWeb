function blankDateFields()
{
    document.getElementById("datai").style.backgroundColor = "white";
    document.getElementById("dataf").style.backgroundColor = "white";
}
function validateDate(date, type)
{
    var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/ ;

    if(date == "")
        return true;

    if(! pattern.test(date))
    {
        blankDateFields();
        if(type=="datai")
            document.getElementById("datai").style.backgroundColor = "red";
        else
            document.getElementById("dataf").style.backgroundColor = "red";
        return false;
    }
    else
        return true;
}
function validateAnalysis()
{
    var datai = document.forms["form"]["datai"].value;
    var dataf = document.forms["form"]["dataf"].value;
    var ret = true;

    ret = validateDate(datai, "datai");
    if(ret == true)
        ret = validateDate(dataf, "dataf");

    return ret;
}