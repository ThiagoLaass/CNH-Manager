const token = localStorage.getItem('Authorization');
const userId = localStorage.getItem('user_id');

async function init() {
    const role = await fetchUserInformation();
    if (role === 'ADMIN') {
        await fetchSolicitacaoInformation();
        await fetchSolicitacoesDeProva();
        await fetchProvas();
    }
}

init();

function displayUserInfo(nome, role) {
    const userInfoElement = document.getElementById('user-info');
    userInfoElement.innerHTML = `Olá ${nome}! Seja bem vindo(a) ao espaço de ${role}`;
}

async function cadastrarPagamento() {
        const tipoPagamento = document.getElementById('tipoPagamento').value;
    
        try {
            const response = await fetch('http://127.0.0.1:8080/pagamentos/cadastrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
                body: JSON.stringify({ tipoPagamento })
            });

            if (response.ok) 
                alert('Pagamento cadastrado com sucesso!');
             else 
                alert('Erro ao cadastrar pagamento');

        } catch (error) {
            console.log('Erro ao cadastrar pagamento');
        }
    }

async function showSolicitacaoLista(solicitacao, listaElement) {
    const li = document.createElement('li');
    li.classList.add('list-group-item');


    if (solicitacao.statusAprovada) {
        li.classList.add('list-group-item-success');
    } else if (!solicitacao.statusAprovada && !solicitacao.statusAberta) {
        li.classList.add('list-group-item-danger');
    }

    li.innerHTML = `
    Solicitação de ${solicitacao.user.login} <br> 
    Aula: ${solicitacao.aula.nome}. <br>
    Horas: ${solicitacao.horasSolicitadas} <br>
    Descrição: ${solicitacao.descricao} <br> 
    Status: ${solicitacao.statusAberta ? "Aguardando verificação" : (solicitacao.statusAprovada ? "Aprovada" : "Negada")} <br> 
    ${solicitacao.statusAberta ? `
        <button class="btn btn-primary" onclick="confirmaAprovaSolicitacao('${solicitacao.id}')">Aprovar</button>
        <button class="btn btn-danger" onclick="confirmaNegaSolicitacao('${solicitacao.id}')">Negar</button>
    ` : ''}
`;
    listaElement.appendChild(li);
}

async function fetchUserInformation() {
    const apiUrl = `http://127.0.0.1:8080/auth/user/${localStorage.getItem('user_id')}`;
    (apiUrl);

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        const data = await response.json();
        displayUserInfo(data.login, data.role);
        (data);
        return data.role;
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

async function showSolicitacaoProvaLista(solicitacao, listaElement) {
    const li = document.createElement('li');
    li.classList.add('list-group-item');

    if (solicitacao.statusAprovada) {
        li.classList.add('list-group-item-success');
    } else if (!solicitacao.statusAprovada && !solicitacao.statusAberta) {
        li.classList.add('list-group-item-danger');
    }

    let btnProva;
    if (solicitacao.prova.data && solicitacao.prova.horario) {
        btnProva = `
            <button class="btn btn-secondary btnEditarProva" data-prova-id="${solicitacao.prova.id}" data-bs-toggle="modal" data-bs-target="#provaModal">Editar Prova</button>
        `;
    } else {
        btnProva = `
            <button class="btn btn-primary btnCadastrarProva" data-prova-id="${solicitacao.prova.id}" data-bs-toggle="modal" data-bs-target="#provaModal">Cadastrar Prova</button>
        `;
    }

    li.innerHTML = `
    Solicitação de ${solicitacao.user.login} <br> 
    Prova: ${solicitacao.tipoProva.nome}. <br>
    ${solicitacao.statusProvaElegivel ?
            '<p class="text-success">Aluno Elegível</p>'
            : '<p class="text-warning">Aluno Não Elegível</p>'} <br>
        <button class="btn btn-primary" onclick="confirmaAprovaSolicitacaoProva('${solicitacao.id}','${solicitacao.pagamento.id}')">Aprovar Pagamento</button>
        <button class="btn btn-danger" onclick="negaAprovaSolicitacaoProva('${solicitacao.id}')">Negar Solicitação</button>
        ${btnProva}
    `;

    listaElement.appendChild(li);

    const btnProvaElement = li.querySelector(".btnCadastrarProva,.btnEditarProva");
    let provaId;
    btnProvaElement.addEventListener("click", function () {
        provaId = btnProvaElement.getAttribute("data-prova-id");
    });

    // Ouvinte de evento para salvar a prova
    document.getElementById("btnSalvarProva").addEventListener("click", function () {
        salvarProva(provaId);
    });
}

function salvarProva(provaId) {
    const provaModal = document.getElementById("provaModal");
    const dataProva = provaModal.querySelector("#dataProva").value;
    const horaProva = provaModal.querySelector("#horaProva").value;

    // PUT para atualizar a data e hora da prova
    fetch(`http://127.0.0.1:8080/provas/${provaId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            data: dataProva,
            horario: horaProva
        })
    })
        .then(response => response.json())
        .then(data => {
            fetchSolicitacoesDeProva();
            location.reload()
        })
        .catch(error => console.error('Erro:', error));
}

function confirmaAprovaSolicitacaoProva(solicitacaoId, pagamentoId) {
    if (confirm('Você tem certeza que deseja aprovar esta solicitação?')) {
        aprovaSolicitacaoProva(solicitacaoId, pagamentoId);
    }
}

async function aprovaSolicitacaoProva(solicitacaoId, pagamentoId) {

    const generalUrl = `http://127.0.0.1:8080`;

    const solicitacaoProvaUrl = `${generalUrl}/solicitacaoDeProva/${solicitacaoId}`;

    const pagamentoUrl = `${generalUrl}/pagamentos/${pagamentoId}`;

    const solicitacaoAtualizada = {
        statusAberta: false,
        statusAprovada: true
    };

    const pagamentoAtualizado = {
        statusPagamento: true
    };

    try {
        const response = await fetch(solicitacaoProvaUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(solicitacaoAtualizada)
        });

        if (response.ok) {
            alert('Solicitação de Prova aprovada com sucesso!')
        } else {
            alert('Erro ao aprovar a solicitação de prova!')
        }
    } catch (error) {
        console.error('Erro:', error);
    }

    try {
        const response2 = await fetch(pagamentoUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pagamentoAtualizado)
        });

        if (response2.ok) {
            location.reload();
            alert('Pagamento aprovado com sucesso!')
        } else {
            alert('Erro ao aprovar pagamento de prova!')
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

function negaAprovaSolicitacaoProva(solicitacaoId) {
    if (confirm('Você tem certeza que deseja nega esta solicitação?')) {
        negaSolicitacaoProva(solicitacaoId);
    }
}

async function negaSolicitacaoProva(solicitacaoId) {
    const apiUrl = `http://127.0.0.1:8080/solicitacaoDeProva/delete/${solicitacaoId}`;

    try {
        const response = await fetch(apiUrl, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            location.reload();
            alert('Solicitação negada com sucesso!')
        } else {
            alert('Erro ao negar a solicitação!')
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

async function fetchSolicitacoesDeProva() {
    try {
        const response = await fetch('http://127.0.0.1:8080/solicitacaoDeProva/all', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        const data = await response.json();
        console.log(data);
        const ProvaSolicitacoesList = document.getElementById('solicitacoesde-prova-list');
        ProvaSolicitacoesList.innerHTML = '';

        if (data.length == 0) {
            ProvaSolicitacoesList.innerHTML += `<p>Não há nenhuma solicitação de prova no momento</p>`
        }

        data.forEach(solicitacao => {
            if (solicitacao.statusAberta) {
                showSolicitacaoProvaLista(solicitacao, ProvaSolicitacoesList);
            }
        });
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }

}

async function fetchSolicitacaoInformation() {
    try {
        const response = await fetch('http://127.0.0.1:8080/solicitacoes/all', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();
        const solicitacoesAbertasList = document.getElementById('solicitacoesList');
        const solicitacoesFechadasList = document.getElementById('solicitacoesFechadasList');
        solicitacoesAbertasList.innerHTML = '';
        solicitacoesFechadasList.innerHTML = '';

        data.forEach(solicitacao => {
            if (solicitacao.statusAberta) {
                showSolicitacaoLista(solicitacao, solicitacoesAbertasList);
            } else {
                showSolicitacaoLista(solicitacao, solicitacoesFechadasList);
            }
        });
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

async function aprovaSolicitacao(solicitacaoId) {

    const apiUrl = `http://127.0.0.1:8080/solicitacoes/${solicitacaoId}`;

    const solicitacaoAtualizada = {
        statusAberta: false,
        statusAprovada: true
    };

    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(solicitacaoAtualizada)
        });

        if (response.ok) {
            location.reload();
            alert('Solicitação aprovada com sucesso!')
        } else {
            alert('Erro ao aprovar a solicitação!')
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

async function negaSolicitacao(solicitacaoId) {
    const apiUrl = `http://127.0.0.1:8080/solicitacaoDeProva/${solicitacaoId}`;

    const solicitacaoAtualizada = {
        statusAberta: false,
        statusAprovada: false
    };

    try {
        const response = await fetch(apiUrl, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(solicitacaoAtualizada)
        });

        if (response.ok) {
            location.reload();
            alert('Solicitação negada com sucesso!')
        } else {
            alert('Erro ao negar a solicitação!', error)
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

function confirmaAprovaSolicitacao(solicitacaoId) {
    if (confirm('Você tem certeza que deseja aprovar esta solicitação?')) {
        aprovaSolicitacao(solicitacaoId);
    }
}

function confirmaNegaSolicitacao(solicitacaoId) {
    if (confirm('Você tem certeza que deseja negar esta solicitação?')) {
        negaSolicitacao(solicitacaoId);
    }
}

document.getElementById("logoutBtn").addEventListener("click", function () {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('user_id');
    window.location = 'login.html';
});

async function fetchProvas() {

    try {
        const response = await fetch('http://127.0.0.1:8080/provas/inconcluidas', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Erro ao buscar os dados');
        }

        const data = await response.json();
        console.log(data);

        const provasList = document.getElementById('provasList');
        provasList.innerHTML = '';

        if (data.length === 0) {
            provasList.innerHTML += `<p>Não há nenhuma solicitação de prova no momento</p>`;
        }

        data.forEach(prova => {
            if (!prova.statusAprovada) {
                console.log(prova);
                showProvaLista(prova, provasList);
            }
        });

    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }

}

async function showProvaLista(prova, listaElement) {
    const data = prova.data;

    const dataFormatada = `${data[2]}/${data[1]}/${data[0]}`;

    const dataToCompare = new Date(data[0], data[1], data[2]);
    const today = new Date();

    const li = document.createElement('li');
    li.classList.add('list-group-item');
    li.innerHTML = `Prova do usuário ${prova.user.login} <br>
                    Prova: ${prova.tipoProva.nome}. <br>
                    ${dataToCompare > today ?
            `<p class="text-success">Data: ${dataFormatada}</p>`
            : `<p>Data: <span class="text-warning">${dataFormatada}</span></p>`}
                    <button class="btn btn-primary" onclick="confirmaAprovarAluno('${prova.id}')">Aprovar Aluno</button>
                    <button class="btn btn-danger" onclick="confirmaReprovacaoAluno('${prova.id}')">Reprovar Aluno</button>`;

    console.log("id da prova: ", prova.id);

    listaElement.appendChild(li);
}

function confirmaAprovarAluno(provaId) {
    console.log("entrou");
    if (confirm('Você tem certeza que deseja aprovar o aluno?')) {
        aprovaAluno(provaId);
    }
}

function confirmaReprovacaoAluno(provaId){
    if (confirm('Você tem certeza que deseja reprovar o aluno?')) {
        reprovaAluno(provaId);
    }
}

async function aprovaAluno(provaId) {

    const generalUrl = `http://127.0.0.1:8080`;

    const provaUrl = `${generalUrl}/provas/${provaId}`;

    const provaAtualizada = {
        statusAprovado: true
    };

    try {
        const response = await fetch(provaUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(provaAtualizada)
        });

        if (response.ok) {
            alert('Prova prática do aluno aprovada com sucesso!')
            location.reload();
        } else {
            alert('Erro ao aprovar a prova prática do aluno!')
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

async function reprovaAluno(provaId) {

    const generalUrl = `http://127.0.0.1:8080`;

    const provaUrl = `${generalUrl}/provas/${provaId}`;

    const provaAtualizada = {
        statusAprovado: false
    };

    try {
        const response = await fetch(provaUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(provaAtualizada)
        });

        if (response.ok) {
            alert('Prova prática do aluno reprovada!')
            location.reload();
        } else {
            alert('Erro ao reprovar a prova prática do aluno!')
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}


document.addEventListener('DOMContentLoaded', () => {
    const estatisticaButton = document.querySelector('[data-bs-target="#estatisticaModal"]');
    estatisticaButton.addEventListener('click', fetchApprovalRate);
});

document.addEventListener('DOMContentLoaded', () => {
    const estatisticaButton = document.querySelector('[data-bs-target="#estatisticaModal"]');
    estatisticaButton.addEventListener('click', fetchAverageRating);
});
