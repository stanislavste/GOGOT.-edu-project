<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as login>

<@common.page>
<div><strong>Add new user</strong></div>
${message}
<@login.login "/registration" />
</@common.page>