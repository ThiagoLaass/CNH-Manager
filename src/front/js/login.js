async function login() {
    const login = document.getElementById('login').value;
    const password = document.getElementById('password').value;

    // axios.post('http://localhost:8080/auth/login', {
    //     login: login,
    //     password: password
    // })
    // .then(() => {
    //     document.getElementById('resultado').innerText = 'Login com sucesso';
    // })
    // .catch(() => {
    //     document.getElementById('resultado').innerText = 'Falha no login: ';
    // });

    const response = await fetch("http://127.0.0.1:8080/auth/login", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
        }),
        body: JSON.stringify({
            login: login,
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
}

document.getElementById("loginButton").addEventListener("click", function (event) {
    event.preventDefault();
    login();
});