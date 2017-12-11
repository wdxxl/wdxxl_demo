window.onload = () => {
  const container = document.querySelector('.container');
  setInterval(() => {
    container.classList.toggle('show');
  }, 4000);
};
