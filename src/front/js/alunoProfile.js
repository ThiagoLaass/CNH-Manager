const token = localStorage.getItem('Authorization');
const userId = localStorage.getItem('user_id');

fetchUserInformation().then(role => {
    if (role === 'USER') {
        fetchSolicitacoesOfUser();
    }
});

function displayUserInfo(nome, role) {
    const userInfoElement = document.getElementById('user-info');
    userInfoElement.innerHTML = `Olá ${nome}! Seja bem vindo(a) ao espaço de ${role}`;
}

async function fetchUserInformation() {
    const apiUrl = `http://127.0.0.1:8080/auth/user/${localStorage.getItem('user_id')}`;
    console.log(apiUrl);

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        const data = await response.json();
        fetchCargaHoraria(data.cnhAprovada);
        verifyCnhApprovedUser(data.cnhAprovada);
        displayUserInfo(data.login, data.role);
        return data.role;
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

function verifyCnhApprovedUser(cnhAprovada) {
    if (cnhAprovada) {
        document.getElementById('solicitarPresencaBtn').disabled = true;
        document.getElementById('solicitarProvaBtn').disabled = true;
        document.getElementById('solicitarAulaPraticaBtn').disabled = true;
        document.getElementById('solicitarAulasAdicionaisBtn').disabled = true;
        document.getElementById('aguardandoAprovacaoBtn').disabled = true;
        document.getElementById('myHorariosBtn').disabled = true;
    }
}

async function fetchProvasOfUser() {
    const apiUrl = `http://127.0.0.1:8080/provas/all/${localStorage.getItem('user_id')}`;

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Erro ao buscar os dados.');
        }

        const data = await response.json();
        const provasList = document.querySelector('#provasModalBody');

        if (!provasList) {
            console.error('Elemento da lista não encontrado.');
            return;
        }

        provasList.innerHTML = '';

        data.forEach(prova => {
            showProvasOfUser(prova, provasList);
        });

        const hasProvaTeoricaNaoAprovada = data.some(prova =>
            prova.tipoProva.nome === 'Teórica' && prova.statusAprovado === false
        );

        const hasNoProvaTeorica = !data.some(prova => prova.tipoProva.nome === 'Teórica');

        const tipoProvaSelect = document.querySelector('#tipoProva');
        if (hasProvaTeoricaNaoAprovada || hasNoProvaTeorica) {
            tipoProvaSelect.querySelector('option[value="2"]').style.display = 'none';
        } else {
            tipoProvaSelect.querySelector('option[value="2"]').style.display = 'block';
        }

    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

async function fetchSolicitacoesOfUser() {
    const apiUrl = `http://127.0.0.1:8080/solicitacoes/all/${localStorage.getItem('user_id')}`;
    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();
        const solicitacoesList = document.querySelector('#solicitacoesModal .modal-body ul');

        if (!solicitacoesList) {
            console.error('Elemento da lista não encontrado.');
            return;
        }

        solicitacoesList.innerHTML = '';

        data.forEach(solicitacao => {
            showSolicitacoesOfUser(solicitacao, solicitacoesList);
        });
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

async function fetchPagamentosOfUser() {
    const apiUrl = `http://127.0.0.1:8080/pagamentos/user/${localStorage.getItem('user_id')}`;
    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();
        const pagamentosList = document.querySelector('#verPagamentosModal .modal-body ul');

        if (!pagamentosList) {
            console.error('Elemento da lista não encontrado.');
            return;
        }

        pagamentosList.innerHTML = '';

        data.forEach(pagamento => {
            showPagamentosOfUser(pagamento, pagamentosList);
        });
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

function getStatusColor(status) {
    if (status === 'Aprovado') return 'green';
}

function getStatusText(statusPagamento) {
    if (statusPagamento === true) return 'Aprovado';
}

async function showPagamentosOfUser(pagamento, listaElement) {
    const li = document.createElement('li');
    li.classList.add('list-group-item');

    const dataFormatada = formatarData(pagamento.data);
    const horaFormatada = formatarHora(pagamento.horario);

    let statusHtml = '';
    if (pagamento.statusPagamento === true) { // Verifica se o pagamento está aprovado
        const statusText = getStatusText(pagamento.statusPagamento);
        const statusColor = getStatusColor(statusText);
        statusHtml = `<span style="color: ${statusColor};">Status: ${statusText}</span> <br>`;
    }

    li.innerHTML = `
        Pagamento referente a: ${pagamento.tipoPagamento.nome} <br> 
        Preço: ${pagamento.tipoPagamento.preco} <br> 
        Data e Hora de emissão: ${dataFormatada}, ${horaFormatada} <br>
        ${statusHtml}
    `;
    listaElement.appendChild(li);
}


async function showSolicitacoesOfUser(solicitacao, listaElement) {
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
    `;
    listaElement.appendChild(li);
}

async function cadastraPresenca() {
    const descricao = document.getElementById('descricao').value;
    const horasSolicitadas = document.getElementById('horas').value;
    const tipoAula = document.getElementById('tipoAula').value;
    let aulaId;

    if (tipoAula === 'pratica') {
        aulaId = '0';
    } else if (tipoAula === 'teorica') {
        const categoriaAula = document.getElementById('aulaTeorica').value;
        switch (categoriaAula) {
            case 'legislacao':
                aulaId = '1';
                break;
            case 'direcao-defensiva':
                aulaId = '2';
                break;
            case 'primeiros-socorros':
                aulaId = '3';
                break;
            case 'meio-ambiente':
                aulaId = '4';
                break;
            case 'mecanica':
                aulaId = '5';
                break;
            default:
                aulaId = null;
        }
    }

    const response = await fetch('http://127.0.0.1:8080/solicitacoes/cadastrar', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ descricao, horasSolicitadas, aulaId, statusAberta: true, statusAprovado: false })
    });

    if (response.ok) {
        location.reload();
        alert('Requerimento enviado com sucesso!');
        document.getElementById('presencaForm').reset();
        bootstrap.Modal.getInstance(document.getElementById('requerimentoModal')).hide();
    } else {
        alert('ERRO: ' + response.status);
    }
}

document.getElementById('solicitacaoProvaForm').addEventListener('submit', async function (event) {
    event.preventDefault();
    await cadastraSolicitacaoProva();
});


async function cadastraSolicitacaoProva() {
    const descricao = document.getElementById('descricaoSolicitacaoProva').value;
    const idTipoProva = document.getElementById('tipoProva').value;

    const solicitacaoProvaData = {
        "idTipoProva": idTipoProva,
        "descricao": descricao
    }

    const response = await fetch('http://127.0.0.1:8080/solicitacaoDeProva/cadastrar', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(solicitacaoProvaData)
    });

    if (response.ok) {
        location.reload();
        alert('Requerimento enviado com sucesso!');
        document.getElementById('presencaForm').reset();
        bootstrap.Modal.getInstance(document.getElementById('requerimentoModal')).hide();
    } else {
        alert('ERRO: ' + response.status);
    }
}

async function fetchSolicitaProvaOfUser() {
    const apiUrl = `http://127.0.0.1:8080/solicitacaoDeProva/all/${localStorage.getItem('user_id')}`;
    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();
        const solicitacoesList = document.querySelector('#solicitacoesProvaModal .modal-body');

        if (!solicitacoesList) {
            console.error('Elemento da lista não encontrado.');
            return;
        }

        solicitacoesList.innerHTML = '';

        data.forEach(solicitacao => {
            showSolicitaProvaOfUser(solicitacao, solicitacoesList);
        });
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

async function showSolicitaProvaOfUser(solicitacao, listaElement) {
    const card = document.createElement('div');
    card.classList.add('card', 'mb-3');
    card.innerHTML = `
        <div class="card-body">
            <h5 class="card-title">Solicitação de ${solicitacao.tipoProva.nome}</h5>
            <p class="card-text"><strong>Descrição:</strong> ${solicitacao.descricao}</p>
            <p class="card-text"><strong>Status:</strong> ${solicitacao.statusAberta ? "Aguardando verificação" : (solicitacao.statusAprovada ? "Aprovada" : "Negada")}</p>
        </div>
    `;
    listaElement.appendChild(card);
}

document.getElementById("logoutBtn").addEventListener("click", function () {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('user_id');
    window.location = 'login.html';
});

function formatarHora(hora) {
    const [horaPart, minutoPart] = hora.split(':').map(Number);
    const horaFormatada = String(horaPart).padStart(2, '0');
    const minutoFormatado = String(minutoPart).padStart(2, '0');
    return `${horaFormatada}:${minutoFormatado}`;
}

function formatarData(data) {
    const [mes, dia] = data.split('-').map(Number);
    const mesFormatado = String(mes).padStart(2, '0');
    const diaFormatado = String(dia).padStart(2, '0');
    return `${diaFormatado}/${mesFormatado}`;
}

function showProvasOfUser(prova, listaElement) {
    const card = document.createElement('div');
    card.classList.add('card', 'mb-3');
    card.innerHTML = `
        <div class="card-body">
            <h5 class="card-title">Prova de ${prova.tipoProva.nome}</h5>
            <p class="card-text"><strong>Data e Hora:</strong> ${formatarData(prova.data)}, ${formatarHora(prova.horario)}</p>
            <p class="card-text"><strong>Status:</strong> ${prova.statusAprovado ? "Aprovada" : "Não aprovada"}</p>
        </div>
    `;
    listaElement.appendChild(card);
}

document.addEventListener('DOMContentLoaded', function () {
    fetchProvasOfUser();
});

async function fetchInstrutores() {
    const token = localStorage.getItem('Authorization');
    const response = await fetch('http://127.0.0.1:8080/auth/user/all/instrutor', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    });

    if (response.ok) {
        const data = await response.json();
        return data;
    } else {
        console.error('Erro ao obter a lista de instrutores:', response.status);
        return [];
    }
}

function exibirInstrutores(instrutores) {
    const modalBody = document.querySelector('#selectInstructorModal .modal-body');
    modalBody.innerHTML = '';

    instrutores.forEach(instrutor => {
        const card = document.createElement('div');
        card.classList.add('card', 'mb-3');
        card.innerHTML = `
            <div class="instrutor-card card mb-3">
                <div class="card-body">
                    <h5 class="card-title">${instrutor.login}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${instrutor.email}</h6>
                </div>
                <div class="card-footer text-right">
                    <button class="btn btn-primary" data-instrutor-id="${instrutor.id}">Selecionar</button>
                </div>
            </div>
        `;
        modalBody.appendChild(card);
    });

    modalBody.addEventListener('click', event => {
        if (event.target.classList.contains('btn')) {
            selecionarInstrutor(event.target);
        }
    });
}

async function selecionarInstrutor(button) {
    const instrutorId = button.dataset.instrutorId;
    const apiUrl = `http://127.0.0.1:8080/auth/setinstrutor/${instrutorId}/${localStorage.getItem('user_id')}`;

    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            localStorage.setItem('instrutorId', instrutorId);
            window.location.href = 'horariosDisponiveis.html';
        } else {
            const data = await response.json();
            console.error('Erro ao atualizar instrutor:', data);
        }
    } catch (error) {
        console.error('Erro ao buscar os dados:', error);
    }
}

async function init() {
    const instrutores = await fetchInstrutores();
    exibirInstrutores(instrutores);
}

fetchProvasOfUser();
fetchUserInformation();

document.addEventListener('DOMContentLoaded', init);
fetchPagamentosOfUser();
