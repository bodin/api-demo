[#ftl]

[#import "inc/page.ftlh" as p /]
[#import "inc/modal.ftlh" as m /]
[#import "clients/clients-inc.ftlh" as c /]

[@p.page 'API Management'; t]
    [#if t='body']

        <div style="margin-bottom:10px;">
            <button class="pure-button pure-button-primary" hx-get="/site/new"
                    hx-target="#${m.remote_id}"
                    hx-swap="outerHTML">
                Add New API Key
            </button>
        </div>
        [#if clients?has_content]
            <table class="pure-table">
                <thead>
                    <tr>
                        <th>Client Name</th>
                        <th>Subscription</th>
                        <th>Scopes</th>
                        <th>Expires On</th>
                        <th></th>
                    </tr>
                </thead>
                [#list clients as cl]
                    [@c.clientRow cl /]
                [/#list]
            </table>
        [/#if]
    [/#if]
[/@p.page]

