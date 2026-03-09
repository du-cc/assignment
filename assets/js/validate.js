function validate(inputElement) {
  var input = inputElement.value;
  var target = inputElement.parentElement;
  var check = inputElement.id;

  function throwError(text) {
    var errElement = document.getElementById("err_" + check);

    // clear error
    if (text == "") {
      if (errElement) {
        errElement.remove();
      }
      inputElement.classList.remove("invalid");
      return;
    }

    // update error message (failsafe)
    if (errElement) {
      errElement.querySelector("span").innerText = text;
      return;
    }

    // red border
    inputElement.classList.add("invalid");

    // create error message
    errElement = document.createElement("div");
    errElement.id = "err_" + check;
    errElement.style.color = "rgb(255, 69, 69)";
    errElement.style.fontSize = "clamp(0.7rem, 1.2vw, 1rem)";

    var errIcon = document.createElement("i");
    errIcon.className = "fa-regular fa-circle-exclamation";

    var errMessage = document.createElement("span");
    errMessage.innerText = text;
    errMessage.style.marginLeft = "0.3em";

    errElement.appendChild(errIcon);
    errElement.appendChild(errMessage);

    target.appendChild(errElement);
  }

  if (inputElement.required && !input) {
    throwError("Input is required");
    return false;
  }

  if (!inputElement.required && !input) {
    throwError("");
    return true;
  }

  if (check == "email" && !/^[^@]+@[^@]+\.[^@]+$/.test(input)) {
    throwError("Invalid email address");
    return false;
  }

  if (check == "phone" && !/^\+\d{7,15}$/.test(input)) {
    if (!input.includes("+")) {
      throwError("Phone number must begin with a + (country code)");
      return false;
    }
    throwError("Invalid phone number");
    return false;
  }

  throwError("");
  return true;
}

document.addEventListener("input", (e) => {
  validate(e.target);
});

document.addEventListener("focusout", (e) => {
  validate(e.target);
});

document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();

  var inputs = document.querySelectorAll(
    "form input, form textarea, form select",
  );

  for (let i = 0; i < inputs.length; i++) {
    if (!validate(inputs[i])) {
      return;
    }
  }

  e.target.submit()
});
