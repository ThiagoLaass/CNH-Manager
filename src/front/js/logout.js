document.getElementById("logoutBtn").addEventListener("click", function() {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('user_id');
    localStorage.removeItem('instrutorId');
    window.location = 'login.html';
});