[#ftl]
[#import "../inc/modal.ftlh" as m /]

[#macro deleted]
    <tr>
        <td colspan="5">
            <i>Deleted</i>
        </td>
    </tr>
[/#macro]

[#macro clientRow c]
    <tbody>
        <tr>
            <td>${c.clientName}</td>
            <td>${c.subscription}</td>
            <td>${c.scopes?join(", ")}</td>
            <td>${c.clientSecretExpiresAt?string?substring(0, 10)}</td>
            <td>
                <button hx-post="/site/delete?id=${c.id}"
                        hx-target="closest tbody"
                        hx-headers='{"${_csrf.headerName}": "${_csrf.token}"}'
                        class="pure-button button-warning button-small"
                >
                    X
                </button>
            </td>
        </tr>
        [#if (new_client?exists && new_client.id == c.id)]
            <tr class="table-supplemental">
                <td>Client ID</td>
                <td colspan="3"><span class="client_id"
                          hx-on:click="this.classList.add('click_fade')"
                          hx-on:animationend="this.classList.remove('click_fade')"
                          onclick="util.copyText('${new_client.clientId}')" >
                        ${new_client.clientId}
                    </span></td>
                <td></td>
            </tr>
            <tr class="table-supplemental">
                <td>Client Secret</td>
                <td colspan="3"><span class="client_secret"
                          hx-on:click="this.classList.add('click_fade')"
                          hx-on:animationend="this.classList.remove('click_fade')"
                          onclick="util.copyText('${new_client.clientSecret}')">
                        ${new_client.clientSecret}
                    </span></td>
                <td></td>
            </tr>
        [/#if]
    </tbody>
[/#macro]

[#macro addClient]
    [@m.modalRemote "New Api Credentials" false]
        <form action="/site/add" method="post"
              class="pure-form pure-form-stacked">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <fieldset>
                <legend>Basic information</legend>

                <label for="client-name">Name</label>
                <input name="name" id="client-name" placeholder="Name" />

                <label for="expiration">Expiration</label>
                <select name="expiration" id="expiration">
                    <option value="d">1 Day</option>
                    <option value="m">1 Month</option>
                    <option value="y">1 Year</option>
                </select>

                <label for="subscription">Subscription Level</label>
                <select name="subscription" id="subscription">
                    <option value="free">Free Tier</option>
                    <option value="silver">Silver Tier</option>
                    <option value="gold">Gold Tier</option>
                    <option value="platinum">Platinum Tier</option>
                </select>

            </fieldset>

            <fieldset>
                <legend>Select which APIs to access</legend>
                <label for="scope-api1" class="pure-checkbox">
                    <input name="scope" value="api_api1" id="scope-api1" type="checkbox" /> API 1
                </label>

                <label for="scope-api2" class="pure-checkbox">
                    <input name="scope" value="api_api2" id="scope-api2" type="checkbox" /> API 2
                </label>

                <label for="scope-api3" class="pure-checkbox">
                    <input name="scope" value="api_api3" id="scope-api3" type="checkbox" /> API 3
                </label>

            </fieldset>
            <button type="submit">Add</button>
            <button onclick="modal.closeModal()">Close</button>
        </form>
    [/@m.modalRemote]
[/#macro]