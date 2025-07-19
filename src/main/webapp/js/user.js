// Filtrado en vivo por nombre de usuario
document.getElementById('userSearch').addEventListener('input', function () {
    const search = this.value.toLowerCase();
    const items = document.querySelectorAll('.user-item');

    items.forEach(item => {
        const input = item.querySelector('input[name="username"]');
        if (input && input.value.toLowerCase().includes(search)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
});

// Activa edición
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

        const firstInput = form.querySelector('input[name="username"]');
        if (firstInput) firstInput.focus();
    });
});
