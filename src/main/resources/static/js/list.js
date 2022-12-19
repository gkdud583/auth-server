let tbody = document.querySelector("table tbody");
var accessToken;

function showList() {
  fetch("http://localhost:8080/api/v1/users", {
    method: 'GET',
    cache: "no-cache",
    withCredentials: true,
    headers: {
      'Authorization': "Bearer " + accessToken,
    }
  })
  .then(async response => {
    if (response.status == 200) {
      let json = await response.json();
      var template = document.querySelector("#tableTemplate").innerText;
      var bindTemplate = Handlebars.compile(template);
      var resultHtml = json.reduce(function (prev, next) {
        return prev + bindTemplate(next);
      }, "");
      tbody.innerHTML = resultHtml;
    } else {
      let json = await response.json();
      console.log(json.message);
      alert("접근 권한이 없습니다!");
      window.location.href = "http://localhost:8080";
    }
  })
}

function refreshToken() {
  fetch('http://localhost:8080/api/v1/users/token/refresh', {
    method: 'POST',
    credentials: "same-origin",
  })
  .then(async response => {
    if (response.status == 200) {
      let json = await response.json();
      accessToken = json.token;
      showList();
    }
    else {
      let json = await response.json();
      console.log(json.message);
      // alert("접근 권한이 없습니다!");
      // window.location.href = "http://localhost:8080";
    }
  })
}

refreshToken();
