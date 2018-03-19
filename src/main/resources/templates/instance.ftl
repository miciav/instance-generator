<#setting number_format="computer">
Ni ${numItems}
Nj ${numFamilies}
Nk ${numKnapsacks}
items
<#list items as item>
${item.itemId}  ${item.familyId}    ${item.weights[0]}  ${item.weights[1]}
</#list>
families
<#list families as family>
    ${family.familyId}  ${family.profit}    ${family.penalty}   ${family.numItems}
</#list>
knapsacks
<#list knapsacks as k>
    ${k.knapsackId}  ${k.capacities[0]?string}  ${k.capacities[1]}  ${k.alpha}  ${k.beta}
</#list>

