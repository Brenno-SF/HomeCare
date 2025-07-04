function loadProfessionalsFromAPI() {
    const token = localStorage.getItem('token'); // Recupera o token armazenado

    fetch('http://localhost:8080/professional', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Adiciona o token no cabeçalho
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao buscar profissionais');
        }
        return response.json();
    })
    .then(data => {
        window.professionalsData = data;
        renderProfessionals(data);
    })
    .catch(error => {
        console.error('Erro ao carregar profissionais:', error);
    });
}

function renderProfessionals(professionals) {
    const professionalsList = document.getElementById('professionalsList');
    professionalsList.innerHTML = '';

    professionals.forEach(professional => {
        const name = professional.name;
        const specialty = professional.bio || 'Especialidade não informada';
        const address = professional.addresses?.[0];
        const city = address?.city || 'Cidade';
        const state = address?.fu || '';

        const card = document.createElement('div');
        card.className = 'professional-card';
        card.innerHTML = `
            <div class="professional-info">
                <span>${specialty}</span>
                <strong>${name}</strong>
                <span class="specialty">${city} - ${state}</span>
            </div>
        `;
        professionalsList.appendChild(card);
    });
}

function filterProfessionals() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const specialtyFilter = document.getElementById('specialtyFilter').value;
    const cityFilter = document.getElementById('cityFilter').value;

    const filteredProfessionals = (window.professionalsData || []).filter(professional => {
        const nameMatch = professional.name.toLowerCase().includes(searchInput);
        const specialtyMatch = !specialtyFilter || professional.bio === specialtyFilter;
        const cityMatch = !cityFilter || (professional.addresses?.[0]?.city === cityFilter);
        return nameMatch && specialtyMatch && cityMatch;
    });

    renderProfessionals(filteredProfessionals);
}

function goBack() {
    window.history.back();
}

loadProfessionalsFromAPI();
