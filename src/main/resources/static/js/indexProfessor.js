document.addEventListener('DOMContentLoaded', function() {
    // Função para ler um cookie específico
    function getCookie(nome) {
        const cookies = document.cookie.split('; ');
        for (let cookie of cookies) {
            const [key, value] = cookie.split('=');
            if (key === nome) {
                return decodeURIComponent(value);
            }
        }
        return null;
    }
    
    // Função para apagar todos os cookies
    function deleteAllCookies() {
        const cookies = document.cookie.split('; ');
        
        for (let cookie of cookies) {
            const [nome] = cookie.split('=');
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/; domain=${window.location.hostname};`;
        }
        
        const cookiesToDelete = ['auth_token', 'user_name', 'user_email', 'user_turma', 'professor_email'];
        cookiesToDelete.forEach(nome => {
            document.cookie = `${nome}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
        });
        
        console.log('Todos os cookies foram apagados');
    }
    
    // Função para buscar informações do professor (com token)
    async function buscarInfoProfessor(email, token) {
        try {
            const response = await fetch(`http://localhost:8082/professor/buscar/${email}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            
            if (response.status === 401 || response.status === 403) {
                console.log('Token inválido ou expirado');
                logout();
                return null;
            }
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            const professor = await response.json();
            return professor;
        } catch (error) {
            console.error('Erro ao buscar professor:', error);
            return null;
        }
    }

    
    
    // Função para buscar média do professor (com token)
    async function buscarMediaProfessor(id, token) {
        try {
            const response = await fetch(`http://localhost:8082/professor/media/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            
            if (response.status === 401 || response.status === 403) {
                console.log('Token inválido ou expirado');
                logout();
                return null;
            }
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            const media = await response.json();
            return media;
        } catch (error) {
            console.error('Erro ao buscar média:', error);
            return null;
        }
    }
    
    async function buscarComentariosProfessor(id, token) {
        try {
            const response = await fetch(`http://localhost:8082/professor/comentario/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            
            if (response.status === 401 || response.status === 403) {
                console.log('Token inválido ou expirado');
                logout();
                return null;
            }
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            const media = await response.json();
            return media;
        } catch (error) {
            console.error('Erro ao buscar média:', error);
            return null;
        }
    }

    async function reportarComentario(id, token) {
        try {
            console.log('Reportando comentário com ID:', id);
            const response = await fetch(`http://localhost:8082/professor/comentario/${id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            
            if (response.status === 401 || response.status === 403) {
                console.log('Token inválido ou expirado');
                return null;
            }
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            if (response.ok) {
                alert('Comentário reportado com sucesso!');
            }
        } catch (error) {
            console.error('Erro ao buscar média:', error);
            return null;
        }
    }

    // Função para verificar token no backend (opcional)
    async function verificarToken(token) {
        try {
            const response = await fetch('http://localhost:8082/auth/verificar', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            
            return response.ok;
        } catch (error) {
            console.error('Erro ao verificar token:', error);
            return false;
        }
    }
    
    // Função para exibir os dados na tela
    function exibirDadosProfessor(professor, nota, comentario) {
        // Exibe o nome do professor
        const nomeElement = document.getElementById('nomeProfessor');
        const notaElement = document.getElementById('notaValor');
        const comentarioElement = document.getElementById('comentarios');

        if (nomeElement && professor.nome) {
            nomeElement.textContent = professor.nome;
        }
         else if (notaElement) {
            notaElement.textContent = 'Aguardando avaliações';
        }

        // Exibe a nota do professor
        if (notaElement && nota !== null) {
            notaElement.textContent = nota.toFixed(2);
        } else if (notaElement) {
            notaElement.textContent = 'Aguardando avaliações';
        }   

        if (comentarioElement && comentario !== null) {
            for (let [chave, valor] of Object.entries(comentario)) {
                console.log('Comentário:', valor);
                const comentarioDiv = document.createElement('div');
                comentarioDiv.classList.add('comentario');
                comentarioDiv.innerHTML = `<p>${valor}</p>
                <button class="btn-reportar" id="${chave}">Reportar</button>`;
                comentarioElement.appendChild(comentarioDiv);
            }
            adicionarEventoAoBotaoReportar();
        } else if (comentarioElement) {
            comentarioElement.innerHTML = '<p>Nenhum comentário disponível</p>';
        }
    }
    
    // Função principal
    async function carregarDadosProfessor() {
        // Pega o token e email dos cookies
        const token = getCookie('auth_token');
        let professorEmail = getCookie('professor_email') || getCookie('user_email');
        
        if (!token) {
            console.log('Token não encontrado');
            window.location.href = 'loginProfessor.html';
            return;
        }
        
        if (!professorEmail) {
            console.log('Email do professor não encontrado nos cookies');
            window.location.href = 'loginAluno.html';
            return;
        }
        
        console.log('Buscando dados para o professor:', professorEmail);
        
        // Buscar informações do professor
        const professor = await buscarInfoProfessor(professorEmail, token);
        
        if (!professor) {
            console.log('Professor não encontrado ou token inválido');
            return;
        }
        
        console.log('Professor encontrado:', professor);
        
        // Buscar média do professor
        const media = await buscarMediaProfessor(professor.id, token);
        console.log('Média do professor:', media);

        const comentarios = await buscarComentariosProfessor(professor.id, token);
        console.log('Comentários do professor:', comentarios);

        // Exibir dados na tela
        exibirDadosProfessor(professor, media, comentarios);
    }

    function adicionarEventoAoBotaoReportar() {
        const comentariosContainer = document.querySelectorAll('.btn-reportar');
        comentariosContainer.forEach(button => {
            button.addEventListener('click', function() {
                const comentarioId = this.id;
                reportarComentario(comentarioId, token);
            });
        });
    }
    
    // Função para fazer logout
    function logout() {
        console.log('Realizando logout...');
        deleteAllCookies();
        window.location.href = 'loginProfessor.html';
    }
    
    // Verifica autenticação e carrega dados
    const token = getCookie('auth_token');
    if (!token) {
        console.log('Token não encontrado. Redirecionando para login...');
        window.location.href = 'loginProfessor.html';
    } else {
        console.log('Usuário autenticado com sucesso!');
        carregarDadosProfessor();
    }
    
    // Adiciona evento ao botão de deslogar
    const logoutButton = document.querySelector('.btn-logout');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            logout();
        });
    } else {
        console.warn('Botão de logout não encontrado');
    }
});