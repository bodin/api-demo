[#ftl]

[#assign remote_id ="modal-remote-id" /]

[#macro modal id title="" closeButton=true]
    <div id="${id}" class="modal">
        <div class="modal-body">
            <h1>${title}</h1>
            [#nested/]
            [#if closeButton]
                <button onclick="modal.closeModal()">Close</button>
            [/#if]
        </div>
    </div>
[/#macro]

[#macro modalRemote title="" closeButton=true]
    <div id="${remote_id}" class="modal" hx-on::after-settle="modal.openModal('${remote_id}')">
        <div class="modal-body">
            <h1>${title}</h1>
            [#nested/]
            [#if closeButton]
                <button onclick="modal.closeModal()">Close</button>
            [/#if]
        </div>
    </div>
[/#macro]

[#macro modalRemotePlaceholder]
    <div id="${remote_id}" />
[/#macro]

