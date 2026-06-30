# Docker 部署说明

## 1. 服务器准备

服务器需要安装 Docker 和 Docker Compose 插件。

检查命令：

```bash
docker --version
docker compose version
```

## 2. 启动项目

在项目根目录执行：

```bash
docker compose up -d --build
```

首次启动会自动完成：

- 构建 Vue 前端
- Maven 打包 `library.war`
- 启动 MySQL 8.0
- 初始化 `sql/schema.sql` 和 `sql/data.sql`
- 启动 Tomcat 并部署到 `/library`

访问地址：

| 页面 | 地址 |
| --- | --- |
| 读者门户 | `http://服务器IP:8080/library` |
| 管理后台 | `http://服务器IP:8080/library/admin` |

默认账号见 `sql/data.sql`：

| 类型 | 账号 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `admin123` |
| 读者 | `R20240001` | `reader123` |

## 3. 常用命令

查看运行状态：

```bash
docker compose ps
```

查看日志：

```bash
docker compose logs -f app
docker compose logs -f mysql
```

停止服务：

```bash
docker compose down
```

重新构建并启动：

```bash
docker compose up -d --build
```

## 4. 修改端口或密码

可以在项目根目录新建 `.env` 文件：

```env
APP_PORT=8080
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD=请改成强密码
DB_NAME=library_db
```

然后重新启动：

```bash
docker compose up -d
```

## 5. 数据和上传文件

数据库数据保存在 Docker volume `library-mysql-data`。

上传的图书封面保存在 Docker volume `library-uploads`，对应容器内路径：

```text
/data/library/uploads
```

注意：`sql/schema.sql` 和 `sql/data.sql` 只会在 MySQL 数据目录第一次创建时执行。已有数据库不会重复初始化。

如果确实要清空数据库并重新初始化，可以执行：

```bash
docker compose down -v
docker compose up -d --build
```

这会删除数据库和上传文件数据，请谨慎使用。
