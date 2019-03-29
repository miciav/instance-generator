<#setting number_format="computer">
Ni ${numItems}
Nj ${numFamilies}
Nk ${numKnapsacks}
items
<#list items as item>
${item.itemId}  ${item.familyId} <#list item.weights as w> ${w} </#list>
</#list>
families
<#list families as family>
    ${family.familyId}  ${family.profit}    ${family.penalty}   ${family.numItems}
</#list>
knapsacks
<#list knapsacks as k>
    ${k.knapsackId}  <#list k.capacities as c>${c} </#list> <#list k.fillRatio as f>${f} </#list>
</#list>

