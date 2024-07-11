const token = localStorage.getItem('Authorization')

function formatarHora(hora) {
    const [horaPart, minutoPart] = hora;
    const horaFormatada = String(horaPart).padStart(2, '0');
    const minutoFormatado = String(minutoPart).padStart(2, '0');
    return `${horaFormatada}:${minutoFormatado}`;
}

function formatarData(data){
    const [ano, mes, dia] = data;
    const anoFormatado = String(ano);
    const mesFormatado = String(mes).padStart(2, '0');
    const diaFormatado = String(dia).padStart(2, '0');
    return `${diaFormatado}/${mesFormatado}/${anoFormatado}`;

}

async function loadHorarios() {
    try {
        const horariosDiv = document.getElementById('horarios');
        horariosDiv.innerHTML = ''; // Limpa o conteúdo antes de adicionar novos horários

        const response = await fetch('http://127.0.0.1:8080/horario/all', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (!response.ok) {
            throw new Error('Erro ao carregar horários');
        }

        const horarios = await response.json();

        if (horarios.length === 0) {
            const semHorarios = document.createElement('div');
            semHorarios.textContent = 'Não há horários disponíveis';
            horariosDiv.appendChild(semHorarios);
        } else {
            const instrutorId = localStorage.getItem('instrutorId');
            const horariosInstrutor = horarios.filter(horario => 
                horario.instrutor.id === instrutorId && horario.statusAberto === true
            );

            if (horariosInstrutor.length === 0) {
                const semHorariosInstrutor = document.createElement('div');
                semHorariosInstrutor.textContent = 'Nenhum horário encontrado para o instrutor selecionado';
                horariosDiv.appendChild(semHorariosInstrutor);
            } else {
                horariosInstrutor.forEach(horario => {
                    const dataFormatada = formatarData(horario.data);
                    const horaInicioFormatada = formatarHora(horario.horaInicio);
                    const horaFimFormatada = formatarHora(horario.horaFim);

                    const horarioContainer = document.createElement('div');
                    horarioContainer.classList.add('horario-container');

                    const dataCol = document.createElement('div');
                    dataCol.classList.add('col', 'text-center');
                    dataCol.textContent = dataFormatada;

                    const horaCol = document.createElement('div');
                    horaCol.classList.add('col', 'text-center');
                    horaCol.textContent = `De: ${horaInicioFormatada} até ${horaFimFormatada}`;

                    const botao = document.createElement('button');
                    botao.classList.add('btn', 'btn-primary');
                    botao.textContent = 'Selecionar';
                    botao.addEventListener('click', () => {
                        selecionarHorario(horario.codHorario);
                    });

                    const botaoCol = document.createElement('div');
                    botaoCol.classList.add('col', 'text-center');
                    botaoCol.appendChild(botao);

                    horarioContainer.appendChild(dataCol);
                    horarioContainer.appendChild(horaCol);
                    horarioContainer.appendChild(botaoCol);

                    horariosDiv.appendChild(horarioContainer);
                });
            }
        }
    } catch (error) {
        console.error('Erro:', error.message);
        alert('Ocorreu um erro ao carregar os horários');
    }
}

async function selecionarHorario(id){

    const apiUrl = `http://127.0.0.1:8080/horario/${id}/${localStorage.getItem('user_id')}`

    const horarioAtualizado = {
        alunoId: localStorage.getItem("user_id"),
        statusAberto: false
    }

    

    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({horarioAtualizado})
        });

        if (response.ok) {
            location.reload();
            alert('Horario selecionado com sucesso!')
        } else {
            alert('Erro ao selecionar horario!', error)
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

async function fetchUserInformation() {
    const apiUrl = `http://127.0.0.1:8080/auth/user/${localStorage.getItem('user_id')}`;

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log(data)
            return data;
        } else {
            console.error('Erro ao obter tipo de usuário:', response.status);
            return null;
        }
    } catch (error) {
        console.error('Erro ao fazer a requisição:', error);
        return null;
    }
}

fetchUserInformation()
loadHorarios()