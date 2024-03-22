// JavaScript for toggling sub-options
window.addEventListener('DOMContentLoaded', (event) => {
  const options = document.querySelectorAll('.option');

  options.forEach(option => {
    option.addEventListener('click', () => {
      option.classList.toggle('active');
    });

    const subOptions = option.querySelector('.sub-options');

    subOptions.addEventListener('click', (event) => {
      event.stopPropagation();
    });
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const subOptions = document.querySelectorAll('.sub-option');
  const contentDivs = document.querySelectorAll('#content > div');
  const subContentButton = document.getElementById('subcontent-1-1-button');
  const dataDisplayDiv = document.getElementById('data-display');

  subOptions.forEach(subOption => {
    subOption.addEventListener('click', () => {
      const target = subOption.dataset.target;
      const content = document.getElementById(target);

      if (content) {
        contentDivs.forEach(div => {
          if (div.id === target) {
            div.classList.toggle('hidden-content');
          //   alert(target); // Display each part in a separate alert message
            
          } else {
            div.classList.add('hidden-content');
          }
        });
      }
    });
  });

  subContentButton.addEventListener('click', () => {
    const target = 'edit';
    const content = document.getElementById(target);
    const clickedButtonId = subContentButton.getAttribute('data-id');

    if (content) {
      contentDivs.forEach(div => {
        if (div.id === target) {
          div.classList.toggle('hidden-content');
          dataDisplayDiv.textContent = `Button ID: ${clickedButtonId}`;
        } else {
          div.classList.add('hidden-content');
        }
      });
    }
  });
});

