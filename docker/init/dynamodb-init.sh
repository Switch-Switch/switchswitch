aws dynamodb create-table\
  --table-name ChatMessage\
  --attribute-definitions '[{"AttributeName":"chatMessageId","AttributeType":"S"}]'\
  --key-schema AttributeName=chatMessageId,KeyType=HASH\
  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1\
  --endpoint-url http://dynamodb-local:8000