export async function login(username, password) {
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
    return data; 
  }
  