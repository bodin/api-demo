[#ftl]
[#import "modal.ftlh" as m /]
[#macro page title]
    <!DOCTYPE html>
    <html lang="">
    <head>
        <title>${title}</title>
        <meta name="viewport" content="width=device-width"/>

        <script type="module" src="/index.js"></script>
        <link rel="stylesheet" href="/style.css">
        [#nested 'head'/]
    </head>
    <body>
    <!-- main app container -->
    <div>
        [#nested 'body'/]
        [@m.modalRemotePlaceholder /]
    </div>
    </body>
    </html>
[/#macro]