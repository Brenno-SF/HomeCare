const professionalsData = [
    {
        name: "Davi Roriz",
        specialty: "Fisioterapeuta",
        city: "Itabuna",
        state: "BA"
    },
    {
        name: "Deyvid Reis",
        specialty: "Nutricionista",
        city: "Una",
        state: "BA"
    },
    {
        name: "Mariana Oliveira",
        specialty: "Enfermeiro",
        city: "Itagibá",
        state: "BA"
    },
    {
        name: "Estêvão Sousa",
        specialty: "Psicólogo",
        city: "Ubatã",
        state: "BA"
    },
    {
        name: "Maria Alice",
        specialty: "Fisioterapeuta",
        city: "Ilhéus",
        state: "BA"
    },
    {
        name: "Brenno Florêncio",
        specialty: "Enfermeiro",
        city: "Itagibá",
        state: "BA"
    },
    {
        name: "Bruno Santos",
        specialty: "Fonoaudiólogo",
        city: "Itabuna",
        state: "BA"
    }
];

function renderProfessionals(professionals) {
    const professionalsList = document.getElementById('professionalsList');
    professionalsList.innerHTML = '';

    professionals.forEach(professional => {
        const card = document.createElement('div');
        card.className = 'professional-card';
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

function filterProfessionals() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const specialtyFilter = document.getElementById('specialtyFilter').value;
    const cityFilter = document.getElementById('cityFilter').value;

    const filteredProfessionals = professionalsData.filter(professional => {
        const matchesSearch = professional.name.toLowerCase().includes(searchInput);
        const matchesSpecialty = !specialtyFilter || professional.specialty === specialtyFilter;
        const matchesCity = !cityFilter || professional.city === cityFilter;
        return matchesSearch && matchesSpecialty && matchesCity;
    });

    renderProfessionals(filteredProfessionals);
}

function goBack() {
    window.history.back();
}

renderProfessionals(professionalsData);