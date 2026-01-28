const topbarPromise = fetch("topbar.html")
  .then((response) => response.text())
  .then((response) => {
    var topbar = new DOMParser().parseFromString(response, "text/html");
    return topbar.getElementById("topbar");
  });

const videoPromise = new Promise((resolve) => {
  const video = document.querySelector("video");
  if (!video || video.readyState >= 2) {
    resolve();
    return;
  }
  video.addEventListener("loadeddata", resolve, { once: true });
  video.addEventListener("error", resolve, { once: true });
});

Promise.all([topbarPromise, videoPromise])
  .then(([topbarElement]) => {
    if (topbarElement) {
        document.body.prepend(topbarElement);
        if (window.initTheme) window.initTheme();
    }
    document.body.style.opacity = "1";
  })
  .catch((err) => {
    console.log(err);
    document.body.style.opacity = "1";
  });