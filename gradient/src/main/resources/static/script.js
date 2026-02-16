function generateGradient() {
    const img = document.getElementById('gradient');
    img.src = '/generate-gradient?' + new Date().getTime();
}

document.getElementById('generateBtn').addEventListener('click', generateGradient);
document.getElementById('gradient').addEventListener('click', generateGradient);

document.addEventListener('keydown', function(event) {
    if (event.code === 'Space') {
        event.preventDefault();
        generateGradient();
    }
});