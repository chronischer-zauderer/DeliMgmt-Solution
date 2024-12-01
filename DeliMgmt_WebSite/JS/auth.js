function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token) {
        // Si no hay token, redirige inmediatamente
        redirectToLogin();
        return;
    }

    const tokenPayload = parseJwt(token);
    const now = Math.floor(Date.now() / 1000); // Tiempo actual en segundos

    if (tokenPayload.exp && tokenPayload.exp < now) {
        // Si el token ya expiró, redirige
        redirectToLogin();
        return;
    }

    // Calcula el tiempo restante para la expiración
    const timeUntilExpiry = (tokenPayload.exp - now) * 1000;

    // Configura un temporizador para actuar cuando expire
    setTimeout(() => {
        // Mostrar modal en lugar de redirigir
        const modal = new bootstrap.Modal(document.getElementById('sessionExpiredModal'));
        modal.show();
        localStorage.removeItem('token');
    }, timeUntilExpiry);
    
}

function redirectToLogin() {
    window.location.href = 'Index.html';
}

function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        console.error('Error parsing token:', e);
        return {};
    }
}
// Llama a la función al cargar la página
document.addEventListener('DOMContentLoaded', checkAuth);

