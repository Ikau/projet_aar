/**
 * Affiche le formulaire de modification de l'intitule d'une catégorie.
 * @param categorieId L'ID de la categorie.
 */
function afficherFormCategorie(categorieId)
{
    // On vérifie s'il n'y a pas déjà un form
    if(document.getElementById("td-form-cat"+categorieId) !== null) return;

    // On enleve visuellement ce qui existe deja
    document.getElementById("intitule-cat"+categorieId).hidden = true;
    document.getElementById("bouton-cat"+categorieId).hidden   = true;

    // On creer notre formulaire et ses boutons
    var form    = creerCategorieForm(categorieId);
    var annuler = creerAnnuler(categorieId);

    // Création des <td>
    var td = creerTdForm(categorieId);

    // Ajoute le tout à notre <tr>
    var tr = document.getElementById("tr-cat"+categorieId);
    tr.appendChild(td);
}

/**
 * Créer la balise <td> contenant le formulaire de modification de l'intitule d'une catégorie.
 * @param categorieId L'ID de la categorie.
 * @returns {HTMLTableDataCellElement} La balise <td>.
 */
function creerTdForm(categorieId)
{
    // Création du <td>
    var td = document.createElement("td");
    td.id  = "td-form-cat"+categorieId;

    // Création du form
    var form = creerCategorieForm(categorieId);
    td.appendChild(form);

    return td;
}

/**
 * Créer le formulaire de modification de l'intitule d'une catégorie.
 * @param categorieId L'ID de la categorie.
 * @returns {HTMLFormElement} Le <form>.
 */
function creerCategorieForm(categorieId)
{
    // Création du form
    var form    = document.createElement("form");
    form.method = "post";
    form.action = "/admin/categories/"+categorieId;

    // Création des inputs
    var input   = creerInput(categorieId);
    var submit  = creerSubmit();
    var annuler = creerAnnuler(categorieId);

    form.appendChild(input);
    form.appendChild(submit);
    form.appendChild(annuler);

    return form;
}

/**
 * Créer la balise <input> pour écrire le nouvel intitule de la catégorie.
 * @param categorieId L'ID de la categorie.
 * @returns {HTMLInputElement} La balise <input>.
 */
function creerInput(categorieId)
{
    var input   = document.createElement("input");
    input.name  = "intitule";
    input.type  = "text";

    // On met le nom d'origine de la catégorie.
    input.value = document.getElementById("intitule-cat"+categorieId).innerText;

    return input;
}

/**
 * Créer la balise <input> permettant d'envouer le formulaire.
 * @returns {HTMLInputElement} La balise <input> de type 'submit'.
 */
function creerSubmit()
{
    var submit   = document.createElement("input");
    submit.type  = "submit";
    submit.value = "Confirmer";
    return submit;
}

/**
 * Créer le bouton 'annuler' pour détruire le formulaire.
 * @param categorieId L'ID de la categorie.
 * @returns {HTMLButtonElement} La balise <button> d'annulation.
 */
function creerAnnuler(categorieId)
{
    var annuler       = document.createElement("button");
    annuler.onclick   = function(){detruitFormCategorie(categorieId);};
    annuler.innerText = "Annuler";
    return annuler;
}

/**
 * Détruit le formulaire et réaffiche les anciennes informations.
 * @param categorieId L'ID de la categorie.
 */
function detruitFormCategorie(categorieId)
{
    // Destruction du formulaire
    document.getElementById("td-form-cat"+categorieId).remove();

    // Affichage des éléments originaux
    document.getElementById("intitule-cat"+categorieId).hidden = false;
    document.getElementById("bouton-cat"+categorieId).hidden   = false;
}