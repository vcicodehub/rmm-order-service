# Signet Repair Materials Management Microservice

This is the Repair Materials Management Microservice (*formally called Compiere*) that manages the follow:
 * supply orders
 * supply order line items
 * receipt
 * receipt line items
 * users
 * vendors

### YAML Lint (life saver!)
Navigate to the link below to find errors in YAML files.  GREAT for finding insidious bugs in the `buildspec.yml`!

https://jsonformatter.org/yaml-formatter

### DockerHub Pull Restrictions
To deal with the DockerHub pull restrictions read this blog post:

https://medium.com/rockedscience/fixing-docker-hub-rate-limiting-errors-in-ci-cd-pipelines-ea3c80017acb

You will need to add the following to the AIM Role created with CodePipeline:

```
{
    "Effect": "Allow",
    "Action": [
        "ssm:GetParameter",
        "ssm:GetParameters"
    ],
    "Resource": "arn:aws:ssm:{AWS_REGION}:{YOUR_AWS_ACCOUNT_ID}:parameter/my-application/dockerhub/*"
},
{
    "Effect": "Allow",
    "Action": [
        "kms:Decrypt",
        "kms:GenerateDataKey*"
    ],
    "Resource": [
        "{YOUR_KMS_KEY_ID_HERE}"
    ]
},
```

## Push Docker image to ECR (free tier)
* aws ecr create-repository --repository-name rmm-order-service --image-scanning-configuration scanOnPush=true --region us-east-2

* aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 415353467794.dkr.ecr.us-east-2.amazonaws.com
* docker tag rmm-order-service:latest 415353467794.dkr.ecr.us-east-2.amazonaws.com/rmm-order-service:latest
* docker push 415353467794.dkr.ecr.us-east-2.amazonaws.com/rmm-order-service:latest

## Delete an ECR image
* aws ecr batch-delete-image --repository-name rmm-order-service --image-ids imageTag=latest
