// Enum pour le type du formulaire
var typeEnum = Object.freeze({'edit':0, 'reponse':1})

/**
 * Affiche un formulaire d'édition ou de réponse pour un message particulier.
 * @param {int} idMsg L'ID du message.
 * @param {int} idProjet L'ID du projet.
 * @param {int} type Indique si le formulaire est une réponse ou un édit.
 */
function afficherForm(idMsg, idProjet, type)
{
    // Cacher les balises <a> 'éditer' et 'répondre'
    document.getElementById("a"+idMsg).hidden = true;

    // Ajout d'un <a> d'annulation
    var divParent = document.getElementById("div"+idMsg);
    var divAnnuler = creerAnnuler(idMsg);
    divParent.appendChild(divAnnuler);

    // Ajout du formulaire
    var form = creerForm(idMsg, idProjet, type);
    divParent.appendChild(form);
}

/**
 * Détruit le formulaire s'il est existant.
 * @param idMsg
 */
function detruitForm(idMsg)
{
    // Suppression du formulaire
    document.getElementById("form"+idMsg).remove();

    // Supression du <a> d'annulation
    document.getElementById("annuler"+idMsg).remove();

    // Affichage des <a>
    document.getElementById("a"+idMsg).hidden = false;
}

/**
 * Créer une balise <a> permettant d'annuler l'envoi du formulaire.
 * @param {int} idMsg L'ID du message que ce formulaire concerne.
 * @returns {HTMLAnchorElement} Une balise <a> d'annulation.
 */
function creerAnnuler(idMsg)
{
    var tagA       = document.createElement("a");
    tagA.id        = "annuler"+idMsg;
    tagA.onclick   = function(){detruitForm(idMsg);};
    tagA.className = "action-message";
    tagA.innerText = "Annuler";
    return tagA;
}

/**
 * Créer un formulaire de réponse ou d'édition d'un message.
 * @param {int} idMsg L'ID du message.
 * @param {int} idProjet L'ID du projet concerné par le message.
 * @param {int} type Indique si le formulaire est pour l'édition ou une réponse.
 * @returns {HTMLFormElement} Le formulaire d'édition ou de réponse.
 */
function creerForm(idMsg, idProjet, type)
{
    // Création balise et init basique
    var form    = document.createElement("form");
    form.id     = "form"+idMsg;
    form.method = "post";

    // On ajuste les balises selon le type du formulaire.
    switch(type)
    {
        case typeEnum.edit:
            form.action = "/messages/"+idMsg;
            var txtOriginal = document.getElementById("msg"+idMsg).innerText;
            form.appendChild(creerTextarea(txtOriginal));
            form.appendChild(document.createElement("br"));
            form.appendChild(creerHidden("PATCH"));
            form.appendChild(creerSubmit("Éditer"));
            break;

        case typeEnum.reponse:
            form.action = "/projets/"+idProjet+"/messages/"+idMsg;
            form.appendChild(creerTextarea(""));
            form.appendChild(document.createElement("br"));
            form.appendChild(creerSubmit("Répondre"));
            break;
    }

    return form;
}

/**
 * Créer une balise <textarea> pour le formulaire.
 * @returns {HTMLTextAreaElement} La balise <textarea> pour le formulaire.
 */
function creerTextarea(contenu)
{
    var textarea       = document.createElement("textarea");
    textarea.name      = "contenu";
    textarea.type      = "text";
    textarea.className = "w3-input";
    textarea.innerText = contenu;
    return textarea;
}

/**
 * Créer une balise <input type='submit'> pour le formulaire.
 * @param {string} valeur Le texte du bouton.
 * @returns {HTMLInputElement} La balise <input type='submit'>.
 */
function creerSubmit(valeur)
{
    var input       = document.createElement("input");
    input.className = "w3-button w3-white w3-border w3-border-theme w3-hover-theme";
    input.type      = "submit";
    input.value     = valeur;
    return input;
}

/**
 * Créer un input invisible contenant l'action à effectuer.
 * @param {string} valeur L'ID du projet.
 * @returns {HTMLInputElement} L'input invisible.
 */
function creerHidden(valeur)
{
    var input    = document.createElement("input");
    input.type   = "hidden";
    input.name   = "action";
    input.value  = valeur;
    return input;
}

/**
 * Demande à l'utilisateur s'il souhaite "supprimer" (=désactiver) son message et le fait le cas échéant.
 * @param idMsg L'ID du message à supprimer.
 */
function suppression(idMsg)
{
    if(confirm("Voulez-vous vraiment supprimer votre message ?"))
    {
        // Création d'un form de suppression
        var form    = document.createElement("form");
        form.method = "post";
        form.action = "/messages/"+idMsg;
        form.appendChild(creerHidden("DELETE"));

        // Le submit va rediriger vers l'URL du post : l'état de la page n'importe plus.
        document.body.appendChild(form);

        // Envoi du form
        form.submit();
    }
}