#!/bin/sh


# AWS CLI 설치 스크립트
echo "Starting AWS CLI installation..."

# 기존 AWS CLI 제거
echo "Removing existing AWS CLI..."
sudo yum remove -y awscli

# AWS CLI V2 다운로드
echo "Downloading AWS CLI V2..."
curl -s "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"

# 압축 해제
echo "Unzipping AWS CLI package..."
unzip -q awscliv2.zip

# AWS CLI 설치
echo "Installing AWS CLI..."
sudo ./aws/install

# 설치 확인
echo "Verifying AWS CLI installation..."
aws --version

# 정리 작업
echo "Cleaning up..."
rm -rf awscliv2.zip aws

echo "AWS CLI installation completed."