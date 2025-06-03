function toggleForms() {
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');
    
    if (!registerForm || !loginForm) {
        console.error('Formulários não encontrados');
        return;
    }
    
    if (registerForm.classList.contains('hidden')) {
        // Mostrar cadastro, esconder login
        registerForm.classList.remove('hidden');
        loginForm.classList.add('hidden');
        clearForm('login');
    } else {
        // Mostrar login, esconder cadastro
        registerForm.classList.add('hidden');
        loginForm.classList.remove('hidden');
        clearForm('register');
    }
    
    restartFormAnimations();
}