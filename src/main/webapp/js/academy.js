// Filtrado en vivo por nombre de academia
document.getElementById('academySearch').addEventListener('input', function () {
    const search = this.value.toLowerCase();
    const items = document.querySelectorAll('.academy-item');

    items.forEach(item => {
        const name = item.querySelector('input[name="academyName"]');
        if (name && name.value.toLowerCase().includes(search)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
});

// Ya no necesitas duplicar el script de edición aquí porque se inyectó en la propia JSP
// Pero si prefieres, puedes centralizarlo aquí también:
document.querySelectorAll('.toggle-edit').forEach(btn => {
    btn.addEventListener('click', function () {
        const form = this.closest('.edit-form');
        const nameInput = form.querySelector('input[name="academyName"]');
        const rucInput = form.querySelector('input[name="ruc"]');
        const confirmBtn = form.querySelector('.confirm-edit');

        nameInput.removeAttribute('readonly');
        rucInput.removeAttribute('readonly');
        nameInput.focus();
        this.classList.add('d-none');
        confirmBtn.classList.remove('d-none');
    });
});
