<#import "parts/common.ftl" as c>

<@c.page>
    List of users
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <#list users as user>
            <tr>
                <th>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>,</#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
                </th>
            </tr>
        </#list>
    </table>
</@c.page>