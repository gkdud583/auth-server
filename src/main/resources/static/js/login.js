var accessToken = null;
let loginForm = document.querySelector("#loginForm");
let emailInput = document.querySelector(".email");
let passwordInput = document.querySelector(".password");

function login() {
  fetch('http://localhost:8080/api/v1/users/login', {
        method: 'POST',
        cache: "no-cache",
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: emailInput.value,
          password: passwordInput.value
        })
      })
  .then(async (response) => {
    if (response.status == 200) {
      alert("로그인 되었습니다!");
      let json = await response.json();
      accessToken = json.token;
      window.location.href = "http://localhost:8080";
    }
    else {
      let json = await response.json();
      alert(json.message);
    }
  });
}

loginForm.addEventListener('submit', login, true);
