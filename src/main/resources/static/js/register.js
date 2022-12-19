
let registerForm = document.querySelector("#registerForm");
let emailInput = document.querySelector(".email");
let passwordInput = document.querySelector(".password");

function register() {
  fetch('http://localhost:8080/api/v1/users/register', {
    method: 'POST',
    cache: "no-cache",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      email: emailInput.value,
      password: passwordInput.value,
    })
  })
  .then(async (response) => {
    if (response.status == 200) {
      alert("회원가입 되었습니다!");
      window.location.href = "http://localhost:8080/users/login";
    }
    else {
      let json = await response.json();
      alert(json.message);
    }
  });
}

registerForm.addEventListener('submit', register, true);
