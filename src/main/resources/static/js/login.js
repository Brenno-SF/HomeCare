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

    const token = await response.text();
    localStorage.setItem('token', token);
    window.location.href = 'listar.html';


    // Redirecionar para listar.html
    window.location.href = 'listar.html';

  } catch (error) {
    console.error('Erro no login:', error);
    alert("Erro ao conectar com o servidor.");
  }
});

// Função para alternar entre formulários de login e cadastro
function toggleForms() {
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');

    if (!registerForm || !loginForm) {
        console.error('Formulários não encontrados');
        return;
    }

    if (registerForm.classList.contains('hidden')) {
        registerForm.classList.remove('hidden');
        loginForm.classList.add('hidden');
        clearForm('login');
    } else {
        registerForm.classList.add('hidden');
        loginForm.classList.remove('hidden');
        clearForm('register');
    }

    restartFormAnimations();
}

// Função para alternar campos específicos de paciente e profissional
function toggleUserTypeFields() {
    const tipoPerfil = document.getElementById('tipoPerfil').value;
    const pacienteFields = document.getElementById('pacienteFields');
    const profissionalFields = [
        document.getElementById('profissionalFields'),
        document.getElementById('profissionalFields2'),
        document.getElementById('profissionalFields3'),
        document.getElementById('profissionalFields4'),
        document.getElementById('profissionalFields5'),
        document.getElementById('profissionalFields6')
    ];

    if (tipoPerfil === 'paciente') {
        pacienteFields.classList.remove('hidden');
        profissionalFields.forEach(field => field.classList.add('hidden'));
        document.getElementById('cpf').required = true;
        profissionalFields.forEach(field => {
            const input = field.querySelector('input, textarea, select');
            if (input) input.required = false;
        });
    } else if (tipoPerfil === 'profissional') {
        pacienteFields.classList.add('hidden');
        profissionalFields.forEach(field => field.classList.remove('hidden'));
        document.getElementById('specialty').required = true;
        document.getElementById('description').required = true;
        document.getElementById('credential_number').required = true;
        document.getElementById('credential_fu').required = true;
        document.getElementById('validation_date').required = true;
        document.getElementById('credential_type').required = true;
        document.getElementById('cpf').required = false;
    } else {
        pacienteFields.classList.add('hidden');
        profissionalFields.forEach(field => field.classList.add('hidden'));
        document.getElementById('cpf').required = false;
        profissionalFields.forEach(field => {
            const input = field.querySelector('input, textarea, select');
            if (input) input.required = false;
        });
    }

    restartFormAnimations();
}

// Define o formulário de login como padrão, caso formAtivo não esteja definido
document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');

    if (registerForm && loginForm) {
        registerForm.classList.add('hidden');
        loginForm.classList.remove('hidden');
    }
});
document.getElementById("register").addEventListener("submit", async function (e) {
  e.preventDefault();

  const tipoPerfil = document.getElementById("tipoPerfil").value;
  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;
  const birthDate = document.getElementById("birth_date").value;
  const gender = document.getElementById("gender").value;
  const phoneNumber = document.getElementById("phone_number").value;

  const address = {
    zipCode: document.getElementById("zip_code").value,
    street: document.getElementById("street").value,
    number: parseInt(document.getElementById("number").value),
    complement: document.getElementById("complement").value,
    neighborhood: document.getElementById("neighborhood").value,
    city: document.getElementById("city").value,
    fu: document.getElementById("fu").value,
  };

  const phone = {
    phoneNumber: phoneNumber,
    typePhone: "CELULAR" // ou coloque um campo no formulário para selecionar o tipo
  };

  if (tipoPerfil === "paciente") {
    const cpf = document.getElementById("cpf").value;

    const pacienteData = {
      name: nome,
      email: email,
      password: senha,
      cpf: cpf,
      birthDate: birthDate,
      gender: gender,
      addresses: [address],
      phones: [phone]
    };

    try {
      const response = await fetch("http://localhost:8080/patient/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(pacienteData)
      });

      if (response.ok) {
        alert("Cadastro de paciente realizado com sucesso!");
      } else {
        const error = await response.json();
        alert("Erro ao cadastrar paciente: " + (error.message || "Erro desconhecido"));
      }
    } catch (err) {
      alert("Erro de rede: " + err.message);
    }

  } else if (tipoPerfil === "profissional") {
    const bio = document.getElementById("specialty").value;
    const description = document.getElementById("description").value;
    const credential_number = document.getElementById("credential_number").value;
    const credential_fu = document.getElementById("credential_fu").value;
    const validation_date = document.getElementById("validation_date").value;
    const credential_type = document.getElementById("credential_type").value;

    const profissionalData = {
      name: nome,
      email: email,
      password: senha,
      birthDate: birthDate,
      gender: gender,
      bio: bio,
      description: description,
      rate: 0, // você pode permitir o usuário informar, se necessário
      addresses: [address],
      phones: [phone],
      credentials: [
        {
          number: credential_number,
          fu: credential_fu,
          validation_date: validation_date,
          type: credential_type
        }
      ]
    };

    try {
      const response = await fetch("http://localhost:8080/professional/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(profissionalData)
      });

      if (response.ok) {
        alert("Cadastro de profissional realizado com sucesso!");
        toggleForms();
      } else {
        const error = await response.json();
        alert("Erro ao cadastrar profissional: " + (error.message || "Erro desconhecido"));
      }
    } catch (err) {
      alert("Erro de rede: " + err.message);
    }

  } else {
    alert("Selecione um tipo de perfil válido.");
  }
});
