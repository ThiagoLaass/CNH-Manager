async function fetchCargaHoraria(cnhAprovada) {
  try {
    const apiUrl = `http://localhost:8080/carga-horaria/${localStorage.getItem('user_id')}`;
    const response = await fetch(apiUrl, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('Authorization')}`,
        'Content-Type': 'application/json'
      }
    });

    const data = await response.json();
    console.log(data);
    const cargaHorariaConcluida = data;

    // Mapear aulas_id com nomes de aulas
    const aulas = {
      0: 'Prática',
      1: 'Legislação de Trânsito',
      2: 'Direção defensiva',
      3: 'Primeiros Socorros',
      4: 'Meio Ambiente',
      5: 'Mecânica'
    };

    // Calcular progresso para cada aula
    const progresso = {};
    Object.keys(aulas).forEach(aulaId => {
      const aula = cargaHorariaConcluida.find(chc => chc.aula.id === parseInt(aulaId));
      if (aula) {
        const horasConcluidas = aula.cargaHoraria;
        const totalHoras = aula.aula.cargaHoraria;
        
        let porcentagem = 0;
        if (totalHoras > 0) {
          porcentagem = (horasConcluidas / totalHoras) * 100;
        }
        
        progresso[aulas[aulaId]] = { 
          horasConcluidas: horasConcluidas, 
          totalHoras: totalHoras, 
          porcentagem: porcentagem.toFixed(2) 
        };
      } else {
        progresso[aulas[aulaId]] = { 
          horasConcluidas: 0, 
          totalHoras: 0, 
          porcentagem: '0.00' 
        };
      }
    });

    // tabela de progresso
    const tabelaProgresso = document.getElementById('tabela-progresso-body');
    tabelaProgresso.innerHTML = '';
    Object.keys(progresso).forEach((aula, index) => {
      const row = tabelaProgresso.insertRow(index);
      row.innerHTML = `
        <td>${aula}</td>
        <td>${progresso[aula].horasConcluidas} / ${progresso[aula].totalHoras}</td>
        <td>
          (${progresso[aula].porcentagem}%)
          <div class="progress">
            <div class="progress-bar" style="width: ${progresso[aula].porcentagem}%"></div>
          </div>
        </td>
      `;
    });

    // Verificar se o aluno completou 100% de todas as aulas para avaliacao
    let completo = true;
    Object.keys(progresso).forEach((aula) => {
      if (progresso[aula].porcentagem < 100) {
        completo = false;
      }
    });

    if (completo) {
      const avaliarBtnContainer = document.getElementById('avaliar-autoescola-btn-container');
      avaliarBtnContainer.style.display = 'block';
      
      if (cnhAprovada) {
        avaliarBtnContainer.innerHTML = `
          <div class="parabens-container">
            <h1>Parabéns!</h1>
            <p>Você concluiu o curso da autoescola.</p>
            <p>Agora, avalie a sua experiência conosco.</p>
            <button class="btn btn-primary" id="avaliar-autoescola-btn">Avaliar Autoescola</button>
          </div>
        `;
      } else {
        avaliarBtnContainer.innerHTML = `
          <div class="parabens-container">
            <h1>Parabéns!</h1>
            <p>Você concluiu o curso teórico.</p>
          </div>
        `;
      }

      // Adicionar estilo customizado para o container de parabéns
      const style = document.createElement('style');
      style.innerHTML = `
        .parabens-container {
          text-align: center;
          padding: 20px;
          background-color: #f8f9fa;
          border: 1px solid #ddd;
          border-radius: 8px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          margin-top: 20px; /* Adiciona espaço acima do contêiner de parabéns */
        }
        .parabens-container h1 {
          color: #28a745;
          font-size: 2em;
          margin-bottom: 10px;
        }
        .parabens-container p {
          font-size: 1.2em;
        }
        .parabens-container .btn {
          margin-top: 15px;
        }
      `;
      document.head.appendChild(style);

      const avaliarBtn = document.getElementById('avaliar-autoescola-btn');
      avaliarBtn.addEventListener('click', () => {
        // Abre a janela modal para avaliar autoescola
        const modal = document.getElementById('avaliar-autoescola-modal');
        new bootstrap.Modal(modal).show();
      });
    }
  } catch (error) {
    console.error('Erro ao buscar os dados: ', error);
  }
}

fetchCargaHoraria();
