const token = localStorage.getItem('Authorization')

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('cadastrarHorarioForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const data = document.getElementById('dia').value;
        const horaInicio = document.getElementById('horarioInicio').value;
        const horaFim = document.getElementById('horarioFim').value;
        const statusAberto = true;
        const statusAprovado = false;

        console.log(data);

        try {
            const response = await fetch('http://127.0.0.1:8080/horario/cadastrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
                body: JSON.stringify({ data, horaFim, horaInicio, statusAberto, statusAprovado }),
            });

            if (response.ok) {
                alert('Horário cadastrado com sucesso!');
                event.target.reset();
            } else {
                alert('Erro ao cadastrar o horário: ' + response.status);
            }
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao cadastrar o horário: ' + error.message);
        }
    });
});
