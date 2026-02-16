function generateGradient() {
    const img = document.getElementById('gradient');
    img.src = '/generate-gradient?' + new Date().getTime();
}

const button = document.getElementById('generate-btn');

button.addEventListener('click', generateGradient);
document.getElementById('gradient').addEventListener('click', generateGradient);

button.classList.add('hidden');
githubLink.classList.add('hidden');

document.addEventListener('keydown', function(event) {
    if (event.key === 'b' || event.key === 'B') {
        button.classList.toggle('hidden');
        githubLink.classList.toggle('hidden');
    }

    if (event.code === 'Space') {
        event.preventDefault();
        generateGradient();
    }
});