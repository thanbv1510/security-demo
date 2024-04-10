# Security

## Requirements

- Authentication using JWT
- Authorization using role + permission based access control

## Implement

- Hardcode: Map<username, List<User>>
- API login: create JWT
- API Authorize:
    - Declare the permission for this API
    - Verify token, permission

## Demo
> Using Java core with JDK 21
>
> [list apis](api.http)

### 1. API Login

- login fail

```json
{
  "code": 401,
  "err": "Invalid password",
  "data": null
}
```

- login success

```json
{
  "code": 200,
  "err": "success",
  "data": {
    "user": {
      "id": 1,
      "username": "123456",
      "roles": [
        "ANONYMOUS",
        "USER_FREE"
      ]
    },
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyb25pbkZyZWUxIiwiaWF0IjoxNzEyNzgxNDc4LCJleHAiOjE3MTI3ODIwNzh9.ykgr7b0N68OKWJKCzvmbONtncino_RHyTI89vEqS_knmA3TpTVYMPRRi2euVGx3r"
  }
}
```

### 2. API get all posts Premium

- User free (user does not permission)

```json
{
  "code": 401,
  "err": "user roninFree1 does not have permissions [[POST_PREMIUM_VIEW]]",
  "data": null
}
```

- User premium

```json
{
  "code": 200,
  "err": "success",
  "data": [
    {
      "id": 5,
      "title": "title5",
      "content": "content5",
      "summary": "summary5",
      "avatarUrl": "avatarUrl5",
      "slug": "slug5",
      "type": "PREMIUM"
    },
    {
      "id": 4,
      "title": "title4",
      "content": "content4",
      "summary": "summary4",
      "avatarUrl": "avatarUrl4",
      "slug": "slug4",
      "type": "PREMIUM"
    },
    {
      "id": 3,
      "title": "title3",
      "content": "content3",
      "summary": "summary3",
      "avatarUrl": "avatarUrl3",
      "slug": "slug3",
      "type": "PREMIUM"
    }
  ]
}
```