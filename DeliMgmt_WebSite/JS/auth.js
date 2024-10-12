// auth.js
function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token) {
        // Si no hay token, redirige al Index.html
        window.location.href = 'Index.html';
    }
}

// Llama a la función al cargar el archivo
checkAuth();
