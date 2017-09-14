%dw 2.0
ns mule_mule http://www.mulesoft.org/schema/mule/core
ns http http://www.mulesoft.org/schema/mule/http
ns doc http://www.mulesoft.org/schema/mule/documentation
ns urn urn:oid:2.16.840
---
mule_mule#flow @(name: "HelloWorldFlow1"): {
    http#"inbound-endpoint" @("exchange-pattern": "request-response", host: "localhost", port: 8081,
                            doc#name: "HTTP", doc#description: "This endpoint receives an HTTP message"): null,
    mule_mule#"set-payload" @(value: "Hello World",
                       doc#name: "Set Payload", doc#description: "This processor sets the payload of the message to the string 'Hello World'"): null
}