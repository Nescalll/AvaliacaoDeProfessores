
document.addEventListener("DOMContentLoaded", function() {

    function logout() {
        console.log("Realizando logout...");
        window.location.href = '#';
    }

    const logoutButton = document.getElementById("logout");
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            logout();
        })
    } else {
        console.warn("Botão de logout não encontrado!")
    }
});