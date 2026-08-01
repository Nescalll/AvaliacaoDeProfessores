
document.addEventListener('DOMContentLoaded', function() {

    const form = document.getElementById('form');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const button = document.getElementById('btn-submit');

    button.addEventListener('click', function() {
        event.preventDefault();

        checkForm();

    })
})

function checkInputEmail() {
    const emailValue = email.value;

    if(emailValue === "") {
        errorInput(email, "Preencha este campo!")
    } else {
        const formItem = email.parentElement;
        formItem.className = 'input-box'
    }
}

function checkInputPassword() {
    const passwordValue = password.value;

    if(passwordValue === "") {
        errorInput(password, "Preencha este campo!")
    } else if(passwordValue.length < 8) {
        errorInput(password, "No mínimo 8 caracteres!")
    } else {
        const formItem = password.parentElement;
        formItem.className = 'input-box'
    }
}

function checkForm() {
    checkInputEmail();
    checkInputPassword();

    const formItems = form.querySelectorAll(".input-box-error");

    const isValid = [...formItems].every((item) => {
        return item.className === "input-box";
    })

    if (isValid) {
        alert("Formulário enviado com sucesso!");

        const RegisterData = {
            email:email.value,
            senha:password.value
        }

        
    }
}

function errorInput(input, message) {
    const formItem = input.parentElement;
    const textMessage = formItem.querySelector('a');

    textMessage.innerText = message;
    formItem.className = "input-box-error";
}