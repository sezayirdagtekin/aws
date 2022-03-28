#!/bin/bash
echo "Hello World" >> sezar.txt
aws s3 mb s3://sezarbucket
aws s3 sync ./ s3://sezarbucket
aws s3 ls s3://sezarbucket


