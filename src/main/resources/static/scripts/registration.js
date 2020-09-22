const name = document.getElementById("name");
const lastName = document.getElementById("lastName");
const username = document.getElementById("username");
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirmPassword");
const submitButton = document.getElementById("submitButton");

// Nome: può contenere solo lettere maiuscole e minuscole
function isValidName() {
    let regex = /^[a-zA-Z]+$/;
    return name.value.match(regex);
}

//Cognome: stesse regole del Nome
function isValidLastName() {
    let regex = /^[a-zA-Z]+$/;
    return lastName.value.match(regex);
}

//Username: può contenere lettere maiuscole, minuscole, numeri e il carattere _
function isValidUsername() {
    let regex = /^[0-9a-zA-Z_]+$/;
    return username.value.match(regex);
}

//Password: come lo Username. Deve inoltre avere una lunghezza tra gli 8 e i 15 caratteri compresi
function isValidPassword() {
    let regex = /^[0-9a-zA-Z_]+$/;
    return password.value.match(regex) && (password.value.length >= 8) && (password.value.length <= 15);
}

function isPasswordConfirmed() {
    return (password.value === confirmPassword.value);
}

name.addEventListener('input', ev => {
    if (!isValidName())
        applyWarning(name, "Sono permesse solo lettere!");
    else {
        removeWarning(name);
    }
    updateSubmitButton();
});

lastName.addEventListener('input', ev => {
    if (!isValidLastName())
        applyWarning(lastName, "Sono permesse solo lettere!");
    else {
        removeWarning(lastName);
    }
    updateSubmitButton();
});

username.addEventListener('input', ev => {
    if (!isValidUsername())
        applyWarning(username, "Sono permessi lettere maiuscole, minuscole, numeri e il carattere _");
    else {
        removeWarning(username);
    }
    updateSubmitButton();
});

password.addEventListener('input', ev => {
    if (!isValidPassword())
        applyWarning(password,
            "Sono permessi lettere maiuscole, minuscole, numeri e il carattere_ e lunghezza tra gli 8 e i 15 caratteri compresi");
    else {
        removeWarning(password);
    }
    updateSubmitButton();
});

confirmPassword.addEventListener('input', ev => {
    if (!isPasswordConfirmed())
        applyWarning(confirmPassword, "I due password non sono uguali");
    else {
        removeWarning(confirmPassword);
    }
    updateSubmitButton();
});

function applyWarning(element, message) {
    element.style.borderColor = "red";
    let tooltipElement = $("#" + element.name);
    tooltipElement.tooltip({
        title: message
        ,
        template: "<div class=\"tooltip\" role=\"tooltip\"><div class=\"arrow\"></div><div class=\"tooltip-inner\"></div></div>"
    });
    tooltipElement.tooltip('show');
}

function removeWarning(element) {
    element.style.borderColor = "green";
    $("#" + element.name).tooltip('dispose');
}

function updateSubmitButton() {
    submitButton.disabled = !(isValidName() && isValidLastName() && isValidUsername() && isValidPassword() && isPasswordConfirmed());
}
