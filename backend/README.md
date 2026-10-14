# 智慧养老服务中心后端

课程团队项目；项目负责人 / 维护者：迟凡杰

本目录保存课程团队后端的脱敏源码。功能导航、技术架构、配置与 IDE 启动入口见 [仓库首页](../README.md)。本次公开整理未安装依赖或执行构建、测试，不能据此断言当前版本已经运行通过。

## Required configuration

Supply DB_URL, DB_USERNAME, DB_PASSWORD, MAIL_USERNAME, MAIL_PASSWORD, JWT_SECRET, and UPLOAD_ROOT through the deployment environment. `src/main/resources/application-dev.example.yml` contains placeholders only. Real development configuration, logs, uploads, recovery data, IDE files, compiled output, and the original side-effecting test remain excluded.

JWT processing fails closed when its signing key is absent. File storage requires a configured root and rejects paths escaping that root. SQL statement, parameter, stdout, response-payload, and local-path logging is disabled by default.

## Database archive

`database/schema.sql` contains schema-only DDL and constraints for 28 audited tables. `database/seed.example.sql` creates no accounts and contains no source row data, login-capable credential, or contact value.

## License provenance

`LICENSE` is the unchanged MIT license retained from the audited backend source history (SHA-256 `ad909b15a9e3b66c24887f5b1bb8496c150d1200147802d23fcdc34fbf96ce5b`).
