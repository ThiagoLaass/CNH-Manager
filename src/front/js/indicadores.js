const token = localStorage.getItem('Authorization');

function checkAuthentication() {
    if (!token) {
        window.location.href = 'login.html';  
    }
}

checkAuthentication();

async function init() {
    const role = await fetchUserInformation();
    if (role === 'ADMIN') {
        await fetchIndicadores();
    }
}

init();

function displayUserInfo(nome, role) {
    const userInfoElement = document.getElementById('user-info');
    userInfoElement.innerHTML = `Olá ${nome}! Seja bem vindo(a) ao espaço de ${role}`;
}

async function fetchUserInformation() {
    const apiUrl = `http://127.0.0.1:8080/auth/user/${localStorage.getItem('user_id')}`;
    console.log(apiUrl)

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error('Falha na autenticação');
        }
        const data = await response.json();
        displayUserInfo(data.login, data.role);
        return data.role;
    } catch (error) {
        console.error('Erro ao buscar informações do usuário:', error);
        window.location.href = 'login.html';  
    }
}

async function fetchIndicadores() {
    console.log('Token de Autenticação:', token);
    try {
        const approvalRateResponse = await fetch('http://127.0.0.1:8080/indicators/calculateApprovalRate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!approvalRateResponse.ok) {
            throw new Error('Erro ao buscar a taxa de aprovação');
        }
        const approvalRate = await approvalRateResponse.json();
        console.log('Taxa de Aprovação:', approvalRate);
        const approvalRateData = generateFakeData(approvalRate);
        renderChart('taxaAprovacaoChart', 'Taxa de Aprovação', approvalRateData);

        const averageRatingResponse = await fetch('http://127.0.0.1:8080/indicators/avaliacoes/average', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!averageRatingResponse.ok) {
            throw new Error('Erro ao buscar a média de avaliações');
        }
        const averageRating = await averageRatingResponse.json();
        console.log('Média de Avaliações:', averageRating);
        const averageRatingData = generateFakeData(averageRating);
        renderChart('mediaAvaliacoesChart', 'Média de Avaliações', averageRatingData);

        const averageAdditionalPracticeResponse = await fetch('http://127.0.0.1:8080/indicators/calculateAverageAdditionalPractice', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!averageAdditionalPracticeResponse.ok) {
            throw new Error('Erro ao buscar a média de aulas adicionais');
        }
        const averageAdditionalPractice = await averageAdditionalPracticeResponse.json();
        console.log('Média de Aulas Adicionais:', averageAdditionalPractice);
        const averageAdditionalPracticeData = generateFakeData(averageAdditionalPractice);
        renderChart('mediaAulasAdicionaisChart', 'Média de Aulas Adicionais', averageAdditionalPracticeData);
    } catch (error) {
        console.error('Erro ao buscar indicadores:', error);
    }
}

function generateFakeData(realData) { // função dedicada para geracao de mock data para gerar dados coletados ao longo de meses de funcionamento e utilização do sistema. Necessária já que o sistema não se encontra publicado. Todavia, as funcionalidades necessárias para utilização de dados reais foram também criadas, porém comentadas.
    const fakeData = [];
    for (let i = 0; i < 5; i++) {
        fakeData.push(Math.random() * 100);
    }
    fakeData.push(realData);
    return fakeData;
}

function renderChart(canvasId, label, data) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Mês 1', 'Mês 2', 'Mês 3', 'Mês 4', 'Mês 5', 'Mês 6'],
            datasets: [{
                label: label,
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

document.getElementById("logoutBtn").addEventListener("click", function() {
    
    localStorage.removeItem('Authorization');
    localStorage.removeItem('user_id');
    window.location = 'login.html';
    
});
