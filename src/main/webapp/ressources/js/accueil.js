/**
 * Active une recherche via le clic sur les flèches de navigation.
 * @param numeroPage Le numero de la page à afficher.
 * @param categorieId L'ID de la catégorie à rechercher.
 */
function rechercherFleche(numeroPage, categorieId)
{
    var form = creerGetFormulaire(numeroPage, categorieId);
    submitForm(form);

}

/**
 * Permet d'effectuer une recherche via le champ de texte.
 * @param event L'évènement reçu dans l'input.
 * @param inputId L'ID de l'input.
 * @param categorieId L'ID de la catégorie.
 */
function rechercherInput(event, inputId, categorieId)
{
    if(event.key === "Enter")
    {
        var numero = document.getElementById(inputId).value;
        if(numero <= 0) return;

        var form = creerGetFormulaire(numero, categorieId);
        submitForm(form);
    }
}

/**
 * Permet de créer le formulaire de soumission de la méthode get pour obtenir une page de recherche particulière..
 * @param numeroPage Le numero de la page souhaitée.
 * @param categorieId L'ID de la catégorie à rechercher.
 * @returns {HTMLFormElement} Le <form> vers la page souhaitée.
 */
function creerGetFormulaire(numeroPage, categorieId)
{
    var form = document.createElement("form");
    form.method = "get";
    form.action = "/recherche";
    form.appendChild(creerHiddenParam("page", numeroPage));
    form.appendChild(creerHiddenParam("option", categorieId));
    return form;
}

/**
 * Créer une balise invisible pour la création d'un formulaire.
 * @param nomParam Le nom du paramètre.
 * @param valeurParam La valeur du paramètre.
 * @returns {HTMLInputElement} La balise <hidden>.
 */
function creerHiddenParam(nomParam, valeurParam)
{
    var hidden   = document.createElement("input");
    hidden.name  = nomParam;
    hidden.value = valeurParam;
    return hidden;

}

/**
 * Ajoute le formulaire dans la page et l'actionne immédiatement.
 * @param form Le <form> à soumettre.
 */
function submitForm(form)
{
    document.body.appendChild(form);
    form.submit();
}