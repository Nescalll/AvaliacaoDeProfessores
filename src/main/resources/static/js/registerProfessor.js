document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('form');
    const username = document.getElementById('username');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const subject = document.getElementById('subject');
    const turmas = document.querySelectorAll('input[type = "checkbox"]');
    const button = document.getElementById('submit');
    const updMessage = document.getElementById('upd-message');
    const profileButton = document.getElementById('edit-profile');

    
    async function registrar(dados) {
         console.log('Enviando dados...');
        try{
            const response = await fetch(`http://localhost:8082/auth/register/professor`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dados)
            });
            if (response.ok) {
                console.log('Registro com sucesso')
                window.location.href = '../templates/loginProfessor.html';
            }
        }catch(error){
            console.error("Erro na hora de enviar:",error)
        }
    }

    profileButton.addEventListener('click', function() {
        updMessage.style.visibility = 'visible';

        setTimeout(function() {
            updMessage.style.visibility = 'hidden';
        }, 3000);
    })

    button.addEventListener('click', function() {
        event.preventDefault();
        
        checkForm();

    })

})

function getSelectedValues(turmas) {
    const selected = [];
    turmas.forEach(checkbox => {
        if (checkbox.checked) {
            selected.push(checkbox.value);
        }
    });
    return selected;
}


function checkInputUsername() {
    const usernameValue = username.value;

    if (usernameValue === "") {
        errorInput(username, "Preencha este campo!");
    } else {
        const formItem = username.parentElement;
        formItem.className = "input-box"
    }

}

function checkInputEmail() {
    const emailValue = email.value;

    if (emailValue === "") {
        errorInput(email, "Preencha este campo!");
    } else {
        const formItem = email.parentElement;
        formItem.className = "input-box"
    }
}

function checkInputPassword() {
    const passwordValue = password.value;

    if (passwordValue === "") {
        errorInput(password, "Preencha este campo!");
    } else if (passwordValue.length < 8) {
        errorInput(password, "No mínimo 8 caracteres!");
    } else {
        const formItem = password.parentElement;
        formItem.className = "input-box"
    }
}

function checkInputSubject() {
    const subjectValue = subject.value;

    if (subjectValue === "") {
        errorInput(subject, "Preencha este campo!")
    } else {
        const formItem = subject.parentElement;
        formItem.className = "input-box"
    }
}

function checkForm() {
    checkInputUsername();
    checkInputEmail();
    checkInputPassword();
    checkInputSubject();

    const formItems = form.querySelectorAll(".input-box-error");

    const isValid = [...formItems].every((item) => {
        return item.className === "input-box";
    })

    if (isValid) {
        const valoresSelecionados = getSelectedValues(turmas);

        alert("Formulário enviado com sucesso!");

        const RegisterData = {
            nome: username.value,
            email: email.value,
            senha: password.value,
            materia: subject.value,
            turmas: valoresSelecionados
        }

        registrar(RegisterData);


    }
}

function errorInput(input, message) {
    const formItem = input.parentElement;
    const textMessage = formItem.querySelector("a");

    textMessage.innerText = message;

    formItem.className = "input-box-error";
}
