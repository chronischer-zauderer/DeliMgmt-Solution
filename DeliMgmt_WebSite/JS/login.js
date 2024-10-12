// Función de login, realiza la solicitud de autenticación y guarda el token
export async function login(username, password) {
    try {
        const response = await fetch('http://localhost:8081/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }), // Usa los valores proporcionados
        });

        if (!response.ok) {
            throw new Error('Error en la autenticación');
        }

        const data = await response.json();

        // Verifica si el accesstoken está presente
        if (data.accesstoken) {
            localStorage.setItem('token', data.accesstoken); // Guarda el accesstoken
            console.log('Token almacenado:', localStorage.getItem('token'));
            return data; // Devuelve el objeto data
        } else {
            throw new Error('Token no recibido'); // Si no hay token, lanza un error
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Autenticación fallida. Por favor verifica tus credenciales.');
        throw error; // Lanza el error para manejarlo en el llamador
    }
}


// Función de logout, elimina el token y redirige a la página de login
export function logout() {
    localStorage.removeItem('token'); // Elimina el token
    window.location.href = 'Index.html'; // Redirige al login
}

