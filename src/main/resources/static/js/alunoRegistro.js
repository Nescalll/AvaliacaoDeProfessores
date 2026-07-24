document.addEventListener('DOMContentLoaded', function(){
    
    const form = document.getElementById('form');
    const nome = document.getElementById('nome');
    const email = document.getElementById('email');
    const senha = document.getElementById('senha');
    const turma = document.getElementById('turma');
    const botao = document.getElementById('button');

    async function registrar(dados) {
        console.log('Enviando dados...');
        try{
            const response = await fetch(`http://localhost:8082/auth/register/aluno`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dados)
            });
            if (response.ok) {
                console.log('Registro com sucesso')
                window.location.href = '../templates/loginAluno.html';
            }
        }catch(error){
            console.error("Erro na hora de enviar:",error)
        }
    }

    

    botao.addEventListener('click', function(){
        event.preventDefault();

        checkForm();

    })
})

function checkInputUsername() {
    const nomeValue = nome.value;

    if (nomeValue === "") {
        errorInput(nome, "Preencha este campo!");
    } else {
        const formItem = nome.parentElement;
        formItem.className = "input-box";
    }

}

function checkInputEmail() {
    const emailValue = email.value;

    if (emailValue === "") {
        errorInput(email, "Preencha este campo!");
    } else {
        const formItem = email.parentElement;
        formItem.className = "input-box";
    }
}

function checkInputPassword() {
    const senhaValue = senha.value;

    if (senhaValue === "") {
        errorInput(senha, "Preencha este campo!");
    } else if(senhaValue.length < 8) {
        errorInput(senha, "No mínimo 8 caracteres!");
    } else {
        const formItem = senha.parentElement;
        formItem.className = "input-box";
    }
}

function checkForm() {
    checkInputUsername();
    checkInputEmail();
    checkInputPassword();

    const formItems = form.querySelectorAll(".input-box-error");

    const isValid = [...formItems].every( (item) => {
        return item.className === "input-box";
    })

    if (isValid) {
        alert("Formulário enviado com sucesso!");

        const dadosDeRegistro = {
                nome: nome.value,
                email: email.value,
                senha: senha.value,
                turma: turma.value
            }

            registrar(dadosDeRegistro);
    }
}

function errorInput(input, message) {
    const formItem = input.parentElement;
    const textMessage = formItem.querySelector("a");

    textMessage.innerText = message;

    formItem.className = "input-box-error";
}
