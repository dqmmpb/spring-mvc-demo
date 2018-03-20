# API规范

[TOC]

## 规约

### 类型格式说明

字符型、数值型
- A18 固定长度为18 的字符。 
- VA100 可变长度，最大为100的字符。
- N3 最大长度为3 位数字。 
- N5.1 最大长度为5 位的十进制小数格式（包括小数点），小数点后保留1 位数字。
- B 布尔类型。
- Obj 对象类型，`{}`。
- Array 数组类型，`[]`。

日期格式化类型 
- D8 采用YYYYMMDD 格式（8 位定长）表示年月日。如1998 年1 月8 日，应表示为19980108。
- D10 采用YYYY-MM-DD 格式（10 位定长）表示年月日。如1998 年1 月8 日，应表示为1998-01-08。 

日期时间型 
- DT15 采用`YYYYMMDDThhmmss` 格式（15 位定长，包括中间1位空格，共15位）表示年月日时分秒。如1998年1月8日13点25分18秒，应表示为`19980108T132518`
- DT19 采用`YYYY-MM-DDThh:mm:ss` 格式（19 位定长，包括中间1位空格，共19位）表示年月日时分秒。如1998年1月8日13点25分18秒，应表示为`1998-01-08T13:25:18` 


### 格式

- 请求格式

统一以`application/json`格式提交

```
Content-Type: application/json
```
- 请求参数

统一封装为如下格式

>| 参数    | 包含 | 名称 | 类型 | 必填 | 备注                                                                                    |
>|:-------|:----|:-----|:----|:----|:----------------------------------------------------------------------------------------|
>| sign   |     | 签名 | VA  | 否  | 参数校验值，校验规则待                                                                     |
>| params |     | 参数 | Obj | 否  | 参数实体包装类，所有的业务参数统一使用`params`包装，`sign`针对`params`中的业务参数，使用签名算法签名 |

- 请求参数示例

```json
{
	"sign": "非必填，用于校验参数的（暂时不适用，//TODO）",
	"params": {
		"phone": "13819493700",
		"password": "123456"
	}
}
```

- 返回参数

统一封装为如下格式

>| 参数          | 包含 | 名称    | 类型       | 必填 | 备注                                                                            |
>|:-------------|:----|:--------|:----------|:----|:--------------------------------------------------------------------------------|
>| success      |     | 请求结果 | B         | 是  | true-请求成功;false-请求失败                                                      |
>| errorCode    |     | 错误码   | VA        | 否  | 错误码                                                                           |
>| errorMessage |     | 错误消息 | VA        | 否  | 错误消息                                                                         |
>| errorType    |     | 错误类型 | VA        | 否  | 错误类型，用于将errorCode归类                                                      |
>| result       |     | 返回结果 | Obj/Array | 否  | 返回结果                                                                         |
>|              |     |         |           |     | `result`建议                                                                    |
>|              |     |         |           |     | `{}`，Obj结果对象                                                                |
>|              |     |         |           |     | `[]`，Array数组对象，用作列表                                                      |
>|              |     |         |           |     | `{"list":[],"pageNum":1,"pageSize":20,"pages":1,"total":8}`，分页对象，用作分页列表 |

- 返回示例

```json
{
    "success": true,
    "errorCode": "",
    "errorMessage": "",
    "errorType": "",
    "result": {
        "login": true,
        "phone": "13819493700",
        "token": "35de065d-f2bd-4264-9c2c-bae60b064a1b"
    }
}
```
```json
{
    "success": true,
    "errorCode": "",
    "errorMessage": "",
    "errorType": "",
    "result": [
        {
            "description": "权限管理",
            "parentPrivId": 0,
            "path": "/priv",
            "privCode": "sys:priv:dir:manage",
            "privId": 1,
            "privName": "权限管理",
            "state": "A",
            "type": 0,
            "url": "/priv"
        },
        {
           "description": "用户管理",
           "parentPrivId": 0,
           "path": "/user",
           "privCode": "sys:user:dir:manage",
           "privId": 32,
           "privName": "用户管理",
           "state": "A",
           "type": 0,
           "url": "/user"
        }
    ]
}
```
```json
{
    "success": true,
    "errorCode": "",
    "errorMessage": "",
    "errorType": "",
    "result": {
        "list": [
            {
                "auditStatus": 1,
                "disableStatus": 1,
                "id": 1,
                "lastLoginTime": null,
                "name": "",
                "phone": "13819493700",
                "state": "A",
                "teamLeader": null,
                "teamLeaderId": 0
            },
            {
                "auditStatus": 1,
                "disableStatus": 1,
                "id": 2,
                "lastLoginTime": null,
                "name": "",
                "phone": "13819493701",
                "state": "A",
                "teamLeader": null,
                "teamLeaderId": 0
            }
        ],
        "pageNum": 1,
        "pageSize": 2,
        "pages": 2,
        "total": 3
    }
}
```
