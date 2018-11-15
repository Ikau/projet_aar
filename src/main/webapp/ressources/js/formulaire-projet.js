
function ajouter() {

    var com = parseInt(document.getElementById("compN").innerText);

    // pour ajouter une compensation
    var bigDiv = document.createElement('div');
    bigDiv.className = "w3-container";
    var id = com;
    com = com + 1;
    bigDiv.id = com;
    document.getElementById("compN").innerText = com;

    var boutton = document.getElementById('addC');
    var newdiv = document.createElement('div');
    newdiv.className = "w3-container w3-cell";
    var text = document.createTextNode('Seuil');
    newdiv.appendChild(text);
    bigDiv.appendChild(newdiv);

    var newdiv2 = document.createElement('div');
    newdiv2.className = "w3-container w3-cell";
    var inp = document.createElement('input');
    inp.type='number';
    inp.className = "w3-input";
    inp.name="pallierList["+id+"].seuil";
    newdiv2.appendChild(inp);
    bigDiv.appendChild(newdiv2);

    var newdiv3 = document.createElement('div');
    newdiv3.className = "w3-container w3-cell";
    var text2 = document.createTextNode('Intitulé');
    newdiv3.appendChild(text2);
    bigDiv.appendChild(newdiv3);

    var newdiv4 = document.createElement('div');
    var inp3 = document.createElement('input');
    inp3.type='text';
    inp3.className = "w3-input";
    inp3.name="pallierList["+id+"].intitule";
    newdiv4.className = "w3-container w3-cell";
    newdiv4.appendChild(inp3);
    bigDiv.appendChild(newdiv4);

    var newdiv5 = document.createElement('div');
    newdiv5.className = "w3-container w3-cell";
    var text3 = document.createTextNode('Description de la compensation :');
    newdiv5.appendChild(text3);
    bigDiv.appendChild(newdiv5);

    var newdiv6 = document.createElement('div');
    newdiv6.className = "w3-container w3-cell";
    var inp2 = document.createElement('textarea');
    inp2.type='text';
    inp2.className = "w3-input";
    inp2.name="pallierList["+id+"].description";
    newdiv6.style="width: 50%";
    newdiv6.appendChild(inp2);
    bigDiv.appendChild(newdiv6);
    bigDiv.appendChild(document.createElement('br'));
    bigDiv.appendChild(document.createElement('br'));
    document.getElementById('compensation').insertBefore(bigDiv,boutton);

}

function enlever(){
    var com = parseInt(document.getElementById("compN").innerText);

    var div = document.getElementById(com);
    if(div === null) return;

    document.getElementById('compensation').removeChild(div);

    document.getElementById("compN").innerText = com-1;

}

