// Get necessary elements from DOM by id
let shadeContainer = document.getElementById("shade-container");
let loginContainer = document.getElementById("login-container");
let registerContainer = document.getElementById("register-container");

let secondaryButton = document.getElementById("secondary-button");

let userIcon = document.getElementById("user-icon");

// Get necessary elements from DOM by class
let fields = document.getElementsByClassName("field");

let exitIcons = document.getElementsByClassName("exit-icon");

// Add event listeners to elements gathered from DOM by id
shadeContainer.addEventListener("click", exit);

secondaryButton.addEventListener("click", enterRegister);

userIcon.addEventListener("click", enterLogin);

// Add event listeners to elements gathered from DOM by class
for (let i = 0; i < exitIcons.length; i++) {
  exitIcons[i].addEventListener("click", exit);
}

// Define functions
function enterLogin() {
  for (let i = 0; i < fields.length; i++) {
    fields[i].style.position = "static";
  }

  shadeContainer.style.display = "block";
  loginContainer.style.display = "flex";
}

function enterRegister() {
  loginContainer.style.display = "none";
  registerContainer.style.display = "flex";
}

function exit() {
  for (let i = 0; i < fields.length; i++) {
    fields[i].style.position = "relative";
  }

  shadeContainer.style.display = "none";
  loginContainer.style.display = "none";
  registerContainer.style.display = "none";
}
