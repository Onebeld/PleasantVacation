/**
 * Creates an element from the given HTML string, optionally trimming whitespace.
 *
 * @param {string} html - the HTML string to create the element from
 * @param {boolean} [trim=true] - flag to indicate whether to trim the HTML string
 * @return {Element|NodeList} the created element or a list of created elements
 */
function createElementFromHTML(html, trim = true) {
    html = trim ? html.trim() : html;
    if (!html) return null;

    const template = document.createElement('template');
    template.innerHTML = html;
    const result = template.content.children;

    if (result.length === 1) return result[0];
    return result;
}

function openMenu() {
    const selectWrapper = document.querySelector(".select-wrapper");

    if (selectWrapper.style.pointerEvents === "all") {
        selectWrapper.style.opacity = 0;
        selectWrapper.style.pointerEvents = "none";
    }
    else {
        selectWrapper.style.opacity = 1;
        selectWrapper.style.pointerEvents = "all";
    }
}

function closeMenu() {
    const selectWrapper = document.querySelector(".select-wrapper");
    selectWrapper.style.opacity = 0;
    selectWrapper.style.pointerEvents = "none";
}

window.addEventListener("click", (event) => {
    if (!event.target.closest(".nav-button-menu") && !event.target.closest(".select-wrapper")) {
        closeMenu();
    }
});

document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".nav-button-menu").addEventListener("click", openMenu);
});