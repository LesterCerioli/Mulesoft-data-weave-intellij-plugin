%dw 2.0
input in0 application/json
output application/json
var toUser = (user) -> { name: user.name,	lastName: user.lastName }
---
users: in0 map ((user) -> {
    user: (toUser(user) ++ user)
  })
