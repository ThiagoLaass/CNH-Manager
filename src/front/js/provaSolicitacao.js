const token = localStorage.getItem('Authorization');

document.getElementById('solicitacaoProvaForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    await cadastraSolicitacaoProva();
});

async function cadastraSolicitacaoProva(){
        const descricao = document.getElementById('descricaoSolicitacaoProva').value;
        const idTipoProva = document.getElementById('tipoProva').value;

        const solicitacaoProvaData = {
            "idTipoProva": idTipoProva,
            "descricao": descricao
        }

        try {

            const response = await fetch('http://127.0.0.1:8080/solicitacaoDeProva/cadastrar', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(solicitacaoProvaData)
        });

        if (!response.ok) {
            console.log('Response status:', response.status);
            console.log('Response text:', await response.text());
            throw new Error("Erro ao adicionar solicitação de prova.");
        } else {
            alert('Requerimento enviado com sucesso!');
            document.getElementById('presencaForm').reset();
            bootstrap.Modal.getInstance(document.getElementById('requerimentoProvaModal')).hide();
        }

        // limpar os campos de fomrulário após o sucesso
        document.getElementById("descricaoSolicitacaoProva").value = "";
        document.getElementById("tipoProva").value = "";
            
        } catch (error) {
            console.error("Erro ao cadastrar solicitação de prova:", error);
            
        }

}