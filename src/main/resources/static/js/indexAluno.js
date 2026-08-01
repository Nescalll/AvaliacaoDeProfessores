// arquivo: indexAluno.js

document.addEventListener('DOMContentLoaded', function() {

    async function pegarProfessores(email, token) {
        try {
            const response = await fetch(`http://localhost:8082/aluno/aulas/${email}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 401 || response.status === 403) {
                console.log('Token inválido ou expirado');
                console.log(token);
                logout();
                return null;
            }

            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }

            const professoresASeremAvaliados = await response.json();
            return professoresASeremAvaliados;
        } catch (error) {
            console.error('Dados não carregados', error);
            return null;
        }
    }

    async function enviarAvaliacao(dados, token){
        try{
            const response = await fetch(`http://localhost:8082/aluno/aula`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(dados)
            });

            if(response.ok){
            location.reload();
        }
        } catch (error){
            console.log(error);
        }
    }
    function getCookie(nome) {
        const cookies = document.cookie.split('; ');
        for (let cookie of cookies) {
            const [key, value] = cookie.split('=');
            if (key === nome) {
                return value;
            }
        }
        return null;
    }

    function deleteAllCookies() {
        const cookies = document.cookie.split('; ');
        for (let cookie of cookies) {
            const [nome] = cookie.split('=');
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/; domain=${window.location.hostname};`;
        }

        const cookiesToDelete = ['auth_token', 'user_name', 'user_email', 'user_turma'];
        cookiesToDelete.forEach(nome => {
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/; domain=${window.location.hostname};`;
        });

        console.log('Todos os cookies foram apagados');
    }

    function logout() {
        console.log('Realizando logout...');
        deleteAllCookies();
        window.location.href = 'loginAluno.html';
    }

    async function receberProfessores(token) {
        const email = getCookie('user_email');
        const professoresParaAvaliar = await pegarProfessores(email, token);
        
        if (!professoresParaAvaliar) {
            return [];
        }
        
        // Transforma para apenas nome e id
        const resultado = professoresParaAvaliar.map(item => ({
            nome: item.nome,
            id: item.id
        }));
        
        return resultado;
    }
    

    async function carregarProfessores(token) {
        try {
            const dadosDeProfessores = await receberProfessores(token);
            listaProfessores = dadosDeProfessores; // Armazena globalmente
            
            const divProfessores = document.getElementById('professores');
            
            //Adicionar limite de professores
            

            if (!dadosDeProfessores || dadosDeProfessores.length === 0) {
                divProfessores.innerHTML = `
                    <div class="professor">
                        <p>Nenhum professor encontrado</p>
                    </div>
                `;
                return;
            }
            
            //Paginação: 
            
            if(dadosDeProfessores.length > 8){
                paginar(1);
                console.log("Professores feitos");
            } else {
                console.log("Entrou");
            dadosDeProfessores.forEach(({ nome, id }) => {
                divProfessores.innerHTML += `
                    <div class="professor" data-id="${id}">
                        <p>${nome}</p>
                    </div>
                `;
            document.getElementById('paginacao').innerHTML = " ";
            adicionarEventosAosProfessores(); 
            });
        }

        
        return listaProfessores;  

        } catch (erro) {
            console.error('Erro ao carregar professores:', erro);
            const divProfessores = document.getElementById('professores');
            divProfessores.innerHTML = `
                <div class="professor">
                    <p style="color: red;">Erro ao carregar professores</p>
                </div>
            `;
        }
    }

    function paginar(paginaAtual) {
        const divProfessores = document.getElementById('allProfessor');
        const numeroDaPagina = document.getElementById('numeroDaPagina');

        
        pagina = (paginaAtual - 1) * 8;
        divProfessores.innerHTML = "";
        for(let i = pagina; i < pagina + 8; i ++){
                let professor = listaProfessores[i]
                divProfessores.innerHTML += `
                    <div class="professor" data-id="${professor.id}">
                        <p>${professor.nome}</p>
                    </div>`;
            }
            numeroDaPagina.innerHTML = " "
            numeroDaPagina.innerHTML = `${paginaAtual}`
            adicionarEventosAosProfessores(); 
    }

    // Função para adicionar eventos aos professores
    function adicionarEventosAosProfessores() {
        const professores = document.querySelectorAll('.professor');
        console.log('Professores encontrados:', professores.length);

        professores.forEach((professor) => {
            professor.addEventListener('click', function() {
                const temProfessorSelecionado = Array.from(professores).some(p => p.classList.contains('selecionadoProfessor'));
                if (temProfessorSelecionado) {
                    const professorSelecionado = document.querySelector('.selecionadoProfessor');
                    professorSelecionado.style.color = "black";
                    professorSelecionado.style.backgroundColor = "white";
                    professorSelecionado.classList.remove('selecionadoProfessor');
                }

                professor.classList.add('selecionadoProfessor');
                professor.style.color = "white";
                professor.style.backgroundColor = "black";
            });
        });
    }

    let listaProfessores = [];


    const token = getCookie('auth_token');
    if (!token) {
        console.log('Token não encontrado. Redirecionando para login...');
        window.location.href = 'loginAluno.html';
    } else {
        console.log('Usuário autenticado com sucesso!');
        carregarProfessores(token);
        let professores = receberProfessores(token);
    }

    const logoutButton = document.querySelector('button[id="logout"]');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            logout();
        });
    } else {
        console.warn('Botão de logout não encontrado');
    }

    const botaoProx = document.querySelector('#proximo');
    const botaoAnterior = document.querySelector('#anterior');


    botaoProx.addEventListener('click', function(){
        const token = getCookie('auth_token');
        const paginaAtual = document.getElementById("numeroDaPagina");
        let valor = Number(paginaAtual.textContent);
        let quantProfessores = professores.length / 8;
        if(valor  < Math.trunc(listaProfessores.length / 8)){
            valor ++;
            paginar(valor);
        }
    });

    botaoAnterior.addEventListener('click', function(){
        const paginaAtual = document.getElementById("numeroDaPagina");
        let valor = Number(paginaAtual.textContent);
        console.log(valor)
        if(valor > 1){
            valor --;
            paginar(valor);
        }
    });


    const notas = document.querySelectorAll('.nota');


    notas.forEach((nota, index) => {
        nota.addEventListener('click', function() {
            const temNotaSelecionada = Array.from(notas).some(n => n.classList.contains('selecionadaNota'));
            if (temNotaSelecionada) {
                const indexNotaSelecionada = Array.from(notas).findIndex(n => n.classList.contains('selecionadaNota'));
                const notaSelecionada = document.querySelector('.selecionadaNota');
                notaSelecionada.classList.remove('selecionadaNota');
                notaSelecionada.classList.add(`nota-${indexNotaSelecionada}`);
            }
            
            nota.classList.remove(`nota-${index}`);
            nota.classList.add('selecionadaNota');
        });
    });



    const botao = document.querySelector('#btn-avaliar');

    botao.addEventListener('click', function() {
        const professores = document.querySelectorAll('.professor');
        const comentario = document.querySelector('#comentarioInput');
        const nota = document.querySelectorAll('.nota')
        let temNotaSelecionada = Array.from(nota).some(n => n.classList.contains('selecionadaNota'));
        let temProfessorSelecionado = Array.from(professores).some(p => p.classList.contains('selecionadoProfessor'));

        
        if (!temNotaSelecionada) {
            window.alert("Nota não foi selecionada");
        }  else if (!temProfessorSelecionado) {
            window.alert("Professor não foi selecionado");
        } else {
            const notaSelecionada = Array.from(notas).findIndex(n => n.classList.contains('selecionadaNota'));

            const professorSelecionado = document.querySelector('.selecionadoProfessor');
            const professorId = professorSelecionado ? professorSelecionado.dataset.id : null;
            
            
            console.log("Tudo concluído!");
            console.log(`Professor ID: ${professorId}`);
            console.log(`Nota: ${notaSelecionada + 1}`);
            console.log(`Comentario: ${comentario.value}`);
            
            const dadosAvaliacao = {
                id_professor: professorId,
                email: getCookie('user_email'),
                nota: notaSelecionada + 1,
                comentario: comentario.value
            };
            enviarAvaliacao(dadosAvaliacao, token);
            console.log('Dados da avaliação:', dadosAvaliacao);
            
           
        }
    });
});