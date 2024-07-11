const instrutorId = localStorage.getItem('user_id');
const apiUrl = `http://127.0.0.1:8080/horario/awatingapproval/${instrutorId}`;

function displayUserInfo(nome, role) {
    const userInfoElement = document.getElementById('user-info');
    userInfoElement.innerHTML = `Olá ${nome}! Seja bem vindo(a) ao espaço de ${role}`;
}

async function fetchUserInformation() {
    const apiUrl = `http://127.0.0.1:8080/auth/user/${localStorage.getItem('user_id')}`;

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
        return data.role;
    } catch (error) {
        console.error('Erro ao buscar os dados: ', error);
    }
}

async function fetchHorariosAguardandoConfirmacao() {

    console.log(apiUrl);

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const horarios = await response.json();
            const horariosTableBody = document.getElementById('horariosTableBody');


            horariosTableBody.innerHTML = '';

            horarios.forEach(horario => {
                const horaInicioFormatada = formatarHora(horario.horaInicio);
                const horaFimFormatada = formatarHora(horario.horaFim);
                const dataFormatada = formatarData(horario.data);

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${dataFormatada}</td>
                    <td>${horaInicioFormatada}</td>
                    <td>${horaFimFormatada}</td>
                    <td>
                        <button class="btn btn-success" onclick="confirmarHorario('${horario.codHorario}')">Aprovar</button>
                        <button class="btn btn-danger" onclick="negarHorario('${horario.codHorario}')">Negar</button>
                    </td>
                `;
                horariosTableBody.appendChild(row);
            });
        } else {
            console.error('Erro ao buscar horários: ', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao buscar horários: ', error);
    }
}

async function confirmarHorario(horarioId) {
    const apiUrl = `http://127.0.0.1:8080/horario/approveOrDeny/${horarioId}`;
    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                statusAprovado: true
            })
        });

        if (response.ok) {
            alert('Horário atualizado com sucesso!');
            fetchHorariosAguardandoConfirmacao();
        } else {
            console.error('Erro ao atualizar horário: ', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao atualizar horário: ', error);
    }
}

async function negarHorario(horarioId) {
    const apiUrl = `http://127.0.0.1:8080/horario/approveOrDeny/${horarioId}`;
    console.log(apiUrl)
    const horario = {
        statusAprovado: false
    }

    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                horario
            })
        });

        if (response.ok) {
            alert('Horário atualizado com sucesso!');
            fetchHorariosAguardandoConfirmacao();
        } else {
            console.error('Erro ao atualizar horário: ', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao atualizar horário: ', error);
    }
}

document.getElementById("logoutBtn").addEventListener("click", function () {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('user_id');
    window.location = 'login.html';
});

function formatarHora(hora) {
    const [horaPart, minutoPart] = hora;
    const horaFormatada = String(horaPart).padStart(2, '0');
    const minutoFormatado = String(minutoPart).padStart(2, '0');
    return `${horaFormatada}:${minutoFormatado}`;
}

function formatarData(data) {
    const [ano, mes, dia] = data;
    const mesFormatado = String(mes).padStart(2, '0');
    const diaFormatado = String(dia).padStart(2, '0');
    return `${diaFormatado}/${mesFormatado}/${ano}`;
}

fetchUserInformation();
fetchHorariosAguardandoConfirmacao();
