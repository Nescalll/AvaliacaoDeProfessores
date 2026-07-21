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

    async function buscarModaDeAdjetivo(id, token) {
        try{
            const response = await fetch(`http://localhost:8082/professor/adjetivo/${id}`, {
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

            const adjetivo = await response.json();
            return adjetivo;
        } catch (error) {
            console.error('Erro ao buscar adjetivo:', error);
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
    function exibirDadosProfessor(professor, media, adjetivo) {
        // Exibe o nome do professor
        const nomeElement = document.querySelector('#nomeProfessor');
        if (nomeElement && professor.nome) {
            nomeElement.textContent = professor.nome;
        }
        
     // Exibe o que precisa melhorar
        const melhorarElement = document.querySelector('.melhorar');
        if (melhorarElement && adjetivo !== null) {
            melhorarElement.textContent = adjetivo;
        }
        
        // Exibe a nota total
        const notaElement = document.querySelector('#notaValor');
        if (notaElement && media !== null) {
            notaElement.textContent = `${media}/10`;
        } else if (notaElement) {
            notaElement.textContent = 'Aguardando avaliações';
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

        // Buscar adjetivo do professor
        const adjetivo = await buscarModaDeAdjetivo(professor.id, token);
        console.log('Adjetivo do professor:', adjetivo);
        
        // Exibir dados na tela
        exibirDadosProfessor(professor, media, adjetivo);
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

    const data = [
            { label: 'Otimo', value: 550, color: '#4CAF50' },
            { label: 'Bom', value: 25, color: '#2196F3' },
            { label: 'Regular', value: 20, color: '#FF9800' },
            { label: 'Ruim', value: 15, color: '#9C27B0' },
            { label: 'Péssimo', value: 10, color: '#f44336' }
        ];
        
        function drawPieChart() {
            const canvas = document.getElementById('pieChart');
            const ctx = canvas.getContext('2d');
            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const radius = 120;
            let startAngle = 0;
            
            // Calcular total
            const total = data.reduce((sum, item) => sum + item.value, 0);
            
            // Desenhar pizza
            data.forEach(item => {
                const sliceAngle = (item.value / total) * 2 * Math.PI;
                const endAngle = startAngle + sliceAngle;
                
                // Desenhar fatia
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.arc(centerX, centerY, radius, startAngle, endAngle);
                ctx.closePath();
                ctx.fillStyle = item.color;
                ctx.fill();
                ctx.strokeStyle = 'white';
                ctx.lineWidth = 2;
                ctx.stroke();
                
                // Desenhar porcentagem no centro da fatia
                const midAngle = startAngle + sliceAngle / 2;
                const percentX = centerX + (radius * 0.65) * Math.cos(midAngle);
                const percentY = centerY + (radius * 0.65) * Math.sin(midAngle);
                
                ctx.fillStyle = 'white';
                ctx.font = 'bold 14px Arial';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                const percent = Math.round((item.value / total) * 100);
                ctx.fillText( item.value , percentX, percentY);
                
                startAngle = endAngle;
            });
            
            // Gerar legenda
            const legendDiv = document.getElementById('legend');
            data.forEach(item => {
                const legendItem = document.createElement('div');
                legendItem.className = 'legend-item';
                legendItem.innerHTML = `
                    <div class="legend-color" style="background: ${item.color}"></div>
                    <span>${item.label}</span>
                `;
                legendDiv.appendChild(legendItem);
            });
        }
        
        drawPieChart();
});