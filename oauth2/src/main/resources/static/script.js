function loginGoogle() {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
}

function carregarDadosUsuario() {
    const params = new URLSearchParams(window.location.search);

    const nome = params.get("nome");
    const email = params.get("email");
    const foto = params.get("foto");

    if (!document.getElementById("nome")) return;

    document.getElementById("nome").innerText = nome;
    document.getElementById("email").innerText = email;

    const img = document.getElementById("foto");

    if (foto && foto !== "null" && foto !== "") {
        img.style.backgroundImage = `url('${decodeURIComponent(foto)}')`;
    } else {
        // SEM FOTO → CÍRCULO COM PRIMEIRA LETRA
        const letra = nome?.charAt(0)?.toUpperCase() || "?";
        img.textContent = letra;
        img.classList.add("no-photo");
    }
}


function realizarLogout() {
    fetch("http://localhost:8080/logout", {
            method: "POST",
            credentials: "include"
        }).then(() => {
            window.location.href = "/";
        });
}

document.addEventListener("DOMContentLoaded", () => {

    // Ativar login (somente se o botão existir)
    const loginButton = document.getElementById("googleBtn");
    if (loginButton) {
        loginButton.addEventListener("click", iniciarLoginGoogle);
    }

    carregarDadosUsuario();

    const logoutButton = document.getElementById("logoutBtn");
    if (logoutButton) {
        logoutButton.addEventListener("click", realizarLogout);
    }
});