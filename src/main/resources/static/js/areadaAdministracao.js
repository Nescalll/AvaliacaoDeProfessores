
document.addEventListener("DOMContentLoaded", function() {

    const professorBtn = document.getElementById('professor');
    const registroBtn = document.getElementById('registro');
    const profAcessDiv = document.getElementById('prof-acess');
    const regiAcessDiv = document.getElementById('regi-acess');
    const regiContainer = document.getElementById('registro-container');
    const notifyBtn = document.getElementById('notification');
    const notiAcessDiv = document.getElementById('noti-acess');

    //Adicionando o sistema de aparecer a aba ao clicar em REGISTRO, PROFESSOR ou NOTIFICAÇÃO.
    professorBtn.addEventListener('click', function() {
        profAcessDiv.style.display = 'flex'
    })

    profAcessDiv.addEventListener('click', function(event) {
        if(event.target === profAcessDiv) {
            profAcessDiv.style.display = 'none';
        }
    })

    registroBtn.addEventListener('click', function() {
        regiAcessDiv.style.display = 'flex'
    })

    regiAcessDiv.addEventListener('click', function(event) {
        if(event.target === regiAcessDiv) {
            regiAcessDiv.style.display = 'none';
        }
    })

    notifyBtn.addEventListener('click', function() {
        notiAcessDiv.style.display = 'flex'
        
        notifyBtn.style.backgroundColor = '#FFFEFF';
        notifyBtn.style.border = '2px solid #000000';
        const icone = notifyBtn.querySelector('i');
        icone.style.color = '#000000';
        icone.style.transform = 'scale(1.2)';
    })

    notiAcessDiv.addEventListener('click', function(event) {
        if(event.target === notiAcessDiv) {
            notiAcessDiv.style.display = 'none';

            notifyBtn.style.backgroundColor = '#000000';
            notifyBtn.style.border = '2px solid #FFEFFF';
            const icone = notifyBtn.querySelector('i');
            icone.style.color = '#FFEFFF';
            icone.style.transform = 'scale(1)';
        }
    })

    // Adicionando evento de clique para o botao de aprovar, ver, ou negar solicitação(para ajudar meu colega Joaozinho :); pode alterar, se necessário.
    document.addEventListener('click', (e) => {

        const targetEl = e.target;
        const parentEl = targetEl.closest('div');

        if(targetEl.classList.contains('aprove-form')) {
            console.log("Aprovando formulário...")
        }

        if(targetEl.classList.contains('see-form')) {
            console.log("Visualizando formulário...");
        }

        if(targetEl.classList.contains('deny-form')) {
            console.log("Negando formulário...") //Se achar que pode mudar a função deste botão, fique a vontade.
        }

        // Botao de apagar da barra de pesquisa.
        if(targetEl.classList.contains('erase-button')) {
            console.log("Apagando palavras...")
        }
    })

    function logout() {
        console.log("Realizando logout...");
        window.location.href = 'loginProfessor.html';
    }

    const logoutButton = document.getElementById("logout");
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            logout();
        })
    } else {
        console.warn("Botão de logout não encontrado!")
    }
});