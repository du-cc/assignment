var theme_switch_e;
var theme_switch_i;

// init button state
var user_dark = window.matchMedia("(prefers-color-scheme: dark)").matches;

function refreshState() {
  var root = document.documentElement;

  if (user_dark) {
    if (theme_switch_i) theme_switch_i.className = "fa-solid fa-sun-bright";
    root.style.setProperty("--bg-theme", "#000");
    root.style.setProperty("--fg", "#fff");
  } else {
    if (theme_switch_i) theme_switch_i.className = "fa-solid fa-moon";
    root.style.setProperty("--bg-theme", "#fff");
    root.style.setProperty("--fg", "#000");
  }
}

refreshState();

window.initTheme = () => {
  theme_switch_e = document.getElementById("theme_switch");
  theme_switch_i = document.querySelector("#theme_switch > i");

  refreshState();

  // button press
  if (theme_switch_e) {
    theme_switch_e.addEventListener("mousedown", (e) => {
      user_dark = !user_dark;
      refreshState();
    });
  }
};
