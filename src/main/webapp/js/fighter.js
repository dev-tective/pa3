// Filtrado en vivo por nombre del luchador
document.getElementById('fighterSearch').addEventListener('input', function () {
    const search = this.value.toLowerCase();
    const items = document.querySelectorAll('.fighter-item');

    items.forEach(item => {
        const nameInput = item.querySelector('input[name="fullName"]');
        if (nameInput && nameInput.value.toLowerCase().includes(search)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
});

// Activar edición de luchador
document.querySelectorAll('.toggle-edit').forEach(btn => {
    btn.addEventListener('click', function () {
        const form = this.closest('.edit-form');
        const inputs = form.querySelectorAll('input, select');
        const confirmBtn = form.querySelector('.confirm-edit');

        inputs.forEach(input => {
            if (input.name !== "id" && input.name !== "_method") {
                input.removeAttribute('readonly');
                input.removeAttribute('disabled');
            }
        });

        this.classList.add('d-none');
        confirmBtn.classList.remove('d-none');

        // Enfoca el primer campo editable (nombre)
        const nameInput = form.querySelector('input[name="fullName"]');
        if (nameInput) nameInput.focus();
    });
});