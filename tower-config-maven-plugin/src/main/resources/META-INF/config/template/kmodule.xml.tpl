<?xml version="1.0" encoding="UTF-8"?>
<kmodule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://www.drools.org/xsd/kmodule">

    <kbase name="default" packages="org.drools.examples.helloworld">
        <ksession name="HelloWorldKS"/>
    </kbase>
   	<kbase name="DTableWithTemplateKB" packages="org.drools.examples.decisiontable-template">
        <ruleTemplate dtable="org/drools/examples/decisiontable-template/ExamplePolicyPricingTemplateData.xls"
                      template="org/drools/examples/decisiontable-template/BasePricing.drt"
                      row="3" col="3"/>
        <ruleTemplate dtable="org/drools/examples/decisiontable-template/ExamplePolicyPricingTemplateData.xls"
                      template="org/drools/examples/decisiontable-template/PromotionalPricing.drt"
                      row="18" col="3"/>
        <ksession name="DTableWithTemplateKS"/>
    </kbase>
</kmodule>
