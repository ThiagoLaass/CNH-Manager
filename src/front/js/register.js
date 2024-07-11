document.getElementById('registerForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const username = document.getElementById('newUsername').value;
    const email = document.getElementById('newEmail').value;
    const password = document.getElementById('newPassword').value;
    const role = document.getElementById('newRole').value;
    // const isInstrutor = document.getElementById('isInstrutor').value;

    // if(isInstrutor == "on"){
    //     isInstrutor = true;
    // }

    const userInfos = {
        "login": username,
        "email": email,
        "password": password,
        "role": role
    }

    console.log(role)
    const response = await fetch('http://127.0.0.1:8080/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userInfos)
    });
    

    if (response.ok) {
        alert("Usuário registrado com sucesso!");
        const response = await fetch("http://127.0.0.1:8080/auth/login", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
        }),
        body: JSON.stringify({
            login: username,
            password: password,
        }),
    });

    if (response.ok) {
        const data = await response.json();
        const token = data.token;
        const id = data.id;
        const role = data.role;

        window.localStorage.setItem("Authorization", token);
        window.localStorage.setItem("user_id", id);

        switch (role) {
            case "USER":
                window.location.href = 'alunoProfile.html';
                break;
            case "ADMIN":
                window.location.href = 'adminProfile.html';
                break;
            case "INSTRUTOR":
                window.location.href = 'instrutorProfile.html';
                break;
            default:
                console.error("Erro: papel do usuário desconhecido");
        }
    } else {
        document.getElementById('resultado').innerText = 'Falha no login: ' + response.status;
    }

        document.getElementById('newUsername').value = "";
        document.getElementById('newEmail').value = "";
        document.getElementById('newPassword').value = "";
        document.getElementById('newRole').value = "";
    } else {
        alert("Erro ao registrar usuário.");
    }
});
