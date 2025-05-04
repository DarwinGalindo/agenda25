# Proyecto Agenda25 para el Despliegue usando Amazon ECS y RDS

### Tecnologías usadas
- Java 21
- Gradle
- Spring Boot
- Amazon ECS
- Amazon RDS
- Amazon ECR
- Balanceo de carga
- Autoescalado automático
- Gestión de secretos y parámetros con AWS Secrets Manager y Parameter Store
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)

## Videotutorial
👉 Mira aquí el video en [YouTube](https://youtu.be/NSIccUMPeww)

## Comandos y permisos utilizados

```bash
./gradlew build
```

```bash
aws configure --profile <profile-name>
```

```bash
aws ecr get-login-password --region <region> --profile <profile-name> | docker login --username AWS --password-stdin <account-id>.dkr.ecr.<region>.amazonaws.com
```

Policy para el usuario `todotic_ecr`

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ecr:BatchCheckLayerAvailability",
                "ecr:CompleteLayerUpload",
                "ecr:GetAuthorizationToken",
                "ecr:InitiateLayerUpload",
                "ecr:PutImage",
                "ecr:UploadLayerPart"
            ],
            "Resource": "*"
        }
    ]
}
```

Policy `AmazonECSTaskExecutionRolePolicy`

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "*"
    }
  ]
}
```

Policy `parameter_store_agenda`

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ssm:GetParameter",
                "ssm:GetParameters",
                "ssm:DescribeParameters"
            ],
            "Resource": "<arn-parameters>"
        }
    ]
}
```

Policy `secret_manager_rds`

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "secretsmanager:GetSecretValue",
                "secretsmanager:DescribeSecret"
            ],
            "Resource": "<arn-rds-secret>"
        }
    ]
}
```