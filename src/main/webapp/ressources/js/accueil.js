function rechercherFleche(numeroPage, categorieId)
{
    var form = creerGetFormulaire(numeroPage, categorieId);
    submitForm(form);

}

function rechercherInput(event, inputId, categorieId)
{
    if(event.key === "Enter")
    {
        var numero = document.getElementById(inputId).value;
        var form = creerGetFormulaire(numero, categorieId);
        submitForm(form);
    }
}

function creerGetFormulaire(numeroPage, categorieId)
{
    var form = document.createElement("form");
    form.method = "get";
    form.action = "/recherche";
    form.appendChild(creerHiddenParam("page", numeroPage));
    form.appendChild(creerHiddenParam("option", categorieId));
    return form;
}

function creerHiddenParam(nomParam, valeurParam)
{
    var hidden   = document.createElement("input");
    hidden.name  = nomParam;
    hidden.value = valeurParam;
    return hidden;

}

function submitForm(form)
{
    document.body.appendChild(form);
    form.submit();
}