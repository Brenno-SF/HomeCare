import professionalsData from './professionals.js';

function goBack() {
    window.history.back();
}

function scheduleAppointment() {
    const professional = JSON.parse(localStorage.getItem('selectedProfessional'));
    const selectedDate = localStorage.getItem('selectedDate');
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;

    if (professional && selectedDate && startTime && endTime) {
        localStorage.setItem('selectedAppointment', JSON.stringify({
            ...professional,
            date: selectedDate,
            startTime,
            endTime
        }));
        window.location.href = 'agendamento.html';
    } else {
        alert('Por favor, selecione um dia e horário válido.');
    }
}

function renderCalendar(professional, year, month) {
    const calendar = document.getElementById('calendar');
    const monthYear = document.getElementById('monthYear');
    calendar.innerHTML = '';

    const today = new Date();
    const currentDate = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0).getDate();
    const firstDayIndex = currentDate.getDay();
    const months = [
        'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];

    monthYear.textContent = `${months[month]} ${year}`;

    for (let i = 0; i < firstDayIndex; i++) {
        const emptyDiv = document.createElement('div');
        emptyDiv.classList.add('empty');
        calendar.appendChild(emptyDiv);
    }

    for (let day = 1; day <= lastDay; day++) {
        const date = new Date(year, month, day);
        const dateStr = date.toISOString().split('T')[0];
        const dayDiv = document.createElement('div');
        dayDiv.textContent = day;

        if (date < today.setHours(0, 0, 0, 0)) {
            dayDiv.classList.add('past');
        } else {
            const isReserved = professional.availability[dateStr]?.length > 0;
            dayDiv.classList.add(isReserved ? 'reserved' : 'available');
            if (!isReserved) {
                dayDiv.onclick = () => {
                    document.querySelectorAll('.calendar div').forEach(div => div.classList.remove('selected'));
                    dayDiv.classList.add('selected');
                    showScheduleOptions(dateStr, professional.availability[dateStr]);
                };
            }
        }
        calendar.appendChild(dayDiv);
    }
}

function showScheduleOptions(date, availableSlots) {
    const startTime = document.getElementById('startTime');
    const endTime = document.getElementById('endTime');
    startTime.innerHTML = '<option value="">Selecione o horário de entrada</option>';
    endTime.innerHTML = '<option value="">Selecione o horário de saída</option>';

    const times = [
        "08:00", "09:00", "10:00", "11:00", "12:00",
        "13:00", "14:00", "15:00", "16:00", "17:00"
    ];
    availableSlots = availableSlots || [];
    const reservedSlots = availableSlots.map(slot => slot.split('-')[0]);

    times.forEach(time => {
        if (!reservedSlots.includes(time)) {
            const option = new Option(time, time);
            startTime.add(option);
        }
    });

    startTime.onchange = () => {
        const selectedStart = startTime.value;
        endTime.innerHTML = '<option value="">Selecione o horário de saída</option>';
        const startIndex = times.indexOf(selectedStart);
        times.slice(startIndex + 1).forEach(time => {
            if (!reservedSlots.includes(time)) {
                endTime.add(new Option(time, time));
            }
        });
    };

    document.getElementById('scheduleSection').classList.remove('hidden');
    localStorage.setItem('selectedDate', date);
}

document.addEventListener('DOMContentLoaded', async function () {
    const professional = JSON.parse(localStorage.getItem('selectedProfessional'));
    if (professional) {
        const title = document.querySelector('.title');
        title.textContent = `Disponibilidade de ${professional.name}`;

        const today = new Date();
        let currentYear = today.getFullYear();
        let currentMonth = today.getMonth();

        renderCalendar(professional, currentYear, currentMonth);

        document.getElementById('prevMonth').onclick = () => {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            renderCalendar(professional, currentYear, currentMonth);
        };

        document.getElementById('nextMonth').onclick = () => {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            renderCalendar(professional, currentYear, currentMonth);
        };
    } else {
        document.getElementById('calendar').innerHTML = '<p>Profissional não encontrado.</p>';
        document.querySelector('.title').textContent = 'Erro: Profissional não encontrado';
    }
});