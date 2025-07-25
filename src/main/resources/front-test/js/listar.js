import professionalsData from './professionals.js';

async function loadProfessionals() {
    return professionalsData; // Retorna os dados diretamente
}

// Função para renderizar os profissionais
function renderProfessionals(professionals) {
    const professionalsList = document.getElementById('professionalsList');
    professionalsList.innerHTML = '';

    professionals.forEach(professional => {
        const card = document.createElement('div');
        card.className = 'professional-card';
        card.onclick = () => {
            localStorage.setItem('selectedProfessional', JSON.stringify(professional));
            window.location.href = 'visualizar.html';
        };
        card.innerHTML = `
            <img src="images/perfil.png" alt="Foto de Perfil">
            <div class="professional-info">
                <span>${professional.specialty}</span>
                <strong>${professional.name}</strong>
                <span class="specialty">${professional.city} - ${professional.state}</span>
            </div>
        `;
        professionalsList.appendChild(card);
    });
}

// Função para filtrar profissionais
function filterProfessionals(professionals) {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const specialtyFilter = document.getElementById('specialtyFilter').value;
    const cityFilter = document.getElementById('cityFilter').value;

    const filteredProfessionals = professionals.filter(professional => {
        const matchesSearch = professional.name.toLowerCase().includes(searchInput);
        const matchesSpecialty = !specialtyFilter || professional.specialty === specialtyFilter;
        const matchesCity = !cityFilter || professional.city === cityFilter;
        return matchesSearch && matchesSpecialty && matchesCity;
    });

    renderProfessionals(filteredProfessionals);
}

// Função para voltar
function goBack() {
    window.history.back();
}

// Inicialização
document.addEventListener('DOMContentLoaded', async function () {
    const professionals = await loadProfessionals();
    renderProfessionals(professionals);
    document.getElementById('searchInput').addEventListener('input', () => filterProfessionals(professionals));
    document.getElementById('specialtyFilter').addEventListener('change', () => filterProfessionals(professionals));
    document.getElementById('cityFilter').addEventListener('change', () => filterProfessionals(professionals));
});