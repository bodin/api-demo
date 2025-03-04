function copyText(t) {
    // We check that the input is not empty
    if (t.length) {
        // We copy the text it contains into the clipboard
        navigator.clipboard.writeText(t).then(() => {
            // We confirm the action in the console
            console.log("Text Copied !");
        })
    } else {
        console.log("The input is empty ");
    }
}

window.util = {copyText}