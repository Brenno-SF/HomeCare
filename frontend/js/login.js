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

document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');
    
    // Define o formulário de login como padrão, caso formAtivo não esteja definido
    if (registerForm && loginForm) {
        registerForm.classList.add('hidden');
        loginForm.classList.remove('hidden');
    }
});

// Função para limpar formulário
function clearForm(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
        const inputs = form.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            input.classList.remove('input-error', 'input-success');
        });
    }
}

// Função para reiniciar animações do formulário
function restartFormAnimations() {
    const forms = document.querySelectorAll('.form-container:not(.hidden) .form-group');
    forms.forEach((formGroup, index) => {
        formGroup.style.animation = 'none';
        formGroup.offsetHeight; // Trigger reflow
        formGroup.style.animation = null;
        formGroup.style.animationDelay = `${0.1 * (index + 1)}s`;
    });
}

// Evento de submissão do formulário de cadastro
document.getElementById('register').addEventListener('submit', async function (event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);
    const tipoPerfil = formData.get('tipoPerfil');

    // Estrutura básica compartilhada por paciente e profissional
    const data = {
        name: formData.get('nome'),
        email: formData.get('email'),
        password: formData.get('senha'),
        birthDate: formData.get('birth_date'),
        gender: formData.get('gender'),
        addresses: [{
            city: formData.get('city'),
            neighborhood: formData.get('neighborhood'),
            zipCode: formData.get('zip_code'),
            complement: formData.get('complement') || null,
            number: parseInt(formData.get('number')),
            street: formData.get('street'),
            fu: formData.get('fu')
        }],
        phones: [{
            phoneNumber: formData.get('phone_number'),
            typePhone: 'cell'
        }]
    };

    // Dados específicos por tipo de perfil
    if (tipoPerfil === 'paciente') {
        data.cpf = formData.get('cpf');
    } else if (tipoPerfil === 'profissional') {
        data.credentials = [{
            number: formData.get('credential_number'),
            fu: formData.get('credential_fu'),
            validation_date: formData.get('validation_date'),
            type: formData.get('credential_type')
        }];
        data.bio = formData.get('specialty'); // Mapeando 'specialty' para 'bio'
        data.description = formData.get('description');
        data.rate = 0; // Valor inicial, já que não é coletado no formulário
    }

    const endpoint = tipoPerfil === 'paciente' ? 'http://localhost:8080/patient' : 'http://localhost:8080/professional';

    try {
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (response.ok) {
            alert('Cadastro realizado com sucesso!');
            clearForm('register');
            toggleForms();
            form.querySelectorAll('input, select, textarea').forEach(input => {
                input.classList.add('input-success');
                input.classList.remove('input-error');
            });
        } else {
            alert('Erro ao cadastrar: ' + (result.message || 'Erro desconhecido'));
            form.querySelectorAll('input, select, textarea').forEach(input => {
                input.classList.add('input-error');
                input.classList.remove('input-success');
            });
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro ao conectar com o servidor.');
        form.querySelectorAll('input, select, textarea').forEach(input => {
            input.classList.add('input-error');
            input.classList.remove('input-success');
        });
    }
});

// Evento de submissão do formulário de login
document.getElementById('login').addEventListener('submit', async function (event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);
    const data = {
        email: formData.get('email'),
        password: formData.get('senha')
    };

    try {
        const response = await fetch('http://localhost:8080/login/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.text(); // Espera uma string (token ou mensagem)

        if (response.ok) {
            alert('Login realizado com sucesso!');
            clearForm('login');
            // Armazenar o token (se aplicável) ou redirecionar
            // Exemplo: localStorage.setItem('token', result);
            // window.location.href = '/dashboard.html';
            form.querySelectorAll('input').forEach(input => {
                input.classList.add('input-success');
                input.classList.remove('input-error');
            });
        } else {
            alert('Erro ao fazer login: ' + result);
            form.querySelectorAll('input').forEach(input => {
                input.classList.add('input-error');
                input.classList.remove('input-success');
            });
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro ao conectar com o servidor.');
        form.querySelectorAll('input').forEach(input => {
            input.classList.add('input-error');
            input.classList.remove('input-success');
        });
    }
});