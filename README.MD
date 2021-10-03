#Reference
- https://prometheus.io/docs/alerting/configuration/#webhook_config
- https://prometheus.io/docs/alerting/notifications/#alert

#Test
curl -X POST \
  https://DOMAIN_XXX/alert \
  -H 'Content-Type: application/json' \
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