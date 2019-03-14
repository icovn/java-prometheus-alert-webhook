#Reference
- https://prometheus.io/docs/alerting/configuration/#webhook_config
- https://prometheus.io/docs/alerting/notifications/#alert

#Test
curl -X POST \
  https://DOMAIN_XXX/alert/sms \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: f649494b-d242-4675-9974-c6566d94ccbd' \
  -H 'cache-control: no-cache' \
  -d '{
   "receiver":"team-sms",
   "status":"firing",
   "alerts":[
      {
         "status":"firing",
         "labels":{
            "alertname":"test-123",
            "instance":"10.0.1.99"
         },
         "annotations":{
            "description":"RAM 70%"
         }
      }
   ],
   "commonLabels":{
      "key":"value"
   }
}'