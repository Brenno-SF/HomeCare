function goBack() {
    window.history.back();
}

function checkAvailability() {
    const professional = JSON.parse(localStorage.getItem('selectedProfessional'));
    if (professional) {
        // Salva o profissional no localStorage (já feito no listar.js)
        localStorage.setItem('selectedProfessional', JSON.stringify(professional));
        window.location.href = 'disponibilidade.html';
    } else {
        alert('Nenhum profissional selecionado.');
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const professional = JSON.parse(localStorage.getItem('selectedProfessional'));
    if (professional) {
        document.getElementById('professionalName').textContent = professional.name;
        document.getElementById('professionalSpecialty').textContent = professional.specialty;
        document.getElementById('professionalCredential').textContent = professional.credential_number;
        document.getElementById('professionalLocation').textContent = `${professional.city} - ${professional.state}`;
        document.getElementById('professionalDescription').textContent = professional.description;
    } else {
        document.getElementById('professionalName').textContent = 'Profissional não encontrado';
    }
});