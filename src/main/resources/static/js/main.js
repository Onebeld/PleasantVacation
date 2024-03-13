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

/**
 * Sets the size of navigation buttons to the maximum width of all buttons.
 *
 * @param {type} items - description of the items parameter
 * @return {type} undefined - no return value
 */
function uniformNavigationButtonsSize() {
    const items = document.querySelectorAll(".nav-button");
    let maxWidth = 0;

    items.forEach(item => {
        maxWidth = Math.max(maxWidth, item.offsetWidth);
    });

    items.forEach(item => {
        item.style.width = `${maxWidth}px`;
    });
}

window.addEventListener("load", () => {
    uniformNavigationButtonsSize();
});