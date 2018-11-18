/**
 * Active une recherche via le clic sur les flèches de navigation.
 * @param numeroPage Le numero de la page à afficher.
 * @param categorieId L'ID de la catégorie à rechercher.
 */
function rechercherNumero(numeroPage, categorieId)
{
    var form = creerGetFormulaire(numeroPage, categorieId);
    submitForm(form);

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
    form.hidden = true;
    form.appendChild(creerHInputParam("page", numeroPage));
    form.appendChild(creerHInputParam("option", categorieId));
    return form;
}

/**
 * Créer une balise invisible pour la création d'un formulaire.
 * @param nomParam Le nom du paramètre.
 * @param valeurParam La valeur du paramètre.
 * @returns {HTMLInputElement} La balise <hidden>.
 */
function creerHInputParam(nomParam, valeurParam)
{
    var input   = document.createElement("input");
    input.name  = nomParam;
    input.value = valeurParam;
    return input;

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