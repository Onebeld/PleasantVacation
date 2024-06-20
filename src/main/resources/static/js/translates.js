let translates = {};

const translatesElement = document.querySelector("translates");

if (translatesElement !== null) {
    for (const translate of translatesElement.children) {
        translates[translate.getAttribute("id")] = translate.innerText;
    }

    translatesElement.remove();
}