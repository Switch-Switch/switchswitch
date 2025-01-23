#!/bin/sh

sleep 5

echo "Installing required dependencies..."
apt-get update && apt-get install -y unzip curl

echo "Downloading AWS CLI V2..."
curl -s "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"

echo "Unzipping AWS CLI package..."
unzip -q awscliv2.zip

echo "Installing AWS CLI..."
./aws/install

echo "Verifying AWS CLI installation..."
aws --version

echo "Cleaning up..."
rm -rf awscliv2.zip aws

echo "Creating DynamoDB table..."
aws dynamodb create-table \
    --table-name ChatMessage \
    --attribute-definitions AttributeName=chatMessageId,AttributeType=S \
    --key-schema AttributeName=chatMessageId,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --endpoint-url http://localhost:8000

echo "Table 'MyTable' created successfully."