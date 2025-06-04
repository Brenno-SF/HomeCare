document.getElementById('login').addEventListener('submit', async function (e) {
  e.preventDefault();

  const email = document.getElementById('loginEmail').value;
  const senha = document.getElementById('loginSenha').value;

  try {
    const response = await fetch('http://localhost:8080/login/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password: senha })
    });

    if (!response.ok) {
      alert("Credenciais inválidas.");
      return;
    }

    const data = await response.json();
    // Supondo que o backend retorna { token: '...' }
    localStorage.setItem('token', data.token);

    // Redirecionar para listar.html
    window.location.href = 'listar.html';

  } catch (error) {
    console.error('Erro no login:', error);
    alert("Erro ao conectar com o servidor.");
  }
});
