

async function loadHorarios() {
  const apiUrl = `http://127.0.0.1:8080/horario/all/user/${userId}` 
  console.log(apiUrl)
  try {
    const response = await fetch(apiUrl, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
      }
    });
    const horarios = await response.json();
    console.log(horarios)
    const horariosContainer = document.getElementById("horariosContainer");

    horariosContainer.innerHTML = '';
    horarios.forEach(horario => {
        if (!horario.aguardandoAprovacao) {
          const dataFormatada = formatarData(horario.data);
          const horaInicioFormatada = formatarHora(horario.horaInicio);
          const horaFimFormatada = formatarHora(horario.horaFim);
          const horarioHTML = `
          <div class="horario card mb-3">
          <div class="card-body">
            <h4 class="card-title">${dataFormatada} - ${horaInicioFormatada} às ${horaFimFormatada}</h4>
            <div class="card-text d-flex justify-content-between align-items-center mt-3">
              <span class="instrutor">Instrutor: ${horario.instrutor.login}</span>
              <div class="d-flex">
                <button class="btn btn-danger btn-sm me-2 flex-fill" onclick="cancelaHorario('${horario.codHorario}')">Cancelar</button>
                <button class="btn btn-success btn-sm flex-fill" onclick="confirmarHorario('${horario.codHorario}')">Confirmar Conclusão</button>
              </div>
            </div>
          </div>
        </div>
      `;
          horariosContainer.innerHTML += horarioHTML;
      }
    });
  } catch (error) {
    console.error('Erro:', error);
  }
}

async function loadHorariosEsperandoAprovacao() {
  const apiUrl = `http://127.0.0.1:8080/horario/confirmed/${localStorage.getItem('user_id')}`
  console.log(apiUrl)
  try {
    const response = await fetch(apiUrl, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
      }
    });
    const horarios = await response.json();
    console.log(horarios)
    const horariosContainer = document.getElementById("horariosApContainer");

    horariosContainer.innerHTML = '';
    horarios.forEach(horario => {
        const dataFormatada = formatarData(horario.data);
        const horaInicioFormatada = formatarHora(horario.horaInicio);
        const horaFimFormatada = formatarHora(horario.horaFim);
        const horarioHTML = `
        <div class="horario card mb-3">
        <div class="card-body">
          <h4 class="card-title">${dataFormatada} - ${horaInicioFormatada} às ${horaFimFormatada}</h4>
          <div class="card-text d-flex justify-content-between align-items-center mt-3">
            <span class="instrutor">Instrutor: ${horario.instrutor.login}</span>
          </div>
        </div>
      </div>
      `;
        horariosContainer.innerHTML += horarioHTML;
    });
  } catch (error) {
    console.error('Erro:', error);
  }
}

async function cancelaHorario(id) {
  const apiUrl = `http://127.0.0.1:8080/horario/cancel/${id}`

  const horarioAtualizado = {
    statusAberto: true,
    alunoId: null
  }

  try {
    const response = await fetch(apiUrl, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ horarioAtualizado })
    });

    if (response.ok) {
      alert('Horario cancelado com sucesso!');
      loadHorarios();
    } else {
      alert('Erro ao cancelar horario!', error)
    }
  } catch (error) {
    console.error('Erro:', error);
  }

}

function formatarHora(hora) {
  const [horaPart, minutoPart] = hora;
  const horaFormatada = String(horaPart).padStart(2, '0');
  const minutoFormatado = String(minutoPart).padStart(2, '0');
  return `${horaFormatada}:${minutoFormatado}`;
}

function formatarData(data) {
  const [ano, mes, dia] = data;
  const anoFormatado = String(ano);
  const mesFormatado = String(mes).padStart(2, '0');
  const diaFormatado = String(dia).padStart(2, '0');
  return `${diaFormatado}/${mesFormatado}/${anoFormatado}`;
}

async function confirmarHorario(horarioId) {
  const apiUrl = `http://127.0.0.1:8080/horario/confirm/${horarioId}`;
  console.log(apiUrl);
  try {
    const response = await fetch(apiUrl, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (response.ok) {
      alert('Horário confirmado com sucesso!');
      loadHorarios();
    } else {
      alert('Erro ao confirmar horário.');
    }
  } catch (error) {
    console.error('Erro ao confirmar horário: ', error);
  }
}

const myHorariosModal = new bootstrap.Modal(document.getElementById("myHorariosModal"));
document.getElementById("myHorariosModal").addEventListener("shown.bs.modal", loadHorarios);

const myHorariosApModal = new bootstrap.Modal(document.getElementById("aguardandoAprovacao"));
document.getElementById("aguardandoAprovacao").addEventListener("shown.bs.modal", loadHorariosEsperandoAprovacao);
