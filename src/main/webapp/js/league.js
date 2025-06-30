const searchInput = document.getElementById('leagueSearch');
searchInput.addEventListener('input', function () {
    const query = this.value.toLowerCase();
    document.querySelectorAll('.league-item').forEach(item => {
        const title = item.querySelector('.name').value.toLowerCase();
        item.style.display = title.includes(query) ? '' : 'none';
    });
});

document.querySelectorAll('.toggle-edit').forEach(btn => {
    btn.addEventListener('click', function () {
        const form = this.closest('.edit-form');
        const input = form.querySelector('input[name="leagueName"]');
        const confirmBtn = form.querySelector('.confirm-edit');

        input.removeAttribute('readonly');
        input.focus();
        this.classList.add('d-none');
        confirmBtn.classList.remove('d-none');
    });
});