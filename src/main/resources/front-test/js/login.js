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