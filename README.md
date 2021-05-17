# Signet Supply Order Management Microservice

This is the Supply Order Management Microservice (*formally call Compiere*) that manages the follow:
 * supply orders
 * supply order line items
 * receipt
 * receipt line items
 * users
 * vendors

### The Swagger/OpenAPI spec

Navigate to the link below to see the OpenAPI docs for this API:

`http://<host>:<port>/swagger-ui.html`

### YAML Lint (life saver!)
Navigate to the link below to find errors in YAML files.  GREAT for finding insidious bugs in the `buildspec.yml`!

https://jsonformatter.org/yaml-formatter

### DockerHub Pull Restrictions
To deal with the DockerHub pull restrictions read this blog post:

https://medium.com/rockedscience/fixing-docker-hub-rate-limiting-errors-in-ci-cd-pipelines-ea3c80017acb

You will need to add the following to the AIM Role created with CodePipeline:

`
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
`