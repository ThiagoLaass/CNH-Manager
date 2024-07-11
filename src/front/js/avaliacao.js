const ratingInputs = document.querySelectorAll('.rating input[type="checkbox"]');
const ratingLabels = document.querySelectorAll('.rating label');

ratingInputs.forEach((input, index) => {
  input.addEventListener('click', () => {
    const value = parseInt(input.value);
    for (let i = 0; i < value; i++) {
      ratingInputs[i].checked = true;
    }
    for (let i = value; i < ratingInputs.length; i++) {
      ratingInputs[i].checked = false;
    }
  });
});
const avaliacaoForm = document.getElementById('avaliar-autoescola-form');

ratingInputs.forEach((input) => {
  input.addEventListener('change', () => {
    const selectedRating = getSelectedRating();
  });
});

avaliacaoForm.addEventListener('submit', (e) => {
  e.preventDefault();

  const descricaoInput = e.target.elements.descricao;
  const descricao = descricaoInput.value.trim();

  const userId = localStorage.getItem('user_id');
  const token = localStorage.getItem('Authorization');
  const selectedRating = getSelectedRating();

  if (!userId || !token) {
    console.error('User ID ou token não encontrado');
    return;
  }

  const avaliacao = {
    descricao: descricao,
    numAvaliacao: selectedRating,
    user: {
      id: userId,
    },
  };

  fetch('http://localhost:8080/indicators/avaliacoes/cadastrar', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(avaliacao),
  })
  .then((response) => {
    if (!response.ok) {
      throw new Error('Erro ao cadastrar avaliação');
    }
    return response.json();
  })
  .then((data) => {
    console.log(data);
    window.alert('Avaliação cadastrada com sucesso!');
  })
  .catch((error) => {
    console.error(error);
    window.alert('Erro ao cadastrar avaliação. Tente novamente mais tarde.');
  });
});

function getSelectedRating() {
  let selectedRating = 0;
  ratingInputs.forEach((input) => {
    if (input.checked) {
      selectedRating = parseInt(input.value);
    }
  });
  return selectedRating;
}